package com.example.cleanarchitecture.manage.network.retrofit;

import android.os.Build;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hwang on 2018-03-15.
 *
 * Description : 통신을 위한 모듈
 */
public class NetworkModule {
    private String baseUrl;
    private File cacheFile;

    public NetworkModule(String baseUrl, File cacheFile) {
        this.baseUrl = baseUrl;
        this.cacheFile = cacheFile;
    }
    public Retrofit provideRetrofit() {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
            }
        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, certs, new SecureRandom());

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                                    .removeHeader("User-Agent")
                                    .header("Content-Type", "application/json")
                                    .addHeader("User-Agent", "TIGRIS/Android (App:" + "0.1.00" + "/OS:" + Build.VERSION.RELEASE + ")")
                                    .addHeader("Cache-Control", String.format(Locale.getDefault(), "max-age=%d", 432000))
                                    .addHeader("Cookie", "")
                                    .build()))
                            .cache(new Cache(cacheFile, 10 * 1024 * 1024))
                            .hostnameVerifier((hostname, session) -> true)
                            .sslSocketFactory(sslContext.getSocketFactory()/*, trustManager*/)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            return null;
        }
    }
}
