package android.bwapps.com.supentaproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private Button serviceButton;

    public TextView activityLog;

    AlertService alertService = new AlertService();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceButton = (Button) findViewById(R.id.service_button);
        serviceButton.setTag(1);

        startService(new Intent(getBaseContext(), AlertService.class));

        activityLog = (TextView)findViewById(R.id.activity_log);
    }

    @Override
    protected void onResume()
    {
        activityLog.setText(alertService.logString);
        super.onResume();
    }

    public void onServiceClick(View view)
    {
        final int status = (Integer) view.getTag();

        if (status == 1)
        {
            serviceButton.setText(R.string.button_text_start);
            stopService(new Intent(getBaseContext(), AlertService.class));
            activityLog.setText("");
            alertService.logString = "";
            view.setTag(0);
        }
        else
        {
            serviceButton.setText(R.string.button_text_stop);
            startService(new Intent(getBaseContext(), AlertService.class));
            view.setTag(1);
        }
    }
}
