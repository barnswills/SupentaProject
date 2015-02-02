package android.bwapps.com.supentaproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity
{
    private Button serviceButton;
    private boolean hasStarted = false;

    private ListView activityList;

    private TextView installAmountText;
    private TextView uninstallAmountText;

    private int installAmountInt = 0;
    private int uninstallAmountInt = 0;

    // intent for when application has been installed
    private IntentFilter monitorAppFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceButton = (Button) findViewById(R.id.service_button);
        serviceButton.setTag(1);

        activityList = (ListView) findViewById(R.id.activity_list);

        installAmountText = (TextView) findViewById(R.id.apps_installed_amount);
        uninstallAmountText = (TextView) findViewById(R.id.apps_uninstalled_amount);

        installAmountText.setText(String.valueOf(installAmountInt));
        uninstallAmountText.setText(String.valueOf(uninstallAmountInt));

        final BroadcastReceiver receiver = new BroadcastReceiver()
        {
            Outcome outcome = new Outcome();

            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED))
                {
                    outcome.SetOutcome("installed");
                }

                if(intent.getAction().equals(Intent.ACTION_PACKAGE_CHANGED))
                {
                    outcome.SetOutcome("updated");
                }

                if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED))
                {
                    outcome.SetOutcome("uninstalled");
                }

                Toast.makeText(getBaseContext(),
                        "An Application has been " + outcome.GetOutcome(),
                        Toast.LENGTH_LONG).show();
            }
        };

        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        monitorAppFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        monitorAppFilter.addDataScheme("package");
        registerReceiver(receiver, monitorAppFilter);
    }

    public void onServiceClick(View view)
    {
        final int status = (Integer) view.getTag();

        if (status == 1)
        {
            // Service will start when button is clicked
            hasStarted = true;
            // Buttons text is changed giving the user the option to stop
            serviceButton.setText(R.string.button_text_stop);
            view.setTag(0);
        }
        else
        {
            // Service will stop when button is clicked
            hasStarted = false;
            // Buttons text is changed giving the user the option to start
            serviceButton.setText(R.string.button_text_start);
            view.setTag(1);
        }
    }
}
