package sng.com.testhvn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import sng.com.testhvn.R;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.user.User;
import sng.com.testhvn.util.LogUtils;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {
    private ArrayList<Comment> mCommentList;
    private Context mContext;
    private ArrayList<User> mListUser;

    public ReviewAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<Comment> comments, ArrayList<User> listUser) {
        if (null == mCommentList) {
            mCommentList = new ArrayList<>();
        }
        if (null == mListUser) {
            mListUser = new ArrayList<>();
        }
        if (listUser!=null){
            mListUser.clear();
            mListUser.addAll(listUser);
        }
        if (comments!=null){
            mCommentList.clear();
            mCommentList.addAll(sortList(comments));
        }

    }

    private ArrayList<Comment> sortList(ArrayList<Comment> list) {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.addAll(list);
        Collections.sort(comments, new Comparator<Comment>() {
            public int compare(Comment m1, Comment m2) {
                return formatDate(m2.getUpdatedAt()).compareTo(formatDate(m1.getUpdatedAt()));
            }
        });
        return comments;
    }

    private Date formatDate(String data) {
        Date date = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        try {
            date = format1.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String newDateFormat(String data) {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            return df.format(formatDate(data));
        } catch (Exception e) {
            LogUtils.d("sonnguyen", ">>>>>>>  : " +e);
            e.printStackTrace();
            return data;
        }

    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_review_list_item, parent, false);
        return new ReviewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        if (mCommentList == null || position > mCommentList.size() || position == mCommentList.size())
            return;
        if (position < 10 && null != mCommentList.get(position) && null != mCommentList.get(position).getUserID()) {
            try {
                holder.setData(getUserInfo(mCommentList.get(position).getUserID().getObjectId()), mCommentList.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return 10;
//        return (null == mCommentList ? 0 : mCommentList.size());
    }

    public User getUserInfo(String userID) {
        for (int i = 0; i < mListUser.size(); i++) {
            if (userID.equals(mListUser.get(i).getObjectId())) {
                return mListUser.get(i);
            }
        }
        return null;
    }
}

class ReviewHolder extends RecyclerView.ViewHolder {
    private TextView mTvName;
    private TextView mTvRating;
    private TextView mTvComment;
    private TextView mTvDate;
    private ReviewAdapter mReviewAdapter;

    public ReviewHolder(View v, ReviewAdapter adapter) {
        super(v);
        mTvName = (TextView) v.findViewById(R.id.tv_user_name);
        mTvRating = (TextView) v.findViewById(R.id.tv_rating);
        mTvComment = (TextView) v.findViewById(R.id.tv_comment);
        mTvDate = (TextView) v.findViewById(R.id.tv_date);
        mReviewAdapter = adapter;
    }

    public void setData(User user, Comment comment) {
        if (user == null || comment == null ){
            return;
        }
        mTvName.setText("" + user.getUserName());
        mTvDate.setText("" + mReviewAdapter.newDateFormat(comment.getUpdatedAt()));
        mTvRating.setText("" + comment.getRating() / 2);
        mTvComment.setText("" + comment.getComment());
    }
}