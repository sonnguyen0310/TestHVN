package sng.com.testhvn.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.client.Response;
import sng.com.testhvn.R;
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
import sng.com.testhvn.ui.activity.QrScanActivity;
import sng.com.testhvn.util.Utils;

public class CommentFragment extends BaseLoadingFragment implements View.OnClickListener {
    public static final String TAG = "CommentFragment";
    private static final String ARG_LIST_PRODUCT = "param1";
    private static final String ARG_LIST_USER = "param2";
    private static final String ARG_POST_REVIEW = "arg_post_review";
    private static final String ARG_PRODUCT = "arg_product";
    private static final String ARG_PRODUCT_ID = "arg_product_id";
    private static final String PREF_USER_EMAIL = "user_mail";
    private static final int LOADER_GET_ALL_USER = 0;
    private static final int LOADER_GET_ALL_PRODUCT = 1;
    private static final int LOADER_POST_COMMENT = 2;
    private static final int SUCCESS = 1;
    private static final int ACTIVITY_RESULT_VOICE_CODE = 100;
    private static final int ACTIVITY_RESULT_QR_CODE = 101;

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
    private AppCompatRatingBar mRatingBar;

    private ArrayList<Product> mListProduct;
    private ArrayList<User> mListUser;

    private Product mProduct;
    private User mUser;
    private ArrayAdapter<String> mProductAutoAdapter;
    private String mProductID;
    private TextWatcher mTextWatcher;

    public static CommentFragment newInstance(ArrayList<Product> listProduct, ArrayList<User> listUser, Product product, String productID) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST_PRODUCT, listProduct);
        args.putParcelableArrayList(ARG_LIST_USER, listUser);
        args.putParcelable(ARG_PRODUCT, product);
        args.putString(ARG_PRODUCT_ID, productID);
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
            mProduct = getArguments().getParcelable(ARG_PRODUCT);
            mProductID = getArguments().getString(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mBtnQrScan = (View) view.findViewById(R.id.btn_qr_scan);
        mBtnQrScan.setOnClickListener(this);

        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);

        mBtnVoice = (View) view.findViewById(R.id.btn_voice);
        mBtnVoice.setOnClickListener(this);

        mEdtComment = (EditText) view.findViewById(R.id.edt_comment);
        mEdtEmail = (EditText) view.findViewById(R.id.edt_email);

        mRatingBar = (AppCompatRatingBar) view.findViewById(R.id.rb_Rating);

        mEdtProductId = (AutoCompleteTextView) view.findViewById(R.id.edt_product_id);
        mEdtRating = (EditText) view.findViewById(R.id.edt_rating);
        mTvProductName = (TextView) view.findViewById(R.id.tv_product_name);
        mTvProductName.setOnClickListener(this);
        setDisableView();
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.comment_page));
        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        if (mListProduct != null) {
            setAutoCompleteAdapter();
        }
        if (mProductID != null) {
            mEdtProductId.setText(mProductID);
        }
        if (mProduct != null) {
            mEdtProductId.setText(mProduct.getObjectId());
            mTvProductName.setText(mProduct.getProductName());
            setEnableView();
        }
