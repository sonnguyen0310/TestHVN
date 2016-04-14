package sng.com.testhvn.ui.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sng.com.testhvn.R;
import sng.com.testhvn.ui.fragment.CommentFragment;
import sng.com.testhvn.ui.fragment.DetailProductFragment;
import sng.com.testhvn.ui.fragment.SplashScreenFragment;
import sng.com.testhvn.util.Utils;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class HomeActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int SUCCESS = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int ACTIVITY_RESULT_VOICE_CODE = 100;
    private static final int ACTIVITY_RESULT_QR_CODE = 101;
    private static final String TAG = "HomeActivity";
    private ActivityCompat.OnRequestPermissionsResultCallback mPermissionRqCallback;
    @Bind(R.id.multiple_actions)
    public FloatingActionsMenu mFabMenu;
    @Bind(R.id.action_go_to_review)
    public FloatingActionButton mFabGoToReview;
    @Bind(R.id.action_qr_code)
    public FloatingActionButton mFabScanQr;
    @Bind(R.id.action_voice)
    public FloatingActionButton mFabVoice;
    @Bind(R.id.main_layout)
    public View mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mTextToSpeech.setLanguage(Locale.ENGLISH);
//        Fragment fragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, HomeFragment.TAG).commit();
        Fragment fragment = new SplashScreenFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, SplashScreenFragment.TAG).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");
                onClickQRScan();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sonnguyen", "----------HomeActivity-------onActivityResult: resultCode: " + resultCode + " / " + RESULT_OK + " / " + requestCode);
        switch (requestCode) {
            case ACTIVITY_RESULT_VOICE_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String productId = result.get(0);

                    CommentFragment cmFragment = CommentFragment.newInstance(null,
                            null,
                            null, Utils.resultTTS(productId));
                    getSupportFragmentManager().beginTransaction().addToBackStack(CommentFragment.TAG).replace(R.id.fragment_container, cmFragment, CommentFragment.TAG).commit();
                }
                break;
            case ACTIVITY_RESULT_QR_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    if (!TextUtils.isEmpty(data.getStringExtra(ScanActivity.QR_CODE_RESULT_FAIL))) {
                        Toast.makeText(getApplicationContext(), data.getStringExtra(ScanActivity.QR_CODE_RESULT_FAIL), Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        String productId = data.getStringExtra(ScanActivity.QR_CODE_RESULT_SUCCESS);
                        CommentFragment cmFragment = CommentFragment.newInstance(null,
                                null,
                                null, productId);
                        getSupportFragmentManager().beginTransaction().addToBackStack(CommentFragment.TAG).replace(R.id.fragment_container, cmFragment, CommentFragment.TAG).commit();
                    }
                }
                break;
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US.toString());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.global_speech_text));
        try {
            startActivityForResult(intent, ACTIVITY_RESULT_VOICE_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.glbal_voice_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.action_go_to_review)
    void onGotoReview() {
        mFabMenu.collapse();
        CommentFragment cmFragment;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        {
            if (currentFragment instanceof DetailProductFragment) {
                cmFragment = CommentFragment.newInstance(((DetailProductFragment) currentFragment).getmListProduct(),
                        ((DetailProductFragment) currentFragment).getmListUser(),
                        ((DetailProductFragment) currentFragment).getmProductResult(), null);
            }
//        if (getFragmentManager().findFragmentByTag(DetailProductFragment.TAG) != null && getFragmentManager().findFragmentByTag(DetailProductFragment.TAG).isVisible()) {
//            DetailProductFragment fragment = (DetailProductFragment) getSupportFragmentManager().findFragmentByTag(DetailProductFragment.TAG);
//            cmFragment = CommentFragment.newInstance(fragment.getmListProduct(), fragment.getmListUser(), fragment.getmProductResult(), null);
//        }
            else {
                cmFragment = CommentFragment.newInstance(null, null, null, null);
            }
            getSupportFragmentManager().beginTransaction().addToBackStack(CommentFragment.TAG).replace(R.id.fragment_container, cmFragment, CommentFragment.TAG).commit();
        }
    }

    @OnClick(R.id.action_qr_code)
    void onClickQRScan() {
        mFabMenu.collapse();
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Camera permission has not been granted.
                requestCameraPermission();
            } else {
                // Camera permissions is already available, show the camera preview.
                Log.i(TAG,
                        "CAMERA permission has already been granted. Displaying camera preview.");
                Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                startActivityForResult(intent, ACTIVITY_RESULT_QR_CODE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.action_voice)
    void onVoiceInput() {
        promptSpeechInput();
    }

    public void textToSpeech() {
    }

    public void requestCameraPermission() {
        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(mainLayout, R.string.permission_camera_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }
}
