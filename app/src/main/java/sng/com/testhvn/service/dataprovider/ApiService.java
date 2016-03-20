package sng.com.testhvn.service.dataprovider;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import sng.com.testhvn.R;
import sng.com.testhvn.service.apiRequestModel.BrandResult;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public class ApiService implements ApiCall {
    private ApiCall mApiCall;
    private static ApiService mInstance;
    private OkHttpClient mOkHttpClient;

    private ApiService(Context context) {
        mOkHttpClient = getUnsafeOkHttpClient();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gsonBuilder.create()))
                .setEndpoint(context.getString(R.string.global_base_url))
                .build();
        mApiCall = restAdapter.create(ApiCall.class);
    }

    public static ApiService getInstance(Context mContext) {
        if (null == mInstance) {
            mInstance = new ApiService(mContext);
        }
        return mInstance;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }


                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }


                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };


            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });


            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BrandResult getAllBrand() {
        BrandResult results = mApiCall.getAllBrand();
        return results;
    }

    @Override
    public JsonObject getAllProduct(Callback<Response> cb) {
        final JsonObject[] objReturn = new JsonObject[1];
        mApiCall.getAllProduct(new Callback<Response>() {
            @Override
            public void success(Response result, Response response2) {
                //Try to get response body
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                objReturn[0] = (new JsonParser()).parse(sb.toString()).getAsJsonObject();
            }
            @Override
            public void failure(RetrofitError error) {
                error.toString();
            }
        });
        return objReturn[0];
    }

}
