package com.example.cleanarchitecture.data.source.remote;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.exception.ResponseFailedException;

import java.net.HttpURLConnection;
import java.util.Locale;

import retrofit2.Response;

/**
 * Created by Hwang on 2018-04-16.
 *
 * Description :
 */
public class DefaultRemoteDataSource {
    public <ROOT> ROOT common(Response<ROOT> response) throws ResponseFailedException {
        int responseCode = response.code();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return response.body();
        } else {
            switch (responseCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_NOT_FOUND:
                default:
                    throw new ResponseFailedException(responseCode, String.format(Locale.getDefault(),
                            "%d", responseCode) + ":" + response.message());
            }
        }
    }
    public <DATA> DATA response(Response<Model.Root<DATA>> response) throws ResponseFailedException {
        int responseCode = response.code();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Model.Root<DATA> root = response.body();
            if (root == null || root.getCode() != 0) {
                if (root == null) {
                    throw new ResponseFailedException(-100, null);
                }
                throw new ResponseFailedException(root.getCode(), root.getMessage());
            } else {
                return root.getData();
            }
        } else {
            switch (responseCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_NOT_FOUND:
                default:
                    throw new ResponseFailedException(responseCode, String.format(Locale.getDefault(),
                            "%d", responseCode) + ":" + response.message());
            }
        }
    }
}
