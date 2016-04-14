package sng.com.testhvn.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by son.nguyen on 4/14/2016.
 */
public class ScanActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static String TAG = "ScanActivity";
    private static final int ACTIVITY_RESULT_QR_CODE = 101;
    public static final String QR_CODE_RESULT_FAIL = "QR_CODE_RESULT_FAIL";
    public static final String QR_CODE_RESULT_SUCCESS = "QR_CODE_RESULT_SUCCESS";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v(TAG, rawResult.getText());
        Log.v(TAG, rawResult.getBarcodeFormat().toString());
        Intent intent = new Intent();
        intent.putExtra(QR_CODE_RESULT_SUCCESS, rawResult.getText());
        setResult(RESULT_OK, intent);
        finish();
//        mScannerView.resumeCameraPreview(this);
    }
}