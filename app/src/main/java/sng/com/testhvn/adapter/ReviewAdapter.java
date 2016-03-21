package sng.com.testhvn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sng.com.testhvn.R;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.user.User;

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
        mListUser.addAll(listUser);
        mCommentList.addAll(comments);
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_review_list_item, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
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

    public ReviewHolder(View v) {
        super(v);
        mTvName = (TextView) v.findViewById(R.id.tv_user_name);
        mTvRating = (TextView) v.findViewById(R.id.tv_rating);
        mTvComment = (TextView) v.findViewById(R.id.tv_comment);
    }

    public void setData(User user, Comment comment) {
        mTvName.setText("" + user.getUserName().toString());
        mTvRating.setText("" + comment.getRating());
        mTvComment.setText("" + comment.getComment());
    }
}