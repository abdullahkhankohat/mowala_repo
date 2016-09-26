package flp.oursols.com.flp.models;

/**
 * Created by Anwar on 9/26/2016.
 */

public class SimMonitoring extends CommonFeatuers{
    boolean lockPhone,SendMessage,RingPhone;
    String Contact1,Contact2;

    public boolean isLockPhone() {
        return lockPhone;
    }

    public void setLockPhone(boolean lockPhone) {
        this.lockPhone = lockPhone;
    }

    public boolean isSendMessage() {
        return SendMessage;
    }

    public void setSendMessage(boolean sendMessage) {
        SendMessage = sendMessage;
    }

    public boolean isRingPhone() {
        return RingPhone;
    }

    public void setRingPhone(boolean ringPhone) {
        RingPhone = ringPhone;
    }

    public String getContact1() {
        return Contact1;
    }

    public void setContact1(String contact1) {
        Contact1 = contact1;
    }

    public String getContact2() {
        return Contact2;
    }

    public void setContact2(String contact2) {
        Contact2 = contact2;
    }
}
