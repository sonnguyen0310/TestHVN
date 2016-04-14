package sng.com.testhvn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import sng.com.testhvn.R;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.ui.fragment.HomeFragment;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {
    private HomeFragment.OnProductListListener listListener;
    public ArrayList<Product> mListFiltered;
    private ArrayList<Product> mListOriginal;
    ItemFilter mFilter = new ItemFilter();

    public ProductListAdapter(Context context) {
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_detail_item, parent, false);
        return new ProductViewHolder(view, listListener);
    }

    public void setData(ArrayList<Product> list) {
        if (null == mListOriginal) {
            mListOriginal = new ArrayList<>();
        }
        if (null == mListFiltered) {
            mListFiltered = new ArrayList<>();
        }
        mListOriginal.clear();
        mListFiltered.clear();
        if (list != null) {
            mListOriginal.addAll(list);
            mListFiltered.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(HomeFragment.OnProductListListener onItemClickListener) {
        listListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        if (holder != null && position < mListFiltered.size()) {
            holder.setData(mListFiltered.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return null == mListFiltered ? 0 : mListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Product> list = mListOriginal;

            int count = list.size();
            final ArrayList<Product> nlist = new ArrayList<>();

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getProductName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (mListFiltered == null) return;
            mListFiltered.clear();
            mListFiltered.addAll((ArrayList<Product>) results.values);
            notifyDataSetChanged();
        }

    }
}


class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTitle;
    private TextView mPrice;
    private TextView mDescription;
    private int mPosition;
    private HomeFragment.OnProductListListener onItemClickListener;

    public ProductViewHolder(View v, HomeFragment.OnProductListListener onItemClickListener) {
        super(v);
        mTitle = (TextView) v.findViewById(R.id.tv_title);
        mPrice = (TextView) v.findViewById(R.id.tv_price);
        mDescription = (TextView) v.findViewById(R.id.tv_description);
        this.onItemClickListener = onItemClickListener;
        v.setOnClickListener(this);
    }

    public void setData(Product product, int position) {
        if (product == null) {
            return;
        }
        mPosition = position;
        mTitle.setText(product.getProductName() + "");
        mPrice.setText(product.getPrice() + "");
        mDescription.setText(product.getDescription() + "");
    }

    @Override
    public void onClick(View v) {
        if (null != onItemClickListener) {
            onItemClickListener.onItemClick(mPosition);
        }
    }

}

