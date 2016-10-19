package flp.oursols.com.flp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import flp.oursols.com.flp.R;


public enum Dialogs {
    INSTANCE;

    private Dialog mDialog;
    private int progress = 0;
    private CountDownTimer timer;
    int counter = 0;
    int totalTime = 60;

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void dismissDialog() {
        if (null != mDialog && isShowing())
            mDialog.dismiss();
        if(null != timer) {
            try {
                timer.cancel();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isShowing() {
        return ((null != mDialog) && mDialog.isShowing());
    }

    public void showLoader(Context context) {
        if (null != mDialog && mDialog.isShowing()) return;
        mDialog = new Dialog(context, R.style.actionSheetTheme);
        mDialog.setContentView(R.layout.loading);
        mDialog.setCancelable(false);
        mDialog.show();
    }




    public void showErrorDialog(Context context, String errorMessage) {
       /* dismissDialog();
        mDialog = new Dialog(context, R.style.actionSheetTheme);
        mDialog.setContentView(R.layout.error_dialog);
        TextView tvError = (TextView) mDialog.findViewById(R.id.tvErrorMessage);
        ImageButton ibPositive = (ImageButton) mDialog.findViewById(R.id.ibPositive);

        tvError.setText(errorMessage);
        ibPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.show();*/

    }





}
