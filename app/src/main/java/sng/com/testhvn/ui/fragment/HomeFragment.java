package sng.com.testhvn.ui.fragment;
/**
 * Created by son.nguyen on 3/17/2016.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sng.com.testhvn.R;
import sng.com.testhvn.adapter.BrandAdapter;
import sng.com.testhvn.adapter.ProductListAdapter;
import sng.com.testhvn.loader.AllReviewLoader;
import sng.com.testhvn.loader.BrandLoader;
import sng.com.testhvn.loader.ProductByBrandLoader;
import sng.com.testhvn.loader.ProductLoader;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.brand.Brand;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.ProductResult;
import sng.com.testhvn.ui.activity.HomeActivity;

public class HomeFragment extends BaseLoadingFragment {
    // TODO: Rename parameter arguments, choose names that match
    public static final String TAG = "HomeFragment";
    public static final String ARG_LIST_BRAND = "LIST_BRAND";
    private static final String ARG_BRAND_SELECTED = "BRAND_SELECTED";
    private static final int LOADER_GET_ALL_BRAND = 0;
    private static final int LOADER_GET_ALL_PRODUCT = 2;
    private static final int LOADER_GET_ALL_COMMENT = 3;
    private static final int LOADER_GET_PRODUCT_BY_BRAND = 4;
    private static final String BRAND_ID = "brand_id";
    private static String ARG_PRODUCT_DETAIL = "PRODUCT_DETAIL";

    private ProductListAdapter mProductListAdapter;
    private BrandAdapter mSpinnerAdapter;


    private ProductResult mProductResult;
    private CommentResult mCommentList;
    private ArrayList<Brand> mBrandList;
    private int mSelectedBrand = -1;
    private OnProductListListener mOnProductListener;
    private SearchView searchView;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(List<Brand> brands, int selected) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST_BRAND, (ArrayList) brands);
        args.putInt(ARG_BRAND_SELECTED, selected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mBrandList = getArguments().getParcelableArrayList(ARG_LIST_BRAND);
            mSelectedBrand = getArguments().getInt(ARG_BRAND_SELECTED);
        }
        getLoaderManager().restartLoader(LOADER_GET_ALL_COMMENT, null, mCbLoadAllComment);
        showLoading();
        mCommentList = new CommentResult();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Bind(R.id.spn_brand_select)
    AppCompatSpinner mSpinner;
    @Bind(R.id.rc_product_list)
    RecyclerView mRvListProduct;
    @Bind(R.id.et_search)
    EditText mEtSearch;

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvListProduct.setLayoutManager(linearLayoutManager);
        mProductListAdapter = new ProductListAdapter(getContext());

        mSpinnerAdapter = new BrandAdapter(getContext());
        mSpinner.setAdapter(mSpinnerAdapter);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.home_page));
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProductListAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).mFabMenu.setVisibility(View.VISIBLE);
        }
        clearSearch();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        onUpdateBrand();

        mOnProductListener = new OnProductListListener() {
            @Override
            public void onItemClick(int position) {
                DetailProductFragment fragment = DetailProductFragment.newInstance((ArrayList) mProductResult.getResults(), mProductResult.getResults().get(position), (ArrayList<Comment>) mCommentList.getResults());
                replaceFragmmentWithStack(fragment, DetailProductFragment.TAG);
            }
        };
        mProductListAdapter.setOnItemClickListener(mOnProductListener);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearSearch();
                if (null == mBrandList || mBrandList.size() < position) ;
                if (mBrandList.get(position).getName().equals(getString(R.string.product_all_product))) {
                    getLoaderManager().restartLoader(LOADER_GET_ALL_PRODUCT, null, mCbLoadAllProduct);
                } else {
                    Bundle bd = new Bundle();
                    bd.putString(BRAND_ID, mBrandList.get(position).getName());
                    getLoaderManager().restartLoader(LOADER_GET_PRODUCT_BY_BRAND, bd, mCbLoadProducByBrand);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProductListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mSelectedBrand = mSpinner.getSelectedItemPosition();
    }

    private void onUpdateBrand() {
        if (mBrandList == null || !(mBrandList.size() > 0)) {
            getLoaderManager().restartLoader(LOADER_GET_ALL_BRAND, null, mLoadAllBrandCb);
        } else {
            mSpinnerAdapter.setData(mBrandList);
            if (mSelectedBrand != -1) {
                mSpinner.setSelection(mSelectedBrand);
            }
        }

    }

    private final LoaderManager.LoaderCallbacks<BrandResult> mLoadAllBrandCb = new LoaderManager.LoaderCallbacks<BrandResult>() {
        @Override
        public Loader<BrandResult> onCreateLoader(int id, Bundle args) {
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

    private final LoaderManager.LoaderCallbacks<ProductResult> mCbLoadAllProduct = new LoaderManager.LoaderCallbacks<ProductResult>() {
        @Override
        public Loader<ProductResult> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader: mCbLoadAllProduct");
            showLoading();
            return new ProductLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<ProductResult> loader, ProductResult data) {
            showContent();
            if (null == data || !(data.getResults().size() > 0)) {
                setDialogText(getString(R.string.global_error), getString(R.string.global_try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLoaderManager().restartLoader(LOADER_GET_ALL_PRODUCT, null, mCbLoadAllProduct);
                    }
                });
                return;
            }
            mProductResult = data;
            mProductListAdapter.setData((ArrayList) data.getResults());
            mCommentList.getResults().clear();
            mCommentList.getResults().addAll(data.getComment());
            mRvListProduct.setAdapter(mProductListAdapter);
        }

        @Override
        public void onLoaderReset(Loader<ProductResult> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<CommentResult> mCbLoadAllComment = new LoaderManager.LoaderCallbacks<CommentResult>() {
        @Override
        public Loader<CommentResult> onCreateLoader(int id, Bundle args) {
            return new AllReviewLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<CommentResult> loader, CommentResult data) {
            showContent();
            if (data != null) {
                mCommentList.getResults().clear();
                mCommentList.getResults().addAll(data.getResults());
            }

        }

        @Override
        public void onLoaderReset(Loader<CommentResult> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<ProductResult> mCbLoadProducByBrand = new LoaderManager.LoaderCallbacks<ProductResult>() {
        String brandId;
        Bundle mBundle = new Bundle();

        @Override
        public Loader<ProductResult> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader: mCbLoadProducByBrand");
            showLoading();
            brandId = args.getString(BRAND_ID);
            Log.d(TAG, "onCreateLoader: mCbLoadProducByBrand" + brandId);
            mBundle.putAll(args);
            return new ProductByBrandLoader(getContext(), args.getString(BRAND_ID));
        }

        @Override
        public void onLoadFinished(Loader<ProductResult> loader, ProductResult data) {

            if (data != null) {
                Log.d("sonnguyen", "onLoadFinished: " + data.getResults().size());
                mProductResult = data;
                if (mProductListAdapter == null) {
                    mProductListAdapter = new ProductListAdapter(getContext());
                    mRvListProduct.setAdapter(mProductListAdapter);
                }
                mProductListAdapter.setData((ArrayList) data.getResults());
                mRvListProduct.setAdapter(mProductListAdapter);
            } else {
                setDialogText(getString(R.string.global_error), getString(R.string.global_try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLoaderManager().restartLoader(LOADER_GET_PRODUCT_BY_BRAND, mBundle, mCbLoadProducByBrand);
                    }
                });
            }
            showContent();
        }

        @Override
        public void onLoaderReset(Loader<ProductResult> loader) {

        }
    };

    public interface OnProductListListener {
        void onItemClick(int position);
    }

    private void clearSearch() {
        if (mEtSearch != null) {
            mEtSearch.setText("");
        }
    }

}
