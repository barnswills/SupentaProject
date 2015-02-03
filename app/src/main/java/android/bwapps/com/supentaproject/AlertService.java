package android.bwapps.com.supentaproject;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class AlertService extends Service
{
    static String logString = "";
    static boolean isServiceRunning = true;

    Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Service Started!", Toast.LENGTH_LONG).show();
        //return super.onStartCommand(intent, flags, startId);

        IntentFilter monitorAppFilter = new IntentFilter();

        final Outcome outcome = new Outcome();

        final BroadcastReceiver receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Uri uri = intent.getData();
                String appName = uri.getEncodedSchemeSpecificPart();
                outcome.SetAppNameString(appName);

                if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED))
                {
                    outcome.SetOutcomeString("installed");
                }

                if(intent.getAction().equals(Intent.ACTION_PACKAGE_CHANGED))
                {
                    outcome.SetOutcomeString("updated");
                }

                if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED))
                {
                    outcome.SetOutcomeString("uninstalled");
                }

                logString += outcome.GetAppNameString() + " has been " +
                        outcome.GetOutcomeString() + "\n";


                Toast.makeText(getBaseContext(),
                        outcome.GetAppNameString() + " has been "
                                + outcome.GetOutcomeString(),
                        Toast.LENGTH_LONG).show();
            }
        };

        // necessary intents added to the intent filter
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        monitorAppFilter.addDataScheme("package");
        registerReceiver(receiver, monitorAppFilter);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                while (isServiceRunning)
                {
                    try
                    {
                        Thread.sleep(30000);
                        handler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                CheckRAM();
                            }
                        });
                    } catch (Exception e) { e.getStackTrace(); }
                }
            }
        }).start();

        return START_STICKY;
    }

    public void CheckRAM()
    {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

        activityManager.getMemoryInfo(memoryInfo);
        long availableRAM = memoryInfo.availMem / 1048576L;

        Toast.makeText(this, "Available RAM: " + availableRAM + "MB", Toast.LENGTH_LONG).show();
        logString += "Available RAM: " + availableRAM + "MB\n";
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}
