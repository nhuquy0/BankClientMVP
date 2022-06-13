package com.example.bankclient.account1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankclient.EditProfileFragment;
import com.example.bankclient.NetworkImpl;
import com.example.bankclient.R;
import com.example.bankclient.TransferMoneyDialog;
import com.example.bankclient.login.LoginActivity;

public class AccountActivity extends AppCompatActivity implements ContractAccount.AccountView{

    ImageView imgShowHide;
    TextView lblHello, lblAccountBalance;
    Button btnEditProfile, btnTransferMoney, btnPhoneRecharge, btnExitAccount;

    private SharedPreferences sharedPreferences;

    private ContractAccount.AccountPresenter accountPresenter;
    private TransferMoneyDialog transferMoneyDialog;
    private ContractAccount.EditProfileFragment editProfileFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnTransferMoney = findViewById(R.id.btnTransferMoney);
        btnPhoneRecharge = findViewById(R.id.btnPhoneRecharge);
        btnExitAccount = findViewById(R.id.btnExitAccount);
        lblHello = findViewById(R.id.lblHello);
        lblAccountBalance = findViewById(R.id.lblAccountBalance);
        imgShowHide = findViewById(R.id.imgShowHide);

        sharedPreferences = getSharedPreferences("DataPreferences", MODE_PRIVATE);

        fragmentManager = getSupportFragmentManager();

        accountPresenter = new AccountPresenter(AccountActivity.this, new NetworkImpl() ,fragmentManager);
        accountPresenter.getAccountFromServer();

        setLblAccountName(accountPresenter.getAccount().getAccountName());
        imgShowHide.setTag("Hide");

        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editProfileFragment = new EditProfileFragment(accountPresenter);
                accountPresenter.setEditProfileFragment(editProfileFragment);
                fragmentManager.beginTransaction().add(R.id.editProfileFragment, (Fragment) editProfileFragment).commit();
            }
        });

        btnTransferMoney.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                transferMoneyDialog = new TransferMoneyDialog(accountPresenter);
                accountPresenter.setTransferMoneyDialog(transferMoneyDialog);
                transferMoneyDialog.show(fragmentManager,"TransferMoneyDialog");

            }
        });
//
//        btnPhoneRecharge.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//
//            }
//        });
//
        btnExitAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteAccountIDPreference();
                changeToLoginActivity();
            }
        });

        imgShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgShowHideTag = (String) imgShowHide.getTag();
                if(imgShowHideTag.equals("Hide")) {

                    imgShowHide.setImageResource(R.drawable.eye_show);
                    lblAccountBalance.setText(String.valueOf(accountPresenter.getAccount().getAccountBalance()));
                    imgShowHide.setTag("Show");
                }else{
                    imgShowHide.setImageResource(R.drawable.eye_hide);
                    lblAccountBalance.setText("******");
                    imgShowHide.setTag("Hide");
                }
            }
        });
    }

    private void deleteAccountIDPreference(){
        sharedPreferences = getSharedPreferences("DataPreferences", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("accountID",null);
        edit.commit();
    }

    @Override
    public void changeToLoginActivity() {
        Intent it = new Intent(AccountActivity.this, LoginActivity.class);
        startActivity(it);
        Log.v("AccountActivity","Changed activity");
        finish();
    }

    @Override
    public String getAccountIDSharePref(){
        String accountID = sharedPreferences.getString("accountID", null);
        return accountID;
    }

    @Override
    public void setLblAccountName(String accountName){
        lblHello.setText("Xin chào " + accountName);
    }

    @Override
    public void setLblAccountBalance(){
        String imgShowHideTag = (String) imgShowHide.getTag();
        if(imgShowHideTag.equals("Show")) {
            lblAccountBalance.setText(String.valueOf(accountPresenter.getAccount().getAccountBalance()));
        }
    }
}