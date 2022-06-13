package com.example.bankclient.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankclient.account1.AccountActivity;
import com.example.bankclient.NetworkImpl;
import com.example.bankclient.R;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements ContractLogin.LoginView{

    private String uuid;
    private String accountID;
    private String IPAddress;

    TextView lblRegister, lblForgetPassword;
    EditText txtAccountID, txtPassword, txtIPAddress;
    Button btnLogin, btnShowHide;

    private SharedPreferences sharedPreferences;

    ContractLogin.LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("DataPreferences", MODE_PRIVATE);
        createUUID();
        uuid = sharedPreferences.getString("UUID", null);
        accountID = sharedPreferences.getString("accountID",null);

        lblRegister = findViewById(R.id.lblRegister);
        lblForgetPassword = findViewById(R.id.lblForgetPassword);
        txtAccountID = findViewById(R.id.txtAccountID);
        txtPassword = findViewById(R.id.txtPassword);
        txtIPAddress = findViewById(R.id.txtIPAddress);
        btnLogin = findViewById(R.id.btnLogin);
        btnShowHide = findViewById(R.id.btnShowHide);

        loginPresenter = new LoginPresenter(LoginActivity.this, new NetworkImpl());

        if(uuid != null && accountID != null){
            loginPresenter.sendAutoLogin(accountID, uuid);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountID = String.valueOf(txtAccountID.getText());
                String password = String.valueOf(txtPassword.getText());
                IPAddress = String.valueOf(txtIPAddress.getText());
                loginPresenter.sendLogin(uuid,accountID,password);
            }
        });

        btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnShowHide.getText().toString().equals("Show")){
                    txtPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                    btnShowHide.setText("Hide");
                } else{
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    btnShowHide.setText("Show");
                }
            }
        });
    }

    @Override
    public String getIPAddress() {
        return IPAddress;
    }

    @Override
    public void getToast(String notification) {
        Toast.makeText(LoginActivity.this, notification, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveAccountIDPreference(String accountID){
        sharedPreferences = getSharedPreferences("DataPreferences", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("accountID",accountID);
        edit.commit();
    }

    private void createUUID(){
        //This method only run a times
        if (!sharedPreferences.getBoolean("FrstTime", false)) {
            // Do update you want here
            uuid = UUID.randomUUID().toString();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("FrstTime", true);
            edit.putString("UUID",uuid);
            edit.commit();
        }
    }

    @Override
    public void changeToAccountActivity() {
        Intent it = new Intent(LoginActivity.this, AccountActivity.class);
        startActivity(it);
        Log.v("LoginActivity","Changed activity");
        finish();
    }

    @Override
    public String getUUIDSharePref(){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String uuid = sharedPreferences.getString("UUID", null);
//        uuid = sharedPreferences.getString("UUID", null);
        return uuid;
    }
}