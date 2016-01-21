package com.example.gallardosignature.trr_gs;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class new_Registry extends AppCompatActivity {

    EditText entry_name, entry_preparation, entry_add_ingredient;
    TextView entry_ingredients;
    Spinner entry_cost;
    Button entry_save, entry_add;
    String cost_Array[];
    String entry_archive[];
    String new_ingredient;
    String total_ingredients;
    String file_number;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.new_registry);

        entry_name = (EditText) findViewById(R.id.recipie_entry_name);
        entry_ingredients = (TextView) findViewById(R.id.recipie_entry_ingredients);
        entry_preparation = (EditText) findViewById(R.id.recipie_entry_preparation);
        entry_add_ingredient = (EditText) findViewById(R.id.recipie_entry_add_i);
        entry_save = (Button) findViewById(R.id.recipie_entry_save);
        entry_add = (Button) findViewById(R.id.recipie_entry_add);
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
        int start_number = entry_archive.length;

        if (start_number <= 9) {
            file_number = "00" + start_number;
        } else {
            if (start_number >= 10 && start_number <= 99) {
                file_number = "0" + start_number;
            } else {
                file_number = "" + start_number;
            }
        }


        entry_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entry_add_ingredient.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(new_Registry.this, "Nothing to add", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    new_ingredient = entry_add_ingredient.getText().toString();
                    if (entry_ingredients.getText().toString().equals("")) {
                        entry_ingredients.setText(new_ingredient + "\n");
                        total_ingredients = new_ingredient + "-";
                        entry_add_ingredient.setText(null);
                    } else {
                        entry_ingredients.setText(entry_ingredients.getText().toString() + new_ingredient + "\n");
                        total_ingredients = total_ingredients + new_ingredient + "-";
                        entry_add_ingredient.setText(null);
                    }
                }
            }
        });

        entry_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entry_name.getText().toString().equals("") || entry_ingredients.getText().toString().equals("") || entry_preparation.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(new_Registry.this, "Some text spaces are empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    try {
                        OutputStreamWriter entry_archive_outstream = new OutputStreamWriter(openFileOutput("recipie_list" + file_number + ".txt", Activity.MODE_PRIVATE));
                        entry_archive_outstream.write(file_number + ":" + entry_name.getText().toString() + ":" + entry_cost.getSelectedItem().toString() + ":" + total_ingredients + ":" + entry_preparation.getText().toString());
                        entry_archive_outstream.flush();
                        entry_archive_outstream.close();
                    } catch (IOException e) {
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

