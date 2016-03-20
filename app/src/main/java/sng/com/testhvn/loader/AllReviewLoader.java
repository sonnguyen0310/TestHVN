package sng.com.testhvn.loader;

import android.content.Context;

import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.impl.DefaultServiceFactory;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class AllReviewLoader extends BaseLoader<CommentResult> {

    public AllReviewLoader(Context context) {
        super(context);
    }

    @Override
    protected CommentResult doLoadInBackground() throws Exception {
        return DefaultServiceFactory.getsInstance(getContext()).getReviewService().getAllComment(getContext());
    }
}
