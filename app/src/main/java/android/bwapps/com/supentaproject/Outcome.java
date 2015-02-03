package android.bwapps.com.supentaproject;

public class Outcome
{
    private String outcomeString = "";
    private String appNameString = "";

    public void SetOutcomeString(String s)
    {
        outcomeString = s;
    }

    public String GetOutcomeString()
    {
        return outcomeString;
    }

    public void SetAppNameString(String s)
    {
        appNameString = s;
    }

    public String GetAppNameString()
    {
        return appNameString;
    }
}
