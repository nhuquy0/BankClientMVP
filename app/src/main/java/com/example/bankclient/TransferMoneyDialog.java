package com.example.bankclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.bankclient.account1.ContractAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class TransferMoneyDialog extends DialogFragment implements ContractAccount.TransferMoneyDialog {

    private EditText txtAccountID2, txtMoneyTransfer;
    private TextView lblStatusTransferMoney;

    private ContractAccount.AccountPresenter accountPresenter;

    AlertDialog dialog;

    public TransferMoneyDialog(ContractAccount.AccountPresenter accountPresenter){

        this.accountPresenter = accountPresenter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_transfer_money, null);

        txtAccountID2 = view.findViewById(R.id.txtAccountID2);
        txtMoneyTransfer = view.findViewById(R.id.txtMoneyTransfer);
        lblStatusTransferMoney = view.findViewById(R.id.lblStatusTransferMoney);

        txtMoneyTransfer.setTransformationMethod(null);
        //Phải tạo TextWatcher riêng, rồi bỏ vào edittext.addTextChangedListener(textWatcher)
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String current = "";
                if (!editable.toString().equals(current)) {
                    txtMoneyTransfer.removeTextChangedListener(this);
                    String originalString = txtMoneyTransfer.getText().toString();
                    if (originalString.isEmpty()) {
                        return;
                    }
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("\\.", "");
                    }
                    BigDecimal bd = new BigDecimal(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

                    symbols.setGroupingSeparator('.');
                    formatter.setDecimalFormatSymbols(symbols);

                    String formatted = formatter.format(bd);

                    current = formatted;
                    txtMoneyTransfer.setText(formatted);
                    txtMoneyTransfer.setSelection(formatted.length());
                    txtMoneyTransfer.addTextChangedListener(this);
                }
            }
        };
        txtMoneyTransfer.addTextChangedListener(textWatcher);
        builder.setView(view).setTitle("Transfer Money");

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//                String accountID2 = String.valueOf(txtAccountID2.getText());
//                String sMoneyTransfer =String.valueOf(txtMoneyTransfer.getText());
//                if(accountID2.equals("") || sMoneyTransfer.equals("")){
//                    lblStatusTransferMoney.setText("Please input info transfer");
////                    dialog.dismiss();
//                    return;
//                }
//                BigDecimal moneyTransfer = new BigDecimal(sMoneyTransfer);
//
//                if(moneyTransfer.compareTo(new BigDecimal("10000")) <0){
//                    lblStatusTransferMoney.setText("Money transfer >= 10000");
//                }
//
//                if(moneyTransfer.compareTo(account.getAccountBalance()) <0){
//                    lblStatusTransferMoney.setText("The account does not have enough money");
//                }
//
//                NetworkImpl modelNetwork = new NetworkImpl();
//                modelNetwork.createConnect();
//                modelNetwork.sendDataTCP("transfermoney#" + account.getAccountID() + "#" + txtAccountID2 + "#" + moneyTransfer);
//                modelNetwork.readDataTCP();
//                String mesRecv = "";
//                mesRecv = modelNetwork.getMesFromServer();
//                if(mesRecv.equals("transfermoneysuccess")){
//                    //Cập nhật lại Account trong AccountActivity
//                    accountActivity.setAccount(account);
////                    accountActivity.setLblAccountBalance();
//                    //Dismiss once everything is OK.
//                    dialog.dismiss();
//                }else if(mesRecv.equals("transfermoneyWrongID")){
//                    lblStatusTransferMoney.setText("accountID incorrect");
//                    return;
//                }
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();

        //Override button positive để Dialog không biến mất khi nhấn Ok
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                User clicked OK button
                String accountID2 = String.valueOf(txtAccountID2.getText());
                String sMoneyTransfer =String.valueOf(txtMoneyTransfer.getText());
                lblStatusTransferMoney.setTextColor(Color.RED);
                accountPresenter.transferMoney(accountID2, sMoneyTransfer);
            }
        });
        return dialog;
    }

    @Override
    public void setLabelNoti(String notification){
        lblStatusTransferMoney.setText(notification);
    }

    @Override
    public AlertDialog getDialog(){
        return dialog;
    }


    //
//    //Khi fragment Attach vào Activity sẽ nhận được đối tượng Context là mainActivity
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        //context có kiểu MainActivity
//        //toán tử instanceof dùng để kiểm tra context có phải là đối tượng của MainActivity hay k
//        if(context instanceof AccountActivity){
//            accountActivity = (AccountActivity) context;
//        }
//    }
}
