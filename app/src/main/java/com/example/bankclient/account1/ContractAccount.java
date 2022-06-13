package com.example.bankclient.account1;

import android.app.AlertDialog;

import com.example.bankclient.Account;

public interface ContractAccount {
    interface AccountView{

        //Lấy accountID từ SharePreferences
        String getAccountIDSharePref();

        void setLblAccountName(String accountName);

        void setLblAccountBalance();

        void changeToLoginActivity();

    }

    interface EditProfileFragment{
        void setLabelNoti(String notification, int color );
    }

    interface TransferMoneyDialog {
        void setLabelNoti(String notification);
        AlertDialog getDialog();
    }


    interface AccountPresenter{
        Account getAccount();

        void getAccountFromServer();

        void editProfile(String firstName, String lastName, String address, String phoneNumber, String email, String city, String country);
        void transferMoney(String accountID2, String sMoneyTransfer);

        void removeEditProfileFragmenttt();

        void setEditProfileFragment(ContractAccount.EditProfileFragment editProfileFragment);
        void setTransferMoneyDialog(ContractAccount.TransferMoneyDialog transferMoneyDialog);

    }
}
