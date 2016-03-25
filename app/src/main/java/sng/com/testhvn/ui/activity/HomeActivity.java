package sng.com.testhvn.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sng.com.testhvn.R;
import sng.com.testhvn.ui.fragment.CommentFragment;
import sng.com.testhvn.ui.fragment.HomeFragment;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class HomeActivity extends BaseActivity {
    private static final int SUCCESS = 1;
    private static final int ACTIVITY_RESULT_VOICE_CODE = 100;
    private static final int ACTIVITY_RESULT_QR_CODE = 101;
    public View btnAddReview;


    @Bind(R.id.multiple_actions)
    public FloatingActionsMenu btnFabAddReview;
    @Bind(R.id.action_go_to_review)
    public FloatingActionButton mFabGoToReview;
    @Bind(R.id.action_qr_code)
    public FloatingActionButton mFabScanQr;
    @Bind(R.id.action_voice)
    public FloatingActionButton mFabVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnAddReview = (View) findViewById(R.id.fabAddReview);
        Fragment fragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        onDefaultFabClick();
    }

    public void onDefaultFabClick() {
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentFragment fragment = CommentFragment.newInstance(null, null, null, null);
                getSupportFragmentManager().beginTransaction().addToBackStack(CommentFragment.TAG).replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sonnguyen", "onActivityResult: resultCode: " + resultCode + " / " + RESULT_OK + " / " + requestCode);
        switch (requestCode) {
            case ACTIVITY_RESULT_VOICE_CODE:
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String productId = result.get(0);
                }
                break;
            case ACTIVITY_RESULT_QR_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    if (!TextUtils.isEmpty(data.getStringExtra(QrScanActivity.QR_CODE_RESULT_FAIL))) {
                        Toast.makeText(getApplicationContext(), getString(R.string.qrcode_camera_not_found), Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        String productId = data.getStringExtra(QrScanActivity.QR_CODE_RESULT_SUCCESS);
                    }
                }
                break;
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
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
    }


    @OnClick(R.id.action_qr_code)
    void onClickQRScan() {
        try {
            Intent intent = new Intent(HomeActivity.this, QrScanActivity.class);
            startActivityForResult(intent, ACTIVITY_RESULT_QR_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.action_voice)
    void onVoiceInput() {
        promptSpeechInput();
    }

}
