package sng.com.testhvn.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sng.com.testhvn.R;

/**
 * Created by son.nguyen on 3/21/2016.
 */
public class QrScanActivity extends Activity {
    private QRCodeReaderView mQRCodeReaderView;
    private static final int ACTIVITY_RESULT_QR_CODE = 101;
    public static final String QR_CODE_RESULT_FAIL = "QR_CODE_RESULT_FAIL";
    public static final String QR_CODE_RESULT_SUCCESS = "QR_CODE_RESULT_SUCCESS";
    @Bind(R.id.tv_title)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qc_scanner);
        ButterKnife.bind(this);
        mQRCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mQRCodeReaderView.setOnQRCodeReadListener(new QRCodeReaderView.OnQRCodeReadListener() {
            @Override
            public void onQRCodeRead(String text, PointF[] points) {
                Intent intent = new Intent();
                intent.putExtra(QR_CODE_RESULT_SUCCESS, text);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void cameraNotFound() {
                Intent intent = new Intent();
                intent.putExtra(QR_CODE_RESULT_FAIL, "camera not found");
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void QRCodeNotFoundOnCamImage() {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeReaderView.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeReaderView.getCameraManager().stopPreview();
    }
}
