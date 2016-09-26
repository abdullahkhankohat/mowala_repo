package flp.oursols.com.flp.models;

/**
 * Created by Anwar on 9/26/2016.
 */

public class UserFeaturesUsingSms {

    boolean GetLocation,SimMonitoring;
    LockMobileRemotely lockMobileRemotely;
    RingMobile ringMobile;
    WipeMemoryCard wipeMemoryCard;
    WipePhoneMemory wipePhoneMemory;
    GetLocation getLocation;
    SimMonitoring simMonitoring;

    public boolean isGetLocation() {
        return GetLocation;
    }

    public void setGetLocation(boolean getLocation) {
        GetLocation = getLocation;
    }

    public boolean isSimMonitoring() {
        return SimMonitoring;
    }

    public void setSimMonitoring(boolean simMonitoring) {
        SimMonitoring = simMonitoring;
    }

    public LockMobileRemotely getLockMobileRemotely() {
        return lockMobileRemotely;
    }

    public void setLockMobileRemotely(LockMobileRemotely lockMobileRemotely) {
        this.lockMobileRemotely = lockMobileRemotely;
    }

    public RingMobile getRingMobile() {
        return ringMobile;
    }

    public void setRingMobile(RingMobile ringMobile) {
        this.ringMobile = ringMobile;
    }

    public WipeMemoryCard getWipeMemoryCard() {
        return wipeMemoryCard;
    }

    public void setWipeMemoryCard(WipeMemoryCard wipeMemoryCard) {
        this.wipeMemoryCard = wipeMemoryCard;
    }

    public WipePhoneMemory getWipePhoneMemory() {
        return wipePhoneMemory;
    }

    public void setWipePhoneMemory(WipePhoneMemory wipePhoneMemory) {
        this.wipePhoneMemory = wipePhoneMemory;
    }

    public flp.oursols.com.flp.models.GetLocation getGetLocation() {
        return getLocation;
    }

    public void setGetLocation(flp.oursols.com.flp.models.GetLocation getLocation) {
        this.getLocation = getLocation;
    }

    public flp.oursols.com.flp.models.SimMonitoring getSimMonitoring() {
        return simMonitoring;
    }

    public void setSimMonitoring(flp.oursols.com.flp.models.SimMonitoring simMonitoring) {
        this.simMonitoring = simMonitoring;
    }
}
