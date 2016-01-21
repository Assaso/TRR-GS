package com.example.gallardosignature.trr_gs;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class explore extends AppCompatActivity{

    Spinner name_list;
    Button search;
    TextView found_name, found_cost, found_ingredients, found_preparation;
    String recipie_number[];
    String recipie_array[];
    String file_number;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.explore);

        search = (Button)findViewById(R.id.recipie_search);
        found_name = (TextView)findViewById(R.id.found_name);
        found_cost = (TextView)findViewById(R.id.found_cost);
        found_ingredients = (TextView)findViewById(R.id.found_ingredients);
        found_preparation = (TextView)findViewById(R.id.found_preparation);
        name_list = (Spinner)findViewById(R.id.recipie_list);

        recipie_number = fileList();
        int total_recipie = recipie_number.length;
        recipie_array = new String[total_recipie];

        for(int i = 0; i <= (total_recipie-1); i++){
            if (i <= 9){
                file_number = "00"+i;
            }else{
                if(i >= 10 && i  <= 99){
                    file_number = "0"+i;
                }else{
                    file_number = ""+i;
                }
            }

            InputStream isr = null;
            try {
                isr = this.openFileInput("recipie_list" + file_number + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(isr));

            String line;
            String content;
            String parts[];
            String read_name = "";

            try{
                while((line = br.readLine()) != null){
                    content = line;
                    parts = content.split(":");
                    read_name = parts[1];
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                isr.close();
                br.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }

            recipie_array[i] = read_name;
        }

        ArrayAdapter<String> recipie_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipie_array);
        name_list.setAdapter(recipie_adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(name_list.getSelectedItemPosition() <= 9){
                     file_number = "00"+name_list.getSelectedItemPosition();
                 }else{
                     if(name_list.getSelectedItemPosition() >= 10 && name_list.getSelectedItemPosition() <= 99){
                         file_number = "0"+name_list.getSelectedItemPosition();
                     }else{
                         file_number = ""+name_list.getSelectedItemPosition();
                     }
                 }

                InputStream is = null;
                try{
                    is = explore.this.openFileInput("recipie_list" + file_number + ".txt");
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line_complete;
                String content_show;
                String parts_show[] = new String[0];

                try{
                    while((line_complete = reader.readLine()) != null){
                        content_show = line_complete;
                        parts_show = content_show.split(":");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                try{
                    is.close();
                    reader.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }

                found_name.setText(parts_show[1]);
                found_cost.setText(parts_show[2]);
                String ingredients_found[] = parts_show[3].split("\\-");
                String ingredients_constructor = "";
                for(int j = 0; j <= (ingredients_found.length-1); j++){
                    ingredients_constructor = ingredients_constructor + ingredients_found[j]+"\n";
                }
                found_ingredients.setText(ingredients_constructor);
                found_preparation.setText(parts_show[4]);
            }
        });

    }
}
