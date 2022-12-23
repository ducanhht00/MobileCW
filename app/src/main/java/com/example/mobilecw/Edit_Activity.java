package com.example.mobilecw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Calendar;

public class Edit_Activity extends AppCompatActivity {
    Database database;
    Button btnEditTrip;
    EditText txtTripName,txtDestination,txtDate,txtDuration,txtDescription,txtVehicle;
    Switch txtRisk;
    TextView txtID, txtRiskSwitch1;
    int Id1 = 0;
    ArrayList<Trip> arrayTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        database = new Database(this,"tripNote.sqlite",null,1);

        // Anh xa

        txtRisk = findViewById(R.id.txtRisk3);
        txtRisk.setChecked(true);
        txtTripName = findViewById(R.id.txtTripName3);
        txtDate=findViewById(R.id.txtDate3);
        txtDescription = findViewById(R.id.txtDescription3);
        txtDestination = findViewById(R.id.txtDestination3);
        txtDuration = findViewById(R.id.txtDuration3);
        btnEditTrip = findViewById(R.id.edit_trip_button);
        txtID = findViewById(R.id.Id1);
        txtVehicle = findViewById(R.id.txtVehicle3);
        txtRiskSwitch1 = findViewById(R.id.txtRiskSwitch1);

        String Name1 = "A" , Destination1 = "A" , Date1 = "A" , Duration1 = "A" , Risk1 = "A" ,Description1 = "A", Vehicle1 = "A";
        Bundle bd = getIntent().getExtras();
        if(bd != null){
            Name1 = bd.getString("tripName");
            txtTripName.setText(Name1);
            Destination1 = bd.getString("tripDestination");
            txtDestination.setText(Destination1);
            Date1 = bd.getString("tripDate");
            txtDate.setText(Date1);
            Duration1 = bd.getString("tripDuration");
            txtDuration.setText(Duration1);
            Risk1 = bd.getString("tripRisk");
            txtRiskSwitch1.setText(Risk1);
            if(txtRiskSwitch1.getText() == "Yes"){
                txtRisk.setChecked(true);
            } else {
                txtRisk.setChecked(false);
            }
            Description1 = bd.getString("tripDescription");
            txtDescription.setText(Description1);

            Vehicle1 = bd.getString("tripVehicle");
            txtVehicle.setText(Vehicle1);


            Id1 = bd.getInt("tripId");
            txtID.setText(String.valueOf(Id1));
        }
        txtRisk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(txtRisk.isChecked()){
                    txtRiskSwitch1.setText("Yes");
                } else {
                    txtRiskSwitch1.setText("No");
                }
            }
        });

        btnEditTrip.setOnClickListener(view -> {
            String sql="UPDATE Trip SET TripName = '"+ txtTripName.getText() + "', " +
                    "Destination = '" +
                    txtDestination.getText()+"' , Date = '" +
                    txtDate.getText() +"' , " +
                    "Duration = '" +
                    txtDuration.getText() + "' , Risk = '" +
                    txtRiskSwitch1.getText() + "', " +
                    "Description = '" +
                    txtDescription.getText()+ "' , Vehicle = '" +
                    txtVehicle.getText() +"'  WHERE Id = '"+ Id1 + "'";
            database.QueryData(sql);


            // Back ve menu sau khi edit
            Intent backToMEnu = new Intent(Edit_Activity.this, MainActivity.class );
            startActivity(backToMEnu);


        });
        // Date picker Dialog
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDay();
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
            Intent backToMEnu = new Intent(Edit_Activity.this, MainActivity.class );
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