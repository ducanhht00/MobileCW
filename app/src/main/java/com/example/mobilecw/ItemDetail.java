package com.example.mobilecw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemDetail extends AppCompatActivity {
    TextView txtTripName,txtDestination,txtDate,txtDuration,txtRisk,txtDescription,txtVehicle;

    Database database;
    ListView lvExpensive;
    ArrayList<Expensive> arrayExpensive;
    ExpensiveAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        // Anh xa
        txtTripName = findViewById(R.id.DetailName);
        txtDestination = findViewById(R.id.DetaiDestination);
        txtDate = findViewById(R.id.DetailDate);
        txtDuration = findViewById(R.id.DetailDuration);
        txtRisk = findViewById(R.id.DetailRisk);
        txtDescription = findViewById(R.id.DetailDescription);
        txtVehicle = findViewById(R.id.DetailVehicle);
        // SET FOR LIST VIEW
        lvExpensive = findViewById(R.id.list_expensive);
        arrayExpensive = new ArrayList<>();
        adapter = new ExpensiveAdapter(this,R.layout.expensive_item,arrayExpensive);
        lvExpensive.setAdapter(adapter);
        // database
        database = new Database(this,"tripNote.sqlite",null,1);
        database.QueryData("Create TABLE IF NOT EXISTS Expensive" +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT,TripId INTEGER, ExpensiveName VARCHAR(200),Amount VARCHAR(200), " +
                "Date VARCHAR(100) ,Comment VARCHAR(200) )");
        int idItem = 0;
        // Set up data cho item tren list view va item detail tu trip
        Bundle bd = getIntent().getExtras();
        if(bd != null){
            String Name = bd.getString("tripName");
            txtTripName.setText("Trip Name: "+Name);

            String destination = bd.getString("tripDestination");
            txtDestination.setText("Destination: "+destination);

            String date = bd.getString("tripDate");
            txtDate.setText("Date: "+date);

            String duration = bd.getString("tripDuration");
            txtDuration.setText("Duration: "+duration);

            String risk = bd.getString("tripRisk");
            txtRisk.setText("Risk: "+risk);

            String description = bd.getString("tripDescription");
            txtDescription.setText("Description: "+description);

            String vehicle = bd.getString("tripVehicle");
            txtVehicle.setText("Vehicle: "+vehicle);

            idItem = bd.getInt("TripId");
        }

        //database.QueryData("INSERT INTO Expensive VALUES(null," + idItem + ", 'Tien di taxi','ha Noi','2/2/2222')");
        //database.QueryData("INSERT INTO Expensive VALUES(null," + idItem + ", 'Tien di taxi test','hai phong','2/2/2222')");
        // arrayExpensive.add(new Expensive(1,"Aloha","Social","22/11"));
        // lay du lieu cho bang expensive
        Cursor dataExpensive = database.GetData("SELECT * FROM Expensive WHERE TripId = '"+ idItem +"'");
        while (dataExpensive.moveToNext()){
            int id = dataExpensive.getInt(0);
            String ten = dataExpensive.getString(2);
            String amount = dataExpensive.getString(3);
            String Date = dataExpensive.getString(4);
            String Comment = dataExpensive.getString(5);
            arrayExpensive.add(new Expensive(id,ten,amount,Date,Comment));
        }
    }

    // Show cac icon trên thanh menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_expensive,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Quay ve trang chu + add expensive


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.retur_main){
            Intent backToMEnu = new Intent(ItemDetail.this, MainActivity.class );
            startActivity(backToMEnu);
        } else if(item.getItemId() == R.id.add_expensive){
            DialogThemExpensive();
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogThemExpensive(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_expensive);
        EditText txtNameExpensive, txtAmount, txtDateExpensive, txtComment;
        Button btnAddExpenisve, btnCancel;
        txtNameExpensive = dialog.findViewById(R.id.txtAddNameExpensive);
        txtAmount = dialog.findViewById(R.id.txtAddAmount);
        txtDateExpensive = dialog.findViewById(R.id.txtAddDate);
        btnAddExpenisve = dialog.findViewById(R.id.add_expensive);
        btnCancel = dialog.findViewById(R.id.cancel_expensive);
        txtComment = dialog.findViewById(R.id.txtAddComment);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        btnAddExpenisve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bd = getIntent().getExtras();
                int idItem = 0;
                if(bd != null){

                    idItem = bd.getInt("TripId");
                }

                String expensiveName = txtNameExpensive.getText().toString();
                String Amount = txtAmount.getText().toString();
                String Date = txtDateExpensive.getText().toString();
                String Comment1 = txtComment.getText().toString();

                if (expensiveName.equals("") | Amount.equals("") | Date.equals("")){
                    Toast.makeText(ItemDetail.this, "Please fill out completely", Toast.LENGTH_SHORT).show();
                } else{
                    database.QueryData("INSERT INTO Expensive VALUES(null," + idItem + ", '"+expensiveName +"','" + Amount + "','"+ Date + "','" + Comment1 +"')");
                    dialog.dismiss();
                    GetDataExpensive();
                    Toast.makeText(ItemDetail.this,"Add new expensive complete",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }



    // Delete Expensive
    public void DialogDeleteExpensive(String expensiveName,int expensiveId){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Are you sure ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sql;
                sql = "DELETE FROM Expensive WHERE Id = '" + expensiveId + "'";
                database.QueryData(sql);
                Toast.makeText(ItemDetail.this, "Done", Toast.LENGTH_SHORT).show();
                GetDataExpensive();
            }
        });
        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ItemDetail.this, "Huy xoa", Toast.LENGTH_SHORT).show();
            }
        });
        dialogDelete.show();
    }

    // Get data
    private void GetDataExpensive(){

        Bundle bd = getIntent().getExtras();
        int idItem = 0;
        if(bd != null){

             idItem = bd.getInt("TripId");
        }
        Cursor dataExpensive = database.GetData("SELECT * FROM Expensive WHERE TripId = '"+ idItem +"'");
        arrayExpensive.clear();
        while (dataExpensive.moveToNext()){
            int id = dataExpensive.getInt(0);
            String ten = dataExpensive.getString(2);
            String amount = dataExpensive.getString(3);
            String Date = dataExpensive.getString(4);
            String Comment = dataExpensive.getString(4);
            arrayExpensive.add(new Expensive(id,ten,amount,Date,Comment));
        }
        adapter.notifyDataSetChanged();
    }

}