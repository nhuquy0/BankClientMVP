package com.example.bankclient.login;

public interface ContractLogin {

    interface LoginView{
        //Lây IP từ View
        String getIPAddress();

        //Lấy thông báo Toast từ View
        void getToast(String notification);

        //Lưu username vào SharedPreferences
        void saveAccountIDPreference(String accountID);

        //Thay đổi qua AccountActivity
        void changeToAccountActivity();

        //Lấy uuid từ SharePreferences
        String getUUIDSharePref();
    }

    interface LoginPresenter{
        //Xử lý khi nhấn Login
        void sendLogin(String UUID, String username, String password);

        //AutoLogin khi vào ứng dụng
        void sendAutoLogin(String username,String uuid);
    }
}
