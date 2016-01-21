package com.example.gallardosignature.trr_gs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button randomize, registry;
    TextView label;
    String capitol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomize = (Button)findViewById(R.id.Randomize);
        registry =  (Button)findViewById(R.id.New_registry);
        label = (TextView)findViewById(R.id.label);



        capitol = read_info();

        label.setText(capitol);

        String path = getFilesDir().toString()+"/recipie_list001.txt";
        File file = new File(path);
        if(file.exists()){
        }else {
            try {
                OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("recipie_list001.txt", Context.MODE_PRIVATE));
                osw.write("001:");
                osw.close();
            } catch (Exception exe) {
                Log.e("Error", "at write file");
            }
        }

        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, new_Registry.class);
                startActivity(intent);
            }
        });

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, randomize.class);
                startActivity(intent);
            }
        });

    }

    public String read_info(){
        String content = "";

        InputStream is = this.getResources().openRawResource(R.raw.recipies_list);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
            while((line = br.readLine()) != null){
                content = line;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            is.close();
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return content;
    }
}
