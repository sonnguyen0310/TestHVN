package sng.com.testhvn.ui.fragment;
/**
 * Created by nguye on 3/17/2016.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import sng.com.testhvn.loader.BrandLoader;
import sng.com.testhvn.loader.ProductLoader;
import sng.com.testhvn.model.Brand;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.apiRequestModel.ProductResult;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int LOADER_GET_ALL_BRAND = 0;
    private static final int LOADER_GET_ALL_PRODUCT = 2;
    private RecyclerView mRvListProduct;
    private AppCompatSpinner mSpinner;
    private ProductListAdapter mProductListAdapter;
    private BrandAdapter mSpinnerAdapter;

    private String mParam1;
    private String mParam2;
    private OnProductListListener mOnProductListener;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRvListProduct = (RecyclerView) view.findViewById(R.id.rc_product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvListProduct.setLayoutManager(linearLayoutManager);

        mSpinner = (AppCompatSpinner) view.findViewById(R.id.spn_brand_select);
        mSpinnerAdapter = new BrandAdapter(getContext());
        mSpinner.setAdapter(mSpinnerAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mProductListAdapter = new ProductListAdapter(getContext(), mOnProductListener);
        mOnProductListener = new OnProductListListener() {
            @Override
            public void onItemClick() {

            }
        };

        onUpdateUI();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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

    private LoaderManager.LoaderCallbacks<BrandResult> mLoadAllBrandCb = new LoaderManager.LoaderCallbacks<BrandResult>() {
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

    private LoaderManager.LoaderCallbacks<ProductResult> mCbLoadAllProduct = new LoaderManager.LoaderCallbacks<ProductResult>() {
        @Override
        public Loader<ProductResult> onCreateLoader(int id, Bundle args) {
            return new ProductLoader(getContext());
        }

        @Override
        public void onLoadFinished(Loader<ProductResult> loader, ProductResult data) {
            if (null == data || !(data.getResults().size() > 0)) {
                return;
            }
            mProductListAdapter.setData((ArrayList) data.getResults());
            mRvListProduct.setAdapter(mProductListAdapter);

        }

        @Override
        public void onLoaderReset(Loader<ProductResult> loader) {

        }
    };

    public interface OnProductListListener {
        void onItemClick();
    }
}
