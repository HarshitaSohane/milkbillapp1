package com.example.milkbill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button b4;
    TextView tv;
    Button b1;
    EditText e1, e2, e3;
    milkdb mb;
Button b3;
Button b5,b6;
Button b2;
DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mb = new milkdb(this);
        b4 = findViewById(R.id.button4);
        tv = findViewById(R.id.editTextDate);
      final Calendar myCalendar = Calendar.getInstance();
    /*    String currentDate = DateFormat.getDateInstance().format(myCalendar.getTime());
        tv.setText(currentDate);*/
    int yy,mm,dd;
        final Calendar c = Calendar.getInstance();
        yy = c.get(Calendar.YEAR);
        mm = c.get(Calendar.MONTH);
        dd = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tv.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(dd).append(" /").append(mm + 1).append("/")
                .append(yy));


                    tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(MainActivity.this,date,  myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
          date = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }


            private void updateLabel() {
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tv.setText(sdf.format(myCalendar.getTime()));
            }
        };


        e1 = findViewById(R.id.editTextDate);
        e2 = findViewById(R.id.editTextNumber);
        e3 = findViewById(R.id.editTextNumber2);
        b1 = findViewById(R.id.button);
        b3=findViewById(R.id.button3);
        b5=findViewById(R.id.button5);
        b6=findViewById(R.id.button6);
        b2=findViewById(R.id.button2);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Adddata();
        viewAll();
        DeleteData();
        UpdateData();
        delete();
    }

    public void Adddata() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         try {
             boolean isInserted = mb.insertData(tv.getText().toString(), Float.parseFloat(String.valueOf(e2.getText())), Float.parseFloat(e3.getText().toString()));
             if (isInserted == true)
                 Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
             else
                 Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
         }
         catch(Exception e)
         {
             Toast.makeText(MainActivity.this,"Please enter the quantity",Toast.LENGTH_LONG).show();
         }
            }
        });
    }
    public void delete()
    {
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mb.del();
                Toast.makeText(MainActivity.this,"Complete Record Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll() {
        b3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = mb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Date :"+ res.getString(0)+"\n");
                            buffer.append("Cow_Qty :"+ res.getString(1)+"\n");
                            buffer.append("Buff_Qty :"+ res.getString(2)+"\n");
                            System.out.println();
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void DeleteData() {
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = mb.deleteData(tv.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        b6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = mb.updateData(tv.getText().toString(),
                                Float.parseFloat(e2.getText().toString()), Float.parseFloat(( e3.getText().toString())));
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}