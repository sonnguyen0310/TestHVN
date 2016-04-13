package sng.com.testhvn.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import sng.com.testhvn.R;
import sng.com.testhvn.adapter.ReviewAdapter;
import sng.com.testhvn.loader.AllReviewLoader;
import sng.com.testhvn.loader.UserLoader;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.model.user.User;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.UserResult;
import sng.com.testhvn.ui.activity.HomeActivity;
import sng.com.testhvn.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProductFragment extends BaseLoadingFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String ARG_PRODUCT_DETAIL = "PRODUCT_DETAIL";
    private static String ARG_PRODUCT_COMMENT = "PRODUCT_COMMENT";
    private static String ARG_PRODUCT_LIST = "PRODUCT_LIST";
    private static String ARG_COMMENT = "COMMENT";
    private static final int LOADER_GET_ALL_COMMENT = 3;
    public static final String TAG = "DetailProductFragment";
    private static final int LOADER_GET_ALL_USER = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ReviewAdapter mReviewAdapter;

    private Product mProductResult;
    private ArrayList<Comment> mListComment;
    private ArrayList<Product> mListProduct;
    private ArrayList<User> mListUser;

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    public DetailProductFragment() {
    }

    public static DetailProductFragment newInstance(ArrayList<Product> listProduct, Product product, ArrayList<Comment> listComment) {
        Log.d(TAG, "newInstance: listproduct: " + listProduct + " / prodyct: " + product + " / listcm: " + listComment);
        DetailProductFragment fragment = new DetailProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT_DETAIL, product);
        args.putParcelableArrayList(ARG_PRODUCT_COMMENT, listComment);
        args.putParcelableArrayList(ARG_PRODUCT_LIST, listProduct);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Bind(R.id.tv_description)
    TextView mTvDescripton;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_average_rating)
    TextView mTvAverage;
    @Bind(R.id.tv_stock)
    TextView mTvStock;
    @Bind(R.id.tv_color)
    TextView mTvColor;
    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.ln_average)
    LinearLayout mLnAvaerage;
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.product_detail));
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mTvAverage.setText("");
        mLnAvaerage.setVisibility(View.GONE);
        if (getArguments() != null) {
            if (mListComment == null) {
                mListComment = new ArrayList<>();
            } else {
                mListComment.clear();
            }
            mProductResult = getArguments().getParcelable(ARG_PRODUCT_DETAIL);
            if (mProductResult != null) {
                mReviewAdapter = new ReviewAdapter(getContext(), mProductResult.getObjectId());
            } else {
                mReviewAdapter = new ReviewAdapter(getContext(), null);
            }
            ArrayList<Comment> listAllComments = getArguments().getParcelableArrayList(ARG_PRODUCT_COMMENT);
            if (listAllComments == null || listAllComments.size() == 0) {
                getLoaderManager().restartLoader(LOADER_GET_ALL_COMMENT, null, mCbLoadAllComment);
            } else {
                mListComment.addAll(Utils.getProductComment(mProductResult, listAllComments));
            }
            mListProduct = getArguments().getParcelableArrayList(ARG_PRODUCT_LIST);

            getLoaderManager().restartLoader(LOADER_GET_ALL_USER, null, mUserResultLoaderCallbacks);
        }
        showContent();
        mTvName.setText("" + mProductResult.getProductName());
        mTvDescripton.setText("" + mProductResult.getDescription());
        mTvStock.setText(getString(R.string.product_status) + mProductResult.getAvailabilityStatus());
        mTvColor.setText(getString(R.string.product_color) + mProductResult.getColour());

        if (!getUserVisibleHint()) {
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private LoaderManager.LoaderCallbacks<UserResult> mUserResultLoaderCallbacks = new LoaderManager.LoaderCallbacks<UserResult>() {
        @Override
        public Loader<UserResult> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader: mUserResultLoaderCallbacks");
            return new UserLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<UserResult> loader, UserResult data) {
            if (data != null) {
                setmListUser((ArrayList) data.getResults());
                setDataReviewAdapter((ArrayList<User>) data.getResults());
                mRecyclerView.setAdapter(mReviewAdapter);
            } else {
                setDialogText(getString(R.string.global_error), getString(R.string.global_try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLoaderManager().restartLoader(LOADER_GET_ALL_USER, null, mUserResultLoaderCallbacks);
                    }
                });
            }

        }

        @Override
        public void onLoaderReset(Loader<UserResult> loader) {

        }
    };

    public void setmListUser(ArrayList<User> list) {
        if (mListUser == null) {
            mListUser = new ArrayList<>();
        }
        mListUser.clear();
        mListUser.addAll(list);
    }


    private final LoaderManager.LoaderCallbacks<CommentResult> mCbLoadAllComment = new LoaderManager.LoaderCallbacks<CommentResult>() {
        @Override
        public Loader<CommentResult> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "mCbLoadAllComment: ");
            return new AllReviewLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<CommentResult> loader, CommentResult data) {
            if (mListComment == null) {
                mListComment = new ArrayList<>();
            }
            mListComment.clear();
            mListComment.addAll(Utils.getProductComment(mProductResult, (ArrayList<Comment>) data.getResults()));
            setDataReviewAdapter(mListUser);
            mRecyclerView.setAdapter(mReviewAdapter);
            showContent();
        }

        @Override
        public void onLoaderReset(Loader<CommentResult> loader) {

        }
    };


    public ArrayList<User> getmListUser() {
        return mListUser;
    }

    public ArrayList<Comment> getmListComment() {
        return mListComment;
    }

    public void setmListComment(ArrayList<Comment> mListComment) {
        this.mListComment = mListComment;
    }

    public ArrayList<Product> getmListProduct() {
        return mListProduct;
    }

    public void setmListProduct(ArrayList<Product> mListProduct) {
        this.mListProduct = mListProduct;
    }

    public Product getmProductResult() {
        return mProductResult;
    }

    public void setmProductResult(Product mProductResult) {
        this.mProductResult = mProductResult;
    }

    private void setDataReviewAdapter(ArrayList<User> listUser) {
        if (Utils.getReview(getContext(), mProductResult.getObjectId()) != null) {
            mListComment.addAll(Utils.getReview(getContext(), mProductResult.getObjectId()));
        }
        Log.d("sonnguyen", "onLoadFinished: <<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>" + mListComment.size());
        setTvAverage();
        mReviewAdapter.setData(mListComment, listUser);
    }

    private void setTvAverage() {
        if (mListComment.size() == 0) return;
        int total = 0;
        for (int i = 0; i < mListComment.size(); i++) {
            try {
                total = total + mListComment.get(i).getRating();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        double result = (double) total / (mListComment.size() * 2.0);

        DecimalFormat df = new DecimalFormat("#.#");
        mTvAverage.setText(df.format(result) + "");
        mLnAvaerage.setVisibility(View.VISIBLE);
    }
}
