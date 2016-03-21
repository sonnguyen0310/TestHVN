package sng.com.testhvn.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.client.Response;
import sng.com.testhvn.R;
import sng.com.testhvn.interfaces.FabListener;
import sng.com.testhvn.loader.PostCommentLoader;
import sng.com.testhvn.loader.ProductLoader;
import sng.com.testhvn.loader.UserLoader;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.model.product.ProductID;
import sng.com.testhvn.model.user.User;
import sng.com.testhvn.model.user.UserID;
import sng.com.testhvn.service.apiRequestModel.PostReview;
import sng.com.testhvn.service.apiRequestModel.ProductResult;
import sng.com.testhvn.service.apiRequestModel.UserResult;
import sng.com.testhvn.ui.activity.HomeActivity;
import sng.com.testhvn.util.Utils;

public class CommentFragment extends BaseLoadingFragment implements View.OnClickListener {
    public static final String TAG = "CommentFragment";
    private static final String ARG_LIST_PRODUCT = "param1";
    private static final String ARG_LIST_USER = "param2";
    private static final String ARG_POST_REVIEW = "arg_post_review";

    private static final int LOADER_GET_ALL_USER = 0;
    private static final int LOADER_GET_ALL_PRODUCT = 1;
    private static final int LOADER_POST_COMMENT = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AutoCompleteTextView mEdtProductId;
    private EditText mEdtEmail;
    private EditText mEdtRating;
    private EditText mEdtComment;
    private View mBtnQrScan;
    private View mBtnVoice;
    private Button mBtnSubmit;
    private TextView mTvProductName;

    private ArrayList<Product> mListProduct;
    private ArrayList<User> mListUser;

    private Product mProduct;
    private User mUser;
    private ArrayAdapter<String> mProductAutoAdapter;
    private FabListener mFabListener;

    public static CommentFragment newInstance(ArrayList<Product> listProduct, ArrayList<User> listUser) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST_PRODUCT, listProduct);
        args.putParcelableArrayList(ARG_LIST_USER, listUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mListProduct = getArguments().getParcelableArrayList(ARG_LIST_PRODUCT);
            mListUser = getArguments().getParcelableArrayList(ARG_LIST_USER);
        }
        if (null == mListProduct && null == mListUser) {
            getLoaderManager().restartLoader(LOADER_GET_ALL_PRODUCT, null, mCbLoadAllProduct);
        }
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mBtnQrScan = (View) view.findViewById(R.id.btn_qr_scan);

        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);

        mBtnVoice = (View) view.findViewById(R.id.btn_voice);
        mEdtComment = (EditText) view.findViewById(R.id.edt_comment);
        mEdtEmail = (EditText) view.findViewById(R.id.edt_email);
        mEdtProductId = (AutoCompleteTextView) view.findViewById(R.id.edt_product_id);
        mEdtRating = (EditText) view.findViewById(R.id.edt_rating);
        mTvProductName = (TextView) view.findViewById(R.id.tv_product_name);
        setDisableView();
        return view;
    }

