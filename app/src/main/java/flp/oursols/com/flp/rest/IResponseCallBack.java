package flp.oursols.com.flp.rest;

/**
 * Created by APC-10 on 2/18/2016.
 */
public interface IResponseCallBack {
    public void onResponse(Object object);
    public void onSuccess();
    public void onError(String error);
    void onSendCodeSuccess();

}
