package flp.oursols.com.flp.reciever;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import flp.oursols.com.flp.screens.HomeScreen;

public class BootStart extends BroadcastReceiver
{
    private String simid;

    public void onReceive(Context context, Intent arg1) {

        if(isAdmin(context))
        {
            simid = getSimId(context);

        }
    }

    private String getSimId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
    }

    private boolean isAdmin(Context context)
    {
        DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        ComponentName mComponentName = new ComponentName(context, MyAdminReceiver.class);
        return mDevicePolicyManager.isAdminActive(mComponentName);
    }


    public static void getAdminAccess(HomeScreen context)
    {
        int ADMIN_INTENT = 15;
        String description = "Sample Administrator description";
        ComponentName   mComponentName = new ComponentName(context, MyAdminReceiver.class);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
        context.startActivityForResult(intent, ADMIN_INTENT);
    }
}