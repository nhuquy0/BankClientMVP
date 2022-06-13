package com.example.bankclient.login;

import com.example.bankclient.NetworkInterface;

import org.mindrot.jbcrypt.BCrypt;


public class LoginPresenter implements ContractLogin.LoginPresenter{

    ContractLogin.LoginView mainLoginView;
    NetworkInterface.Network modelNetwork;

    public LoginPresenter(ContractLogin.LoginView mainloginView, NetworkInterface.Network modelNetwork) {
        this.mainLoginView = mainloginView;
        this.modelNetwork = modelNetwork;
    }

    @Override
    public void sendLogin(String uuid, String accountID, String password){
        if(password.length() < 6){
            mainLoginView.getToast("AccountID and password invalid");
            return;
        }
        String ipAddress = mainLoginView.getIPAddress();
        modelNetwork.setIPAddress(ipAddress);
        modelNetwork.createConnect();
        modelNetwork.sendDataTCP("login"+ "#" + accountID + "#" + uuid);
        modelNetwork.readDataTCP();
//        modelNetwork.killReadDataTCP(10000);
        String mesRecv = modelNetwork.getMesFromServer();
        boolean valuate = BCrypt.checkpw(password, mesRecv);
        if(valuate) {
            modelNetwork.sendDataTCP("LoginSuccess");
            mainLoginView.saveAccountIDPreference(accountID);
            //Change activity here
            mainLoginView.changeToAccountActivity();
        }else{
            mainLoginView.getToast("AccountID and Password incorrect");
        }
    }

    @Override
    public void sendAutoLogin(String accountID,String uuid){
        String ipAddress = "192.168.1.102";
        modelNetwork.setIPAddress(ipAddress);
        modelNetwork.createConnect();
        modelNetwork.sendDataTCP("autologin"+ "#" + accountID + "#" + uuid);
        modelNetwork.readDataTCP();
//        modelNetwork.killReadDataTCP(10000);
        String mesRecv = "";
        mesRecv = modelNetwork.getMesFromServer();
        if(mesRecv.equals("LoginSuccess")) {
            //Change activity here
            mainLoginView.changeToAccountActivity();
        }
    }
}
