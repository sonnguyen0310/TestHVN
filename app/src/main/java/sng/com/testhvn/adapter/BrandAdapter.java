package sng.com.testhvn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sng.com.testhvn.R;
import sng.com.testhvn.model.brand.Brand;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public class BrandAdapter extends BaseAdapter {
    private ArrayList<Brand> mList;
    private Context mContext;

    public BrandAdapter(Context context) {
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public void setData(ArrayList<Brand> list) {
        if (null == mList) {
            mList = new ArrayList<>();
        }
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return (null == mList && mList.size() < position) ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_brand_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTitle.setText(mList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView mTitle;
    }
}
