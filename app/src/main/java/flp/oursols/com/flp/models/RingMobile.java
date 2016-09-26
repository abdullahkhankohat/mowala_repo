package flp.oursols.com.flp.models;

/**
 * Created by Anwar on 9/26/2016.
 */

public class RingMobile extends CommonFeatuers {

    boolean Vibrate,CameraFlash,CustomRingtone,StopRemotely,SendResponse;
    String CustomRingtoneName;

    public boolean isVibrate() {
        return Vibrate;
    }

    public void setVibrate(boolean vibrate) {
        Vibrate = vibrate;
    }

    public boolean isCameraFlash() {
        return CameraFlash;
    }

    public void setCameraFlash(boolean cameraFlash) {
        CameraFlash = cameraFlash;
    }

    public boolean isCustomRingtone() {
        return CustomRingtone;
    }

    public void setCustomRingtone(boolean customRingtone) {
        CustomRingtone = customRingtone;
    }

    public boolean isStopRemotely() {
        return StopRemotely;
    }

    public void setStopRemotely(boolean stopRemotely) {
        StopRemotely = stopRemotely;
    }

    public boolean isSendResponse() {
        return SendResponse;
    }

    public void setSendResponse(boolean sendResponse) {
        SendResponse = sendResponse;
    }

    public String getCustomRingtoneName() {
        return CustomRingtoneName;
    }

    public void setCustomRingtoneName(String customRingtoneName) {
        CustomRingtoneName = customRingtoneName;
    }
}
