package me.chayut.santaslittlehelper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import me.chayut.SantaHelperLogic.SantaHelperLogic;

public class SantaService extends Service {

    private final static String TAG = "SantaService";

    private SantaHelperLogic mSantaLogic;
    private boolean mLogicInitialized = false;


    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    /** Constructor */
    public SantaService() {
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        SantaService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SantaService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public void onCreate() {
        Log.i(TAG, "Service onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand()");

        if (intent.hasExtra("Extra")){
            Log.i(TAG, "Has Extra");
        }

        mSantaLogic  = new SantaHelperLogic();
        mLogicInitialized = true;

        return 0;
    }

    /** method for clients */
    public String getHello(){
        return "Hello";
    }

    public SantaHelperLogic getSantaLogic(){
        if (!mLogicInitialized) {
            mSantaLogic = new SantaHelperLogic();
        }
        return mSantaLogic;
    }

}


