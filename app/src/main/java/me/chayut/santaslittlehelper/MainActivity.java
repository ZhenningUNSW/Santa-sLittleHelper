package me.chayut.santaslittlehelper;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.chayut.SantaHelperLogic.EndPoint;
import me.chayut.SantaHelperLogic.SantaHelperLogic;
import me.zhenning.EmailSender;


public class MainActivity extends AppCompatActivity {


    private final static String TAG = "MainActivity";

    SantaService mService;
    SantaHelperLogic mLogic;
    boolean mBound = false;

    Button button1;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);


        EndPoint mEndpoint = new EndPoint(EndPoint.TYPE_EMAIL, "HELLO", "HELLO@ITS.ME");

        Intent intent = new Intent(MainActivity.this, SantaService.class);
        intent.putExtra("Extra", mEndpoint);
        startService(intent);

        button1 = (Button) findViewById(R.id.btnTest1);
        sendEmail = (Button) findViewById(R.id.btnSendEmail);

        sendEmail.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                    // TODO Auto-generated method stub
                        sendEmail.setText("Sended");
                        try {
                            EmailSender sender = new EmailSender("zhenning.jiang2014@gmail.com", "");
                            sender.sendMail("This is Subject",
                                    "This is Body",
                                    "zhenning.jiang2014@gmail.com",
                                    "zhenning.jiang2014@gmail.com");
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                 }
        );
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(TAG, "onResume()");


        if(mBound){
            Log.d(TAG,mService.getHello());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, SantaService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SantaService.LocalBinder binder = (SantaService.LocalBinder) service;
            mService = binder.getService();
            mLogic = mService.getSantaLogic();
            mBound = true;


            Log.d(TAG,mService.getHello());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    /** */

    public void onBtnClicked (View v){
        Log.d(TAG,"onBtnClicked");

        Intent intent = new Intent(this, manageEndpointActivity.class);
        startActivity(intent);
    }

}