//        mEdtProductId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    mEdtProductId.addTextChangedListener(mTextWatcher);
//                }
//            }
//        });

        mTextWatcher = new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (mEdtProductId.isFocused()) {
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
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        };
        mEdtProductId.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).mFabMenu.setVisibility(View.GONE);
        }
        showContent();
        try {
            mEdtEmail.setText(Utils.readPreference(getContext(), PREF_USER_EMAIL));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == mListProduct && null == mListUser) {
            getLoaderManager().restartLoader(LOADER_GET_ALL_PRODUCT, null, mCbLoadAllProduct);
        } else {
            setAutoCompleteAdapter();
            if (mListUser == null) {
                getLoaderManager().restartLoader(LOADER_GET_ALL_USER, null, mUserResultLoaderCallbacks);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).mFabMenu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void setEnableView() {
        mEdtComment.setEnabled(true);
        mEdtEmail.setEnabled(true);
        mEdtRating.setEnabled(true);
        mBtnSubmit.setEnabled(true);
    }

    private void setDisableView() {
        mEdtComment.setEnabled(false);
        mEdtEmail.setEnabled(false);
        mEdtRating.setEnabled(false);
        mBtnSubmit.setEnabled(false);
    }

    private List<String> getListProductID() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mListProduct.size(); i++) {
            list.add(mListProduct.get(i).getObjectId());
        }
        return list;
    }

    private void checkProduct(final String productId) {
        if (null == mListProduct) {
            return;
        }
        final String newProductID = Utils.resultTTS(productId);
        for (int i = 0; i < mListProduct.size(); i++) {
            if (mListProduct.get(i).getObjectId().toLowerCase().equals(newProductID.toLowerCase())) {
                final int finalI = i;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvProductName.setText(mListProduct.get(finalI).getProductName());
                        mEdtProductId.setText(newProductID);
                        setEnableView();
                        mEdtEmail.requestFocus();
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
        Log.d("sng", "onSubmit: rating: " + (int) (Math.round(mRatingBar.getRating() * 2)));
        if (TextUtils.isEmpty(mEdtComment.getText())) {
            showMissingField(mEdtComment);
            return;
        }
        if (TextUtils.isEmpty(mEdtEmail.getText())) {
            showMissingField(mEdtEmail);
            return;
        }
//        if (TextUtils.isEmpty(mEdtRating.getText())) {
//            showMissingField(mEdtRating);
//            return;
//        }

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

        postReview.setComment(mEdtComment.getText().toString());
        postReview.setRating((int) (Math.round(mRatingBar.getRating() * 2)));
        postReview.setProductID(productId);
        postReview.setUserID(userId);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_POST_REVIEW, postReview);
        Utils.savePreference(getContext(), PREF_USER_EMAIL, mEdtEmail.getText().toString());
        getLoaderManager().restartLoader(LOADER_POST_COMMENT, bundle, mPostReviewLoaderCallBack);
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
            case R.id.btn_voice:
                promptSpeechInput();
                break;
            case R.id.btn_qr_scan:
                try {
                    PackageManager pm = getContext().getPackageManager();

                    if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        Intent intent = new Intent(getActivity(), QrScanActivity.class);
                        startActivityForResult(intent, ACTIVITY_RESULT_QR_CODE);
                    } else {
                        Toast.makeText(getContext(), "sorry, the device do not have the camera", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_product_name:
                DetailProductFragment fragment = DetailProductFragment.newInstance(mListProduct, mProduct, null);
                replaceFragmmentWithStack(fragment, DetailProductFragment.TAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sonnguyen", "onActivityResult: resultCode: " + resultCode + " / " + getActivity().RESULT_OK + " / " + requestCode);
        switch (requestCode) {
            case ACTIVITY_RESULT_VOICE_CODE:
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mEdtProductId.setText(result.get(0));
                }
                break;
            case ACTIVITY_RESULT_QR_CODE:
                if (resultCode == getActivity().RESULT_OK && null != data) {
                    if (!TextUtils.isEmpty(data.getStringExtra(QrScanActivity.QR_CODE_RESULT_FAIL))) {
                        Toast.makeText(getContext(), getString(R.string.qrcode_camera_not_found), Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        mEdtProductId.setText(data.getStringExtra(QrScanActivity.QR_CODE_RESULT_SUCCESS));
                    }
                }
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

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US.toString());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.global_speech_text));
        try {
            startActivityForResult(intent, ACTIVITY_RESULT_VOICE_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.glbal_voice_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
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
        PostReview mPostReview;

        @Override
        public Loader<Response> onCreateLoader(int id, Bundle args) {
            showLoading();
            mPostReview = (PostReview) args.getParcelable(ARG_POST_REVIEW);
            return new PostCommentLoader(getContext(), mPostReview);
        }

        @Override
        public void onLoadFinished(Loader<Response> loader, Response data) {
            if (Utils.toJson(data).toString().contains("createdAt") && Utils.toJson(data).toString().contains("createdAt")) {
                Utils.saveCommentToPrefrence(getContext(), mPostReview);
                mHandler.sendEmptyMessageDelayed(SUCCESS, 100);
            } else {
                Toast.makeText(getContext(), getString(R.string.comment_error_post), Toast.LENGTH_LONG);
            }
            showContent();
        }

        @Override
        public void onLoaderReset(Loader<Response> loader) {

        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                setDialogText(getString(R.string.comment_success), "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();
                        dialog.dismiss();
                    }
                });

            }
        }
    };

}
