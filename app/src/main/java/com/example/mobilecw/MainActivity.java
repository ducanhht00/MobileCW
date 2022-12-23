package com.example.mobilecw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    SearchView searchBar;
    ListView lvTrip;
    ArrayList<Trip> arrayTrip;
    TripAdapter adapter;
    Button btnDeleteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Anh xa
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        searchBar = findViewById(R.id.search_Trip);
        lvTrip = findViewById(R.id.lvTrip);
        arrayTrip =new ArrayList<>();
        adapter = new TripAdapter(this, R.layout.trip_item,arrayTrip);
        lvTrip.setAdapter(adapter);

        database = new Database(this,"tripNote.sqlite",null,1);
        // Tao bang cong viec
        database.QueryData("Create TABLE IF NOT EXISTS Trip" +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT, TripName VARCHAR(200),Destination VARCHAR(200), " +
                "Date VARCHAR(100), Duration VARCHAR(100), Risk VARCHAR(100), Description VARCHAR(1000), Vehicle VARCHAR(1000) )");
        // insert base data

        //database.QueryData("INSERT INTO Trip VALUES(null, 'Chuyen Di Sai Gon','Sai Gon','3/3/2222','10 Day','Yes','con cac')");
        // Lay du lieu
        Cursor dataTrip = database.GetData("SELECT * FROM Trip");

        while (dataTrip.moveToNext()){
            String Ten = dataTrip.getString(1);
            int Id = dataTrip.getInt(0);
            String Destination = dataTrip.getString(2);
            String Date = dataTrip.getString(3);
            String Duration = dataTrip.getString(4);
            String Risk = dataTrip.getString(5);
            String Description = dataTrip.getString(6);
            String Vehicle = dataTrip.getString(7);
            arrayTrip.add(new Trip(Id,Ten,Destination,Date,Duration,Risk,Description,Vehicle));
        }


        lvTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent mh2 = new Intent(MainActivity.this, ItemDetail.class );
                mh2.putExtra("TripId",arrayTrip.get(i).getId());
                mh2.putExtra("tripName",arrayTrip.get(i).getTripName());
                mh2.putExtra("tripDestination",arrayTrip.get(i).getDestination());
                mh2.putExtra("tripDate",arrayTrip.get(i).getDate());
                mh2.putExtra("tripDuration",arrayTrip.get(i).getDuration());
                mh2.putExtra("tripRisk",arrayTrip.get(i).getRisk());
                mh2.putExtra("tripDescription",arrayTrip.get(i).getDescription());
                mh2.putExtra("tripVehicle",arrayTrip.get(i).getVehicle());
                startActivity(mh2);
            }
        });
        // Delete all
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeleteAllTrip();
            }
        });

        // search view
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                    Cursor dataTrip1 = database.GetData("SELECT * FROM Trip WHERE TripName LIKE '%" +query+ "%'");
                    arrayTrip.clear();
                    while (dataTrip1.moveToNext()){
                        String Ten = dataTrip1.getString(1);
                        int Id = dataTrip1.getInt(0);
                        String Destination = dataTrip1.getString(2);
                        String Date = dataTrip1.getString(3);
                        String Duration = dataTrip1.getString(4);
                        String Risk = dataTrip1.getString(5);
                        String Description = dataTrip1.getString(6);
                        String Vehicle = dataTrip1.getString(7);

                        arrayTrip.add(new Trip(Id,Ten,Destination,Date,Duration,Risk,Description,Vehicle));
                    }
                return false;
            }
            //view

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    Cursor dataTrip1 = database.GetData("SELECT * FROM Trip");
                    arrayTrip.clear();
                    while (dataTrip1.moveToNext()){
                        String Ten = dataTrip1.getString(1);
                        int Id = dataTrip1.getInt(0);
                        String Destination = dataTrip1.getString(2);
                        String Date = dataTrip1.getString(3);
                        String Duration = dataTrip1.getString(4);
                        String Risk = dataTrip1.getString(5);
                        String Description = dataTrip1.getString(6);
                        String Vehicle = dataTrip1.getString(7);

                        arrayTrip.add(new Trip(Id,Ten,Destination,Date,Duration,Risk,Description,Vehicle));
                    }
                }
                return false;
            }
        });
    }
    // Delete Trip
    public void DialogDeleteTrip(String tripName,int tripId){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Are you sure ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sql;
                sql = "DELETE FROM Trip WHERE Id = '" + tripId + "'";
                database.QueryData(sql);
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                GetDataTrip();
            }
        });
        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialogDelete.show();
    }
    // Delete All Trip Dialog
    public void DialogDeleteAllTrip(){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Are you sure ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sql;
                String sql1;
                sql = "DELETE FROM Trip ";
                sql1 = "DELETE FROM Expensive ";
                database.QueryData(sql);
                database.QueryData(sql1);
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                GetDataTrip();
            }
        });
        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialogDelete.show();
    }
    // Hien thi dau cong trip tren thanh menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_trip,menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Chuc nang them trip
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_trip){
            Intent addtrip = new Intent(MainActivity.this, AddTrip.class );
            startActivity(addtrip);
        }
        return super.onOptionsItemSelected(item);
    }

    // Reload Data
    private void GetDataTrip(){
        // select data
        Cursor dataTrip = database.GetData("SELECT * FROM Trip");
        arrayTrip.clear();
        while (dataTrip.moveToNext()){
            String Ten = dataTrip.getString(1);
            int Id = dataTrip.getInt(0);
            String Destination = dataTrip.getString(2);
            String Date = dataTrip.getString(3);
            String Duration = dataTrip.getString(4);
            String Risk = dataTrip.getString(5);
            String Description = dataTrip.getString(6);
            String Vehicle = dataTrip.getString(7);
            arrayTrip.add(new Trip(Id,Ten,Destination,Date,Duration,Risk,Description,Vehicle));
        }
        adapter.notifyDataSetChanged();

    }
}