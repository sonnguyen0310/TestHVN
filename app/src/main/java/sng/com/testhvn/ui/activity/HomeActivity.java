package sng.com.testhvn.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import sng.com.testhvn.R;
import sng.com.testhvn.ui.fragment.CommentFragment;
import sng.com.testhvn.ui.fragment.HomeFragment;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class HomeActivity extends BaseActivity {
    public View btnAddReview;
    @Bind(R.id.multiple_actions)
    public FloatingActionsMenu btnFabAddReview;
    @Bind(R.id.action_go_to_review)
    public FloatingActionButton mFabGoToReview;
    @Bind(R.id.action_qr_code)
    public FloatingActionButton mFabScanQr;
    @Bind(R.id.action_go_to_review)
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
                CommentFragment fragment = CommentFragment.newInstance(null, null, null);
                getSupportFragmentManager().beginTransaction().addToBackStack(CommentFragment.TAG).replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

}
