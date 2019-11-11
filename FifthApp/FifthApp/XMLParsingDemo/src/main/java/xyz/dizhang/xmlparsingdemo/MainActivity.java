package xyz.dizhang.xmlparsingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import xyz.dizhang.xmlparsingdemo.XmlParserDemo.DomParserDemo;
import xyz.dizhang.xmlparsingdemo.XmlParserDemo.PullParserDemo;
import xyz.dizhang.xmlparsingdemo.XmlParserDemo.SAXParserDemo;

public class MainActivity extends AppCompatActivity {

    private Button btnSax;
    private Button btnPull;
    private Button btnDom;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSax = findViewById(R.id.btnSAX);
        btnPull = findViewById(R.id.btnPull);
        btnDom = findViewById(R.id.btnDom);

        btnSax.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SAXParserDemo.class);
                startActivity(intent);
            }
        });

        btnPull.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PullParserDemo.class);
                startActivity(intent);
            }
        });

        btnDom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DomParserDemo.class);
                startActivity(intent);
            }
        });

    }
}
