package sng.com.testhvn.ui.fragment;
/**
 * Created by son.nguyen on 3/17/2016.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sng.com.testhvn.R;
import sng.com.testhvn.adapter.BrandAdapter;
import sng.com.testhvn.adapter.ProductListAdapter;
import sng.com.testhvn.loader.AllReviewLoader;
import sng.com.testhvn.loader.BrandLoader;
import sng.com.testhvn.loader.ProductLoader;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.brand.Brand;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.ProductResult;

public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int LOADER_GET_ALL_BRAND = 0;
    private static final int LOADER_GET_ALL_PRODUCT = 2;
    private static final int LOADER_GET_ALL_COMMENT = 3;
    private static String ARG_PRODUCT_DETAIL = "PRODUCT_DETAIL";

    private RecyclerView mRvListProduct;
    private AppCompatSpinner mSpinner;
    private ProductListAdapter mProductListAdapter;
    private BrandAdapter mSpinnerAdapter;


    private ProductResult mProductResult;
    private CommentResult mCommentList;
    private String mParam1;
    private String mParam2;
    private OnProductListListener mOnProductListener;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mCommentList = new CommentResult();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRvListProduct = (RecyclerView) view.findViewById(R.id.rc_product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvListProduct.setLayoutManager(linearLayoutManager);

        mSpinner = (AppCompatSpinner) view.findViewById(R.id.spn_brand_select);
        mSpinnerAdapter = new BrandAdapter(getContext());
        mSpinner.setAdapter(mSpinnerAdapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mOnProductListener = new OnProductListListener() {
            @Override
            public void onItemClick(int position) {
                DetailProductFragment fragment = DetailProductFragment.newInstance(mProductResult.getResults().get(position), getProductComment(mProductResult.getResults().get(position)));
                getFragmentManager().beginTransaction().addToBackStack(DetailProductFragment.TAG).replace(R.id.fragment_container, fragment).commit();
            }
        };
        mProductListAdapter = new ProductListAdapter(getContext(), mOnProductListener);
        onUpdateUI();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void onUpdateUI() {
        onUpdateBrand();
    }

    private void onUpdateBrand() {
        getLoaderManager().restartLoader(LOADER_GET_ALL_BRAND, null, mLoadAllBrandCb);
    }

    private final LoaderManager.LoaderCallbacks<BrandResult> mLoadAllBrandCb = new LoaderManager.LoaderCallbacks<BrandResult>() {
        @Override
        public Loader<BrandResult> onCreateLoader(int id, Bundle args) {
            return new BrandLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<BrandResult> loader, BrandResult data) {
            if (null == data) {
                return;
            }

            ArrayList<Brand> list = (ArrayList) data.getResults();
            if (null != list && list.size() > 0) {
                mSpinnerAdapter.setData(list);
            }
            getLoaderManager().restartLoader(LOADER_GET_ALL_PRODUCT, null, mCbLoadAllProduct);
        }

        @Override
        public void onLoaderReset(Loader<BrandResult> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<ProductResult> mCbLoadAllProduct = new LoaderManager.LoaderCallbacks<ProductResult>() {
        @Override
        public Loader<ProductResult> onCreateLoader(int id, Bundle args) {
            return new ProductLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<ProductResult> loader, ProductResult data) {
            if (null == data || !(data.getResults().size() > 0)) {
                return;
            }
            mProductResult = data;
            mProductListAdapter.setData((ArrayList) data.getResults());
            mCommentList.getResults().addAll(data.getComment());
            mRvListProduct.setAdapter(mProductListAdapter);
//            if (getActivity() instanceof HomeActivity) {
//                if (null == ((HomeActivity) getActivity()).getmCommentResult().getResults() || ((HomeActivity) getActivity()).getmCommentResult().getResults().size() == 0) {
//                    getLoaderManager().restartLoader(LOADER_GET_ALL_COMMENT, null, mCbLoadAllComment);
//                } else {
//                    mCommentList.getResults().addAll(((HomeActivity) getActivity()).getmCommentResult().getResults());
//                }
//            } else {
//
//            }
            getLoaderManager().restartLoader(LOADER_GET_ALL_COMMENT, null, mCbLoadAllComment);
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
            mCommentList.getResults().addAll(data.getResults());
        }

        @Override
        public void onLoaderReset(Loader<CommentResult> loader) {

        }
    };

    public interface OnProductListListener {
        void onItemClick(int position);
    }

    private ArrayList<Comment> getProductComment(Product product) {
        ArrayList<Comment> listComment = new ArrayList<>();
        for (Comment comment : mCommentList.getResults()) {
            try {
                if (product.getObjectId().equals(comment.getProductID().getObjectId())) {
                    listComment.add(comment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listComment;
    }
}
