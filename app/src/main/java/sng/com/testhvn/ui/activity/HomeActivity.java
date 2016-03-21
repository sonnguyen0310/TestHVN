package sng.com.testhvn.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import sng.com.testhvn.R;
import sng.com.testhvn.ui.fragment.CommentFragment;
import sng.com.testhvn.ui.fragment.HomeFragment;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class HomeActivity extends BaseActivity {
    public View btnAddReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
