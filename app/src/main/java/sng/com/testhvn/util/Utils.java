package sng.com.testhvn.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit.client.Response;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.service.apiRequestModel.PostReview;


public class Utils {
    private static final String PREFERENCE = "Main_preference";

    public static JsonObject toJson(Response result) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (new JsonParser()).parse(sb.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveCommentToPrefrence(Context context, PostReview data) {

        JsonObject userID = new JsonObject();
        userID.addProperty("__type", "Pointer");
        userID.addProperty("className", "User");
        userID.addProperty("objectId", data.getUserID().getObjectId());

        JsonObject productID = new JsonObject();
        productID.addProperty("__type", "Pointer");
        productID.addProperty("className", "Product");
        productID.addProperty("objectId", data.getProductID().getObjectId());

        JsonObject obj = new JsonObject();
        obj.addProperty("comment", data.getComment());
        obj.addProperty("rating", data.getRating());
        obj.addProperty("createdAt", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).format(new Date()));
        obj.addProperty("updatedAt", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).format(new Date()));

        obj.add("productID", productID);
        obj.add("userID", userID);
        savePreference(context, data.getProductID().getObjectId(), obj.toString());
    }

    public static Comment getReview(Context context, String productId) {
        if (productId == null) {
            return null;
        }
        String jsString = readPreference(context, productId);
        if (jsString.length() == 0) {
            return null;
        }
        Gson gson = new Gson();
        Comment comment = gson.fromJson(jsString, Comment.class);
        return comment;
    }

    public static ArrayList<Comment> getProductComment(Product product, ArrayList<Comment> commentResult) {
        ArrayList<Comment> listComment = new ArrayList<>();
        for (Comment comment : commentResult) {
            try {
                if (product.getObjectId().equals(comment.getProductID().getObjectId())) {
                    listComment.add(comment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listComment;
    }

    public static void savePreference(Context context, String key, String value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readPreference(Context context, String key) {
        SharedPreferences preferences;
        preferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        String result = preferences.getString(key, "");
        return result;
    }

    public static String resultTTS(String text) {
        String[] DIGIT = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        String[] NUMBER = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String result = text;
        for (int i = 0; i < DIGIT.length; i++) {
            if (result.toLowerCase().contains(DIGIT[i])) {
                result = result.replace(DIGIT[i], "" + i);
            }
        }
        result = result.replaceAll("\\s+", "");
        return result;
    }
}
