package com.example.gallardosignature.trr_gs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class randomize extends AppCompatActivity{

    String recipie_number[];
    String file_number;
    TextView random_name, random_cost, random_ingredients, random_preparation;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.randomize);

        random_name = (TextView)findViewById(R.id.random_name);
        random_cost = (TextView)findViewById(R.id.random_cost);
        random_ingredients = (TextView)findViewById(R.id.random_ingredients);
        random_preparation = (TextView)findViewById(R.id.random_preparation);

        recipie_number = fileList();
        Random r = new Random();
        int random_recipie = r.nextInt(recipie_number.length - 0)+0;

        if(random_recipie <= 9){
            file_number = "00"+random_recipie;
        }else{
            if(random_recipie >= 10 && random_recipie <= 99){
                file_number = "0"+random_recipie;
            }else{
                file_number = ""+random_recipie;
            }
        }

        InputStream is = null;
        try{
            is = this.openFileInput("recipie_list"+file_number+".txt");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        String content;
        String parts[] = new String[0];

        try{
            while((line = br.readLine()) != null){
                content = line;
                parts = content.split(":");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            is.close();
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }

        random_name.setText(parts[1]);
        random_cost.setText(parts[2]);
        String ingredients_found[] = parts[3].split("\\-");
        String ingredients_constructor = "";
        for(int i = 0; i <= (ingredients_found.length-1); i++){
            ingredients_constructor = ingredients_constructor+ingredients_found[i]+"\n";
        }
        random_ingredients.setText(ingredients_constructor);
        random_preparation.setText(parts[4]);
    }
}
