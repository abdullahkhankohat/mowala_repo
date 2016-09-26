package flp.oursols.com.flp.reciever;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import flp.oursols.com.flp.screens.HomeScreen;
import flp.oursols.com.flp.utils.Utils;

public class BootStart extends BroadcastReceiver
{
    private String simid;

    public void onReceive(Context context, Intent arg1) {

        if(Utils.isAdmin(context))
        {
            simid = Utils.getSimId(context);

        }
    }

}