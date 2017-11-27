package com.cambium.challenge.network;

import android.content.Context;

import com.cambium.challenge.BuildConfig;
import com.cambium.challenge.models.ProjectDetails;
import com.cambium.challenge.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * A class for network calling
 *
 * @author Ajay Kumar Maheshwari
 */

public class RestClient {
    public static final String BASE_URL = "http://starlord.hackerearth.com/";
    private static ApiInterface apiInterface;

    public static ApiInterface getClient(final Context context) {
        if (null == apiInterface) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            OkHttpClient.Builder okClient = new OkHttpClient.Builder();
            okClient.cache(new Cache(context.getCacheDir(), 2 * 1024 * 1024)); // 2 MB
            okClient.connectTimeout(60, TimeUnit.SECONDS);
            okClient.readTimeout(60, TimeUnit.SECONDS);
            okClient.addInterceptor(logging);
            okClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request ongoing = chain.request();
                    if (Utils.isNetworkAvailable(context)) {
                        ongoing = ongoing.newBuilder().header("Cache-Control", "public, max-age=" +
                                60).build();
                    } else {
                        ongoing = ongoing.newBuilder().header("Cache-Control", "public, " +
                                "only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(ongoing);
                }

            });
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = client.create(ApiInterface.class);
            return apiInterface;
        }
        return apiInterface;
    }

    public interface ApiInterface {
        @GET("kickstarter")
        Call<ArrayList<ProjectDetails>> getListOfProjects();


    }
}
