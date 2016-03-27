package sng.com.testhvn.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sng.com.testhvn.R;
import sng.com.testhvn.adapter.BrandAdapter;
import sng.com.testhvn.loader.BrandLoader;
import sng.com.testhvn.model.brand.Brand;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.ui.activity.HomeActivity;

public class SplashScreenFragment extends BaseLoadingFragment {
    // TODO: Rename parameter arguments, choose names that match
    public static final String TAG = "SplashScreenFragment";
    private static final int LOADER_GET_ALL_BRAND = 0;
    // TODO: Rename and change types of parameters
    private ArrayList<Brand> mBrandList;
    private BrandAdapter mSpinnerAdapter;
    @Bind(R.id.spn_brand_select)
    AppCompatSpinner mSpinner;
    @Bind(R.id.btn_select_brand)
    Button mBtnSellectBrand;

    @OnClick(R.id.btn_select_brand)
    void goToHome() {
        HomeFragment fragment = HomeFragment.newInstance(mBrandList, mSpinner.getSelectedItemPosition());
        replaceFragmmentWithStack(fragment, HomeFragment.TAG);
    }

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    public static SplashScreenFragment newInstance() {
        SplashScreenFragment fragment = new SplashScreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        ButterKnife.bind(this,view);
        mSpinnerAdapter = new BrandAdapter(getContext());
        mSpinner.setAdapter(mSpinnerAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).getSupportActionBar().hide();
                    ((HomeActivity) getActivity()).mFabMenu.setVisibility(View.GONE);
        }
        getLoaderManager().restartLoader(LOADER_GET_ALL_BRAND, null, mLoadAllBrandCb);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).getSupportActionBar().show();
            ((HomeActivity) getActivity()).mFabMenu.setVisibility(View.VISIBLE);
        }
    }

    private final LoaderManager.LoaderCallbacks<BrandResult> mLoadAllBrandCb = new LoaderManager.LoaderCallbacks<BrandResult>() {
        @Override
        public Loader<BrandResult> onCreateLoader(int id, Bundle args) {
            showLoading();
            Log.d(TAG, "onCreateLoader: mLoadAllBrandCb");
            return new BrandLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<BrandResult> loader, BrandResult data) {
            if (null == data) {
                setDialogText(getString(R.string.global_error), getString(R.string.global_try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLoaderManager().restartLoader(LOADER_GET_ALL_BRAND, null, mLoadAllBrandCb);
                    }
                });
                return;
            }
            showContent();
            mBrandList = (ArrayList) data.getResults();
            mBrandList.add(0, new Brand("", "all brand", getString(R.string.product_all_product), "123456", ""));
            if (null != mBrandList && mBrandList.size() > 0) {
                mSpinnerAdapter.setData(mBrandList);
            }
        }

        @Override
        public void onLoaderReset(Loader<BrandResult> loader) {

        }
    };
}
