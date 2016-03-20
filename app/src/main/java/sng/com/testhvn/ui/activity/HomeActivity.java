package sng.com.testhvn.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import sng.com.testhvn.R;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.ui.fragment.HomeFragment;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class HomeActivity extends BaseActivity {
    public CommentResult mCommentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCommentResult = new CommentResult();
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().addToBackStack(HomeFragment.TAG).add(R.id.fragment_container, fragment).commit();
    }

    public CommentResult getmCommentResult() {
        return mCommentResult;
    }
}
