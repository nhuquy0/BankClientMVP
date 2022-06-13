package com.example.bankclient.account1;

import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.example.bankclient.Account;
import com.example.bankclient.EditProfileFragment;
import com.example.bankclient.NetworkInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class AccountPresenter  implements ContractAccount.AccountPresenter{

    ContractAccount.AccountView mainAccountView;
    ContractAccount.EditProfileFragment editProfileFragment;
    FragmentManager fragmentManager;
    ContractAccount.TransferMoneyDialog transferMoneyDialog;
    NetworkInterface.Network modelNetwork;

    private Account account;

    public AccountPresenter(ContractAccount.AccountView mainAccountView, NetworkInterface.Network modelNetwork, FragmentManager fragmentManager) {
        this.mainAccountView = mainAccountView;
        this.modelNetwork = modelNetwork;
        this.fragmentManager = fragmentManager;
    }

    public AccountPresenter() {
    }

    @Override
    public void editProfile(String firstName, String lastName, String address, String phoneNumber, String email, String city, String country){

        if(firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("") || country.equals("") || phoneNumber.equals("")){
            editProfileFragment.setLabelNoti("marked * fields cannot be empty", Color.RED);
            return;
        }

        if(!firstName.equals(account.getFirstName()) || !lastName.equals(account.getLastName()) || !address.equals(account.getAddress()) || !city.equals(account.getCity()) || !country.equals(account.getCountry()) || !phoneNumber.equals(account.getPhoneNumber()) || !email.equals(account.getEmail())){
            //Tạo chuỗi phụ để thêm vào chuỗi truy vấn chính
            //Nếu các trường nhập vào khác account đã load thì thêm vào chuỗi cập nhật vào DB
            String s = "";
            if(!firstName.equals(account.getFirstName())){
                s = s + "firstName = " + "'" + firstName + "', ";
                account.setFirstName(firstName);
            }
//
            if(!lastName.equals(account.getLastName())){
                s = s + "lastName = " + "'" + lastName + "', ";
                account.setLastName(lastName);
            }

            if(!address.equals(account.getAddress())){
                s = s + "address = " + "'" + address + "', ";
                account.setAddress(address);
            }

            if(!city.equals(account.getCity())){
                s = s + "cityStringArray = " + "'" + city + "', ";
                account.setCity(city);
            }

            if(!country.equals(account.getCountry())){
                s = s + "countryStringArray = " + "'" + country + "', ";
                account.setCountry(country);
            }

            if(!phoneNumber.equals(account.getPhoneNumber())){
                s = s + "phoneNumber = " + "'" + phoneNumber + "', ";
                account.setPhoneNumber(phoneNumber);
            }

            if(!email.equals(account.getEmail())){
                s = s + "email = " + "'" + email + "', ";
                account.setEmail(email);
            }

            //Bỏ đi 2 ký tự cuối là ", "
            if(s.length()>=2) {
                s = s.substring(0, s.length() - 2);
            }
            Log.v("BankClient",s);

            //Send string-query to Server
            modelNetwork.createConnect();
            modelNetwork.sendDataTCP("editprofile#" + account.getAccountID() + "#" +s);
            modelNetwork.readDataTCP();
            String mesRecv = "";
            mesRecv = modelNetwork.getMesFromServer();
            if(mesRecv.equals("editprofilesuccess")){
                //Cập nhật lại Account trong AccountActivity
                setAccount(account);
                mainAccountView.setLblAccountName(account.getAccountName());

                //Xoá fragment
                removeEditProfileFragmenttt();
            }

        }
    }

    @Override
    public void removeEditProfileFragmenttt(){
        if(editProfileFragment != null) {
            fragmentManager.beginTransaction().remove((EditProfileFragment) editProfileFragment).commit();
        }
    }

    @Override
    public void transferMoney(String accountID2, String sMoneyTransfer){
        if(accountID2.equals("") || sMoneyTransfer.equals("")){
            transferMoneyDialog.setLabelNoti("Please input info transfer");
            return;
        }
        if(sMoneyTransfer.contains(".")) {
            sMoneyTransfer = sMoneyTransfer.replaceAll("\\.", "");
        }
        BigDecimal moneyTransfer = new BigDecimal(sMoneyTransfer);

        if(moneyTransfer.compareTo(new BigDecimal("10000")) <0){
            transferMoneyDialog.setLabelNoti("Money transfer >= 10000");
            return;
        }

        if(moneyTransfer.compareTo(account.getAccountBalance()) >0){
            transferMoneyDialog.setLabelNoti("The account does not have enough money");
            return;
        }

        modelNetwork.createConnect();
        modelNetwork.sendDataTCP("transfermoney#" + account.getAccountID() + "#" + accountID2 + "#" + moneyTransfer);
        modelNetwork.readDataTCP();
        String mesRecv = "";
        mesRecv = modelNetwork.getMesFromServer();
        String[] messRecvArray = mesRecv.split("#");
        if(messRecvArray[0].equals("transfermoneysuccess")){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                account = objectMapper.readValue(messRecvArray[1], Account.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            mainAccountView.setLblAccountBalance();
            AlertDialog dialog = transferMoneyDialog.getDialog();
            //Dismiss once everything is OK.
            dialog.dismiss();
        }else if(mesRecv.equals("transfermoneyWrongID")){
            transferMoneyDialog.setLabelNoti("accountID incorrect");
            return;
        }
    }

    @Override
    public void getAccountFromServer(){
//        String ipAddress = modelNetwork.getIPAddress();
        String accountID = mainAccountView.getAccountIDSharePref();
        modelNetwork.createConnect();
        modelNetwork.sendDataTCP("account" + "#" + accountID);
        modelNetwork.readDataTCP();
        String mesRecv = "";
        mesRecv = modelNetwork.getMesFromServer();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            setAccount(objectMapper.readValue(mesRecv, Account.class));
            System.out.println(account);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setEditProfileFragment(ContractAccount.EditProfileFragment editProfileFragment){
        this.editProfileFragment = editProfileFragment;
    }

    @Override
    public void setTransferMoneyDialog(ContractAccount.TransferMoneyDialog transferMoneyDialog){
        this.transferMoneyDialog = transferMoneyDialog;
    }

    private void setAccount(Account account) {
        this.account = account;
    }
}
