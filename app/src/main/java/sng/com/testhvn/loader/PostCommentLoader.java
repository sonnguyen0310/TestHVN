package sng.com.testhvn.loader;

import android.content.Context;

import retrofit.client.Response;
import sng.com.testhvn.service.apiRequestModel.PostReview;
import sng.com.testhvn.service.impl.DefaultServiceFactory;

public class PostCommentLoader extends BaseLoader<Response> {
    private PostReview mPostReview;

    public PostCommentLoader(Context context, PostReview postReview) {
        super(context);
        mPostReview = postReview;
    }

    @Override
    protected Response doLoadInBackground() throws Exception {
        return DefaultServiceFactory.getsInstance(getContext()).getReviewService().submitReview(getContext(), mPostReview);
    }
}
