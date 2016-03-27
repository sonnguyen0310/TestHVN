package sng.com.testhvn.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import sng.com.testhvn.R;

/**
 * Created by nguye on 3/17/2016.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void replaceFragmmentWithStack(Fragment fragment, String tag) {
        Log.d("sonnguyen", "replaceFragmmentWithStack: "+tag);
        getFragmentManager().beginTransaction().addToBackStack(tag).replace(R.id.fragment_container, fragment, tag).commit();
        getFragmentManager().executePendingTransactions();
    }

    private interface onRetry {
    } ;
}
