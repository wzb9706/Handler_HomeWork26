package com.example.wzb97.handler_homework26;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView t=(TextView)findViewById(R.id.textView2);
        Button btn1=(Button)findViewById(R.id.button);
        Button btn2=(Button)findViewById(R.id.button2);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                t.setText(msg.arg1+"");
            }
        };
        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while(progress <= 100){
                    Message msg = new Message();
                    msg.arg1 = progress;
                    handler.sendMessage(msg);
                    progress += 10;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();//同 new Message();
                msg.arg1 = -1;
                handler.sendMessage(msg);
            }
        };
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long endTime=System.currentTimeMillis()+10*1000;//10秒
                while(System.currentTimeMillis()<endTime){
                    synchronized (this){
                        try{
                            wait();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();

            }
        });
    }
}