//    @Override
//    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return null;
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (mListProduct != null) {
            setAutoCompleteAdapter();
        }
        mEdtProductId.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                checkProduct(s.toString());
                            }
                        },
                        DELAY
                );
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).btnAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private void setEnableView() {
        mEdtComment.setActivated(true);
        mEdtEmail.setActivated(true);
        mEdtRating.setActivated(true);
        mBtnSubmit.setActivated(true);
    }

    private void setDisableView() {
        mEdtComment.setActivated(false);
        mEdtEmail.setActivated(false);
        mEdtRating.setActivated(false);
        mBtnSubmit.setActivated(false);
    }

    private List<String> getListProductID() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mListProduct.size(); i++) {
            list.add(mListProduct.get(i).getObjectId());
        }
        return list;
    }

    private void checkProduct(String productId) {
        if (null == mListProduct) {
            return;
        }
        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getObjectId().toLowerCase().equals(productId.toLowerCase())) {
                final int finalI = i;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvProductName.setText(mListProduct.get(finalI).getProductName());
                        setEnableView();
                    }
                });
                mProduct = mListProduct.get(i);

                return;
            }
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDisableView();
                mTvProductName.setText(getString(R.string.comment_no_product_found));
            }
        });
    }

    private void onSubmit() {
        if (TextUtils.isEmpty(mEdtComment.getText())) {
            showMissingField(mEdtComment);
            return;
        }
        if (TextUtils.isEmpty(mEdtEmail.getText())) {
            showMissingField(mEdtEmail);
            return;
        }
        if (TextUtils.isEmpty(mEdtRating.getText())) {
            showMissingField(mEdtRating);
            return;
        }
        if (!isGoodUser()) {
            Toast.makeText(getContext(), getString(R.string.comment_user_not_found), Toast.LENGTH_SHORT).show();
            mEdtEmail.requestFocus();
            return;
        }
        PostReview postReview = new PostReview();

        ProductID productId = new ProductID();
        productId.setClassName("Product");
        productId.setType("Pointer");
        productId.setObjectId(mProduct.getObjectId());

        UserID userId = new UserID();
        userId.setObjectId(mUser.getObjectId());
        userId.setType("Pointer");
        userId.setClassName("User");
        userId.setObjectId("s1k6Vzf9Uk");

        postReview.setComment(mEdtComment.getText().toString());
        postReview.setRating(Integer.parseInt(mEdtRating.getText().toString()));
        postReview.setProductID(productId);
        postReview.setUserID(userId);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_POST_REVIEW, postReview);
        getLoaderManager().restartLoader(LOADER_POST_COMMENT,bundle,mPostReviewLoaderCallBack);
    }

    private void showMissingField(View v) {
        v.requestFocus();
        Toast.makeText(getContext(), getString(R.string.comment_missing_field), Toast.LENGTH_SHORT).show();
    }

    private boolean isGoodUser() {

        for (int i = 0; i < mListUser.size(); i++) {
            if (mEdtEmail.getText().toString().equals(mListUser.get(i).getEmail())) {
                mUser = mListUser.get(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                onSubmit();
                break;
        }
    }

    private void setAutoCompleteAdapter() {
        List<String> list = getListProductID();
        mProductAutoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        mEdtProductId.setAdapter(mProductAutoAdapter);
        mEdtProductId.setThreshold(1);
        mProductAutoAdapter.notifyDataSetChanged();
    }

    private LoaderManager.LoaderCallbacks<UserResult> mUserResultLoaderCallbacks = new LoaderManager.LoaderCallbacks<UserResult>() {
        @Override
        public Loader<UserResult> onCreateLoader(int id, Bundle args) {
            showLoading();
            return new UserLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<UserResult> loader, UserResult data) {
            mListUser = (ArrayList) data.getResults();

            showContent();
        }

        @Override
        public void onLoaderReset(Loader<UserResult> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<ProductResult> mCbLoadAllProduct = new LoaderManager.LoaderCallbacks<ProductResult>() {
        @Override
        public Loader<ProductResult> onCreateLoader(int id, Bundle args) {
            showLoading();
            return new ProductLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<ProductResult> loader, ProductResult data) {
            mListProduct = (ArrayList) data.getResults();
            setAutoCompleteAdapter();
            getLoaderManager().restartLoader(LOADER_GET_ALL_USER, null, mUserResultLoaderCallbacks);
            showContent();
        }

        @Override
        public void onLoaderReset(Loader<ProductResult> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<Response> mPostReviewLoaderCallBack = new LoaderManager.LoaderCallbacks<Response>() {
        @Override
        public Loader<Response> onCreateLoader(int id, Bundle args) {
            return new PostCommentLoader(getContext(), (PostReview) args.getParcelable(ARG_POST_REVIEW));
        }

        @Override
        public void onLoadFinished(Loader<Response> loader, Response data) {
            if (Utils.toJson(data).toString().contains("createdAt") && Utils.toJson(data).toString().contains("createdAt")){

            }else {
                Toast.makeText(getContext(),getString(R.string.comment_error_post),Toast.LENGTH_LONG);
            }
        }

        @Override
        public void onLoaderReset(Loader<Response> loader) {

        }
    };
}
