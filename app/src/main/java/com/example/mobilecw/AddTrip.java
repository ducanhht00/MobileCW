package com.example.mobilecw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTrip extends AppCompatActivity {
    Database database;

    Button btnAddTrip;
    EditText txtTripName,txtDestination,txtDate,txtDuration,txtDescription,txtVehicle;
    TextView txtRiskSwitch, txtNotice;
    Switch txtRisk;
    String RiskA = "No" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);


        database = new Database(this,"tripNote.sqlite",null,1);
        // Anh xa
        txtTripName = findViewById(R.id.txtTripName2);
        txtDate=findViewById(R.id.txtDate2);
        txtDescription = findViewById(R.id.txtDescription2);
        txtDestination = findViewById(R.id.txtDestination2);
        txtDuration = findViewById(R.id.txtDuration2);
        txtRiskSwitch = findViewById(R.id.txtRiskSwitch);
        txtNotice = findViewById(R.id.txtNotice);
        txtRisk = findViewById(R.id.txtRisk2);
        btnAddTrip = findViewById(R.id.add_trip_button);
        txtVehicle = findViewById(R.id.txtVehicle2);
        // On OFF Switch cua Risk
        txtRisk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    RiskA = "Yes";
                    txtRiskSwitch.setText("Yes");
                } else {
                    RiskA = "No";
                    txtRiskSwitch.setText("No");
                }
            }
        });


        // Date picker Dialog
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDay();
            }
        });



        // An nut save
        btnAddTrip.setOnClickListener(view -> {



            if (TextUtils.isEmpty(txtTripName.getText().toString()) ||
                    TextUtils.isEmpty(txtDestination.getText().toString()) ||
                    TextUtils.isEmpty(txtDate.getText().toString()) ){
                txtNotice.setText(" Information fields: Name, Destination, Date need to be filled in.");
                txtNotice.setTextColor(Color.RED);
            }else {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
                dialogDelete.setMessage("Name : " + txtTripName.getText().toString()
                        +"\nDestination: " + txtDestination.getText().toString()
                        +"\ndate: " + txtDate.getText().toString()
                        +"\nDuration: " + txtDuration.getText().toString()
                        +"\nRisk: " + RiskA
                        +"\nDescription: " + txtDescription.getText().toString()
                        +"\nVehicle: " + txtVehicle.getText().toString() + "\nConfirm trip information ?");
                dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ten, diemden,thoigian, thoihan, ruiro,mota, phuongtien;
                        ten = txtTripName.getText().toString();
                        diemden = txtDestination.getText().toString();
                        thoigian = txtDate.getText().toString();

                        if(TextUtils.isEmpty(txtDuration.getText().toString())){
                            thoihan = "1 day";
                        } else {
                            thoihan = txtDuration.getText().toString();
                        }

                        ruiro = RiskA;

                        if(TextUtils.isEmpty(txtDescription.getText().toString())){
                            mota = "No Description";
                        } else{
                            mota = txtDescription.getText().toString();
                        }

                        if(TextUtils.isEmpty(txtVehicle.getText().toString())){
                            phuongtien = "No Information";
                        } else{
                            phuongtien = txtVehicle.getText().toString();
                        }

                        String sql;
                        sql = "INSERT INTO Trip VALUES(null, '" + ten + "','" + diemden + "','" + thoigian +
                                "','" + thoihan + "','" + ruiro + "','" + mota + "','" + phuongtien + "')";
                        //Toast.makeText(this, ten +"-" +diemden + thoigian + thoihan + ruiro + mota, Toast.LENGTH_SHORT).show();
                        database.QueryData(sql);

                        Intent backToMEnu1 = new Intent(AddTrip.this, MainActivity.class);
                        startActivity(backToMEnu1);
                    }
                });
                dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AddTrip.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogDelete.show();




            }

        });
    }

    // Hien thi dau quay ve tren thanh menu tren thanh menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.back,menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Quay ve trang chu


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.back){
            Intent backToMEnu = new Intent(AddTrip.this, MainActivity.class );
            startActivity(backToMEnu);
        }
        return super.onOptionsItemSelected(item);
    }

    private void ChooseDay(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


}