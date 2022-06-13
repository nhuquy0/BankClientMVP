package com.example.bankclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bankclient.account1.AccountActivity;
import com.example.bankclient.account1.ContractAccount;

import java.util.ArrayList;

public class EditProfileFragment extends Fragment implements ContractAccount.EditProfileFragment{

    Button btnOKEditProfile, btnCancelEditProfile;
    TextView lblAccountID, lblAccountName, lblStatusInteract;
    EditText txtFirstName, txtLastName, txtAddress, txtPhoneNumber, txtEmail;
    Spinner spinCountry, spinCity;

    private ArrayList<String> arrayCity, arrayCountry;

    private ContractAccount.AccountPresenter accountPresenter;

    private AccountActivity accountActivity;

    public EditProfileFragment(ContractAccount.AccountPresenter accountPresenter) {
        // Required empty public constructor
        this.accountPresenter = accountPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        // Inflate the layout for this fragment

        btnOKEditProfile = view.findViewById(R.id.btnOKEditProfile);
        btnCancelEditProfile = view.findViewById(R.id.btnCancelEditProfile);
        lblAccountID = view.findViewById(R.id.lblAccountID);
        lblAccountName = view.findViewById(R.id.lblAccountName);
        lblStatusInteract = view.findViewById(R.id.lblStatusInteract);
        txtFirstName = view.findViewById(R.id.txtFirstName);
        txtLastName = view.findViewById(R.id.txtLastName);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtEmail = view.findViewById(R.id.txtEmail);
        spinCountry = view.findViewById(R.id.spinCountry);
        spinCity = view.findViewById(R.id.spinCity);

        Account account = accountPresenter.getAccount();

        setAllLabel(account);

        btnOKEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = String.valueOf(txtFirstName.getText());
                String lastName = String.valueOf(txtLastName.getText());
                String address = String.valueOf(txtAddress.getText());
                String phoneNumber = String.valueOf(txtPhoneNumber.getText());
                String email = String.valueOf(txtEmail.getText());
                String city = String.valueOf(spinCity.getSelectedItem());
                String country = String.valueOf(spinCountry.getSelectedItem());
                accountPresenter.editProfile(firstName, lastName, address, phoneNumber, email, city, country);
            }
        });

        btnCancelEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xoá fragment
                accountPresenter.removeEditProfileFragmenttt();
            }
        });

        return view;
    }

    @Override
    public void setLabelNoti(String notification, int color){
        lblStatusInteract.setTextColor(color);
        lblStatusInteract.setText(notification);
    }

    private void setAllLabel(Account account){
        String[] cityStringArray = {"","Ho Chi Minh","Da Nang","Ha Noi","Ca Mau"};
        arrayCity = new ArrayList<>();
        for(String s : cityStringArray) {
            arrayCity.add(s);
        }
        String[] countryStringArray = {"","Viet Nam", "Singapore", "Thailand","China","Indonesia"};
        arrayCountry = new ArrayList<>();
        for (String s : countryStringArray) {
            arrayCountry.add(s);
        }

        ArrayAdapter arrayAdapterCity = new ArrayAdapter(accountActivity,android.R.layout.simple_spinner_item, arrayCity);
        arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(arrayAdapterCity);

        ArrayAdapter arrayAdapterCountry = new ArrayAdapter(accountActivity,android.R.layout.simple_spinner_item, arrayCountry);
        arrayAdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCountry.setAdapter(arrayAdapterCountry);

        lblAccountID.setText(account.getAccountID());
        lblAccountName.setText(account.getAccountName());
        txtFirstName.setText(account.getFirstName());
        txtLastName.setText(account.getLastName());
        txtAddress.setText(account.getAddress());
        txtPhoneNumber.setText(account.getPhoneNumber());
        txtEmail.setText(account.getEmail());

        for(int i = 0; i < cityStringArray.length; i++){
            if(cityStringArray[i].equals(account.getCity())){
                spinCity.setSelection(i);
            }
        }

        for(int i = 0; i < countryStringArray.length; i++){
            if(countryStringArray[i].equals(account.getCountry())){
                spinCountry.setSelection(i);
            }
        }
    }

//    //Khi fragment Attach vào Activity sẽ nhận được đối tượng Context là mainActivity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //context có kiểu MainActivity
        //toán tử instanceof dùng để kiểm tra context có phải là đối tượng của MainActivity hay k
        if(context instanceof AccountActivity){
            accountActivity = (AccountActivity) context;
        }
    }
}