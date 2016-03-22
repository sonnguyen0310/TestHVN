package sng.com.testhvn.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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

    private TextView mTvName;
    private TextView mTvDescripton;
    private TextView mTvPrice;
    private TextView mTvStock;
    private TextView mTvColor;
    private RecyclerView mRecyclerView;
    private ReviewAdapter mReviewAdapter;

    private Product mProductResult;
    private ArrayList<Comment> mListComment;
    private ArrayList<Product> mListProduct;
    private ArrayList<User> mListUser;
    private AlertDialog.Builder mBuilder;

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    public static DetailProductFragment newInstance(ArrayList<Product> listProduct, Product product, ArrayList<Comment> listComment) {
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
        if (getArguments() != null) {
            mProductResult = getArguments().getParcelable(ARG_PRODUCT_DETAIL);
            mListComment = getArguments().getParcelableArrayList(ARG_PRODUCT_COMMENT);
            if (mListComment == null) {
                mListComment = new ArrayList<>();
                getLoaderManager().restartLoader(LOADER_GET_ALL_COMMENT, null, mCbLoadAllComment);
            }
            mListProduct = getArguments().getParcelableArrayList(ARG_PRODUCT_LIST);
        }
        mReviewAdapter = new ReviewAdapter(getContext());
        showContent();
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        mTvDescripton = (TextView) view.findViewById(R.id.tv_description);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvStock = (TextView) view.findViewById(R.id.tv_stock);
        mTvColor = (TextView) view.findViewById(R.id.tv_color);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
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
        mTvName.setText("" + mProductResult.getProductName());
        mTvPrice.setText("" + mProductResult.getPrice() + "$");
        mTvDescripton.setText("" + mProductResult.getDescription());
        mTvStock.setText(getString(R.string.product_status) + mProductResult.getAvailabilityStatus());
        mTvColor.setText(getString(R.string.product_color) + mProductResult.getColour());

        if (!getUserVisibleHint()) {
            return;
        }
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).btnAddReview.setVisibility(View.VISIBLE);
            ((HomeActivity) getActivity()).btnAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        CommentFragment fragment = CommentFragment.newInstance(mListProduct, mListUser, mProductResult);
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(CommentFragment.TAG).replace(R.id.fragment_container, fragment).commit();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(LOADER_GET_ALL_USER, null, mUserResultLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<UserResult> mUserResultLoaderCallbacks = new LoaderManager.LoaderCallbacks<UserResult>() {
        @Override
        public Loader<UserResult> onCreateLoader(int id, Bundle args) {
            return new UserLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<UserResult> loader, UserResult data) {
            setmListUser((ArrayList) data.getResults());
            mReviewAdapter.setData(mListComment, (ArrayList) data.getResults());
            mRecyclerView.setAdapter(mReviewAdapter);
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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (getActivity() instanceof HomeActivity) {
//            ((HomeActivity) getActivity()).onDefaultFabClick();
//        }
//    }

    private final LoaderManager.LoaderCallbacks<CommentResult> mCbLoadAllComment = new LoaderManager.LoaderCallbacks<CommentResult>() {
        @Override
        public Loader<CommentResult> onCreateLoader(int id, Bundle args) {
            return new AllReviewLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<CommentResult> loader, CommentResult data) {
            mListComment.clear();
            mListComment.addAll(data.getResults());
            mReviewAdapter.setData(mListComment, mListUser);
            mRecyclerView.setAdapter(mReviewAdapter);
            showContent();
        }

        @Override
        public void onLoaderReset(Loader<CommentResult> loader) {

        }
    };
    private void setDialogText(String mess, String button, DialogInterface.OnClickListener listener) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setMessage(mess)
                    .setCancelable(false)
                    .setPositiveButton(button, listener);
        }
        mBuilder.show();
    }
}
