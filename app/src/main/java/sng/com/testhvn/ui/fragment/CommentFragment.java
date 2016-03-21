package sng.com.testhvn.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sng.com.testhvn.R;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.model.product.ProductID;
import sng.com.testhvn.model.user.UserID;
import sng.com.testhvn.service.apiRequestModel.PostReview;

public class CommentFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mEdtProductId;
    private EditText mEdtEmail;
    private EditText mEdtRating;
    private EditText mEdtComment;
    private View mBtnQrScan;
    private View mBtnVoice;
    private Button mBtnSubmit;
    private TextView mTvProductName;
    private ArrayList<Product> mListProduct;
    private Product mProduct;
    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mBtnQrScan = (View) view.findViewById(R.id.btn_qr_scan);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mBtnVoice = (View) view.findViewById(R.id.btn_voice);
        mEdtComment = (EditText) view.findViewById(R.id.edt_comment);
        mEdtEmail = (EditText) view.findViewById(R.id.edt_email);
        mEdtProductId = (EditText) view.findViewById(R.id.edt_product_id);
        mEdtRating = (EditText) view.findViewById(R.id.edt_rating);
        mTvProductName = (TextView) view.findViewById(R.id.tv_product_name);
        setDisableView();
        return view;
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

    private void checkProduct(String productId) {
        for (int i = 0; i < mListProduct.size(); i++) {
            if (TextUtils.equals(productId, mListProduct.get(i).getObjectId())) {
                mTvProductName.setText(mListProduct.get(i).getProductName());
                setEnableView();
                return;
            }
        }
        setDisableView();
        mTvProductName.setText(getString(R.string.comment_no_product_found));
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
        ProductID productId = new ProductID();
        UserID userId =new UserID();
        PostReview postReview = new PostReview();
        productId.setClassName("Product");

    }

    private void showMissingField(View v) {
        v.requestFocus();
        Toast.makeText(getContext(), getString(R.string.comment_missing_field), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                break;
        }
    }
}
