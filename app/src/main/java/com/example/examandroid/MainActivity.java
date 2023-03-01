package com.example.examandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    AppDatabase db;
    EditText edName, edDesig, edSalary;

    Button btnAdd, btnEdit, btnDelete;
    RecyclerView rvEmployee;
    private int id;
    EmployeeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        db = AppDatabase.getAppDatabase(this);
        List<Employee> list = db.employeeDao().getAllUser();

        adapter = new EmployeeAdapter(this,this, list);
        adapter.ReloadData(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rvEmployee = findViewById(R.id.rvEmployee);
        rvEmployee.setLayoutManager(layoutManager);
        rvEmployee.setAdapter(adapter);

        //get
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        Employee user = (Employee) bundle.get("user");
        edName.setText(user.name.toString());
        edDesig.setText(user.desi.toString());
        edSalary.setText(String.valueOf(user.salary));
        id = user.id;
    }

    private void initView() {
        edName = findViewById(R.id.ed_name);
        edDesig = findViewById(R.id.ed_desi);
        edSalary = findViewById(R.id.ed_salary);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnEdit =findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }
    private void saveUser() {
        String name = edName.getText().toString();
        String desi = edDesig.getText().toString();
        float salary =   Float.parseFloat(edSalary.getText().toString());
        //Validate
        Employee user = new Employee(name, desi, salary );
        db.employeeDao().insertUser(user);
        Toast.makeText(this, "Add new user successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        getAllDB();
    }
    void getAllDB() {
        List<Employee> list = db.employeeDao().getAllUser();
        for (Employee user : list) {
            Log.d("TAG", "id: "+user.id + " - Name: " +user.name +"desi: "+ user.desi + "salary : "+user.salary);
        }
    }
    void updateDB() {
        Employee userUpdate = db.employeeDao().findUser(id);
        userUpdate.name = edName.getText().toString();
        userUpdate.desi = edDesig.getText().toString();
        userUpdate.salary = Float.parseFloat(edSalary.getText().toString());
        if(userUpdate != null){
            db.employeeDao().updateUser(userUpdate);
            Toast.makeText(this, "Update new user successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        getAllDB();

    }
    void deleteDB() {
        Employee userUpdate = db.employeeDao().findUser(id);
        if(userUpdate != null){

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Delete user")
                    .setMessage("Are you sure you want to delete this employee?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.employeeDao().deleteUser(userUpdate);
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        getAllDB();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:
                saveUser();
                break;
            case R.id.btnEdit:
                updateDB();
                break;
            case R.id.btnDelete:
                deleteDB();
                break;
            default:
                break;
        }
    }
}