package android.bwapps.com.supentaproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity
{
    private Button serviceButton;
    private boolean hasStarted = false;

    private ListView activityList;

    private TextView installAmountText;
    private TextView uninstallAmountText;

    private int installAmountInt = 0;
    private int uninstallAmountInt = 0;

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

        monitorApps();
    }

    private void monitorApps()
    {

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
