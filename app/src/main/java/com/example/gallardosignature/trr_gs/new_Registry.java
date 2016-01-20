package com.example.gallardosignature.trr_gs;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class new_Registry extends AppCompatActivity{

    EditText entry_name, entry_ingredients, entry_preparation;
    Spinner entry_cost;
    Button entry_save;
    String cost_Array[];
    String entry_archive[];
    String last_line;
    String last_line_parts[];
    String entry_ingredients_stringchain;
    String order_number;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.new_registry);

        entry_name = (EditText) findViewById(R.id.recipie_entry_name);
        entry_ingredients = (EditText) findViewById(R.id.recipie_entry_ingredients);
        entry_preparation = (EditText) findViewById(R.id.recipie_entry_preparation);
        entry_save = (Button) findViewById(R.id.recipie_entry_save);
        entry_cost = (Spinner) findViewById(R.id.recipie_entry_price);

        cost_Array = new String[5];
        cost_Array[0] = "less that $50";
        cost_Array[1] = "less that $80";
        cost_Array[2] = "less that $100";
        cost_Array[3] = "less that $200";
        cost_Array[4] = "more that $200 D:!";

        ArrayAdapter<String> cost_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cost_Array);
        entry_cost.setAdapter(cost_adapter);

        entry_archive = fileList();

            try {
                InputStreamReader entry_archive_stream = new InputStreamReader(openFileInput("recipie_list.txt"));
                BufferedReader br = new BufferedReader(entry_archive_stream);
                String line;
                while ((line = br.readLine()) != null) {
                    last_line = line;
                }
                br.close();
                entry_archive_stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        entry_ingredients_stringchain = entry_ingredients.getText().toString();

        entry_ingredients.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    entry_ingredients_stringchain = entry_ingredients_stringchain + entry_ingredients.getText().toString()+"-";
                    return true;
                }
                return false;
            }
        });

        entry_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entry_name.getText().toString().equals("")||entry_ingredients.getText().toString().equals("")||entry_preparation.getText().toString().equals("")){
                    Toast toast = Toast.makeText(new_Registry.this, "Some text spaces are empty", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    last_line_parts = last_line.split(":");
                    String last_number = last_line_parts[0];
                    int last_int_number = Integer.parseInt(last_number);
                    if(last_int_number <= 9){
                        order_number = "00"+(last_int_number+1);
                    }else{
                        if(last_int_number >= 10 && last_int_number <= 99){
                            order_number = "0"+(last_int_number+1);
                        }else{
                            order_number = ""+(last_int_number+1);
                        }
                    }
                    try{
                        OutputStreamWriter entry_archive_outstream = new OutputStreamWriter(openFileOutput("recipie_list.txt", Activity.MODE_PRIVATE));
                        entry_archive_outstream.write(order_number+":"+entry_name.getText().toString()+":"+entry_cost.getSelectedItem().toString()+":"+entry_ingredients_stringchain+":"+entry_preparation.getText().toString());
                        entry_archive_outstream.flush();
                        entry_archive_outstream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Toast toast = Toast.makeText(new_Registry.this, "The recipie was been saved", Toast.LENGTH_SHORT);
                    toast.show();
                    entry_name.setText(null);
                    entry_ingredients.setText(null);
                    entry_preparation.setText(null);
                    finish();
                }
            }
        });
    }



    }

