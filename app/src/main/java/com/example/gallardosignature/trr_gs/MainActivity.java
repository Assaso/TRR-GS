package com.example.gallardosignature.trr_gs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button randomize, registry, explore;
    TextView label;
    String capitol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomize = (Button) findViewById(R.id.Randomize);
        registry = (Button) findViewById(R.id.New_registry);
        explore = (Button) findViewById(R.id.explore);
        label = (TextView) findViewById(R.id.label);


        capitol = read_info();

        label.setText(capitol);


        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, new_Registry.class);
                startActivity(intent);
            }
        });

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String files[] = fileList();
                if (files.length == 0) {
                    Toast toast = Toast.makeText(MainActivity.this, "No items found to show", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, explore.class);
                    startActivity(intent);
                }
            }
        });

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String files[] = fileList();
                if (files.length == 0) {
                    Toast toast = Toast.makeText(MainActivity.this, "No items found to show", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, explore.class);
                    startActivity(intent);
                }
            }
        });

    }

    public String read_info() {
        String content = "";

        InputStream is = this.getResources().openRawResource(R.raw.recipies_list);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
            while ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return content;
    }
}
