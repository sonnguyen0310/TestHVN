package sng.com.testhvn.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sng.com.testhvn.R;
import sng.com.testhvn.adapter.ReviewAdapter;
import sng.com.testhvn.loader.UserLoader;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.service.apiRequestModel.UserResult;

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
    private static String ARG_COMMENT = "COMMENT";
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
    private Product mProductResult;
    private ArrayList<Comment> mListComment;

    private ReviewAdapter mReviewAdapter;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    public static DetailProductFragment newInstance(Product product, ArrayList<Comment> listComment) {
        DetailProductFragment fragment = new DetailProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT_DETAIL, product);
        args.putParcelableArrayList(ARG_PRODUCT_COMMENT, listComment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductResult = getArguments().getParcelable(ARG_PRODUCT_DETAIL);
            mListComment = getArguments().getParcelableArrayList(ARG_PRODUCT_COMMENT);
        }
        mReviewAdapter = new ReviewAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
            mReviewAdapter.setData(mListComment, (ArrayList) data.getResults());
            mRecyclerView.setAdapter(mReviewAdapter);
        }

        @Override
        public void onLoaderReset(Loader<UserResult> loader) {

        }
    };
}
