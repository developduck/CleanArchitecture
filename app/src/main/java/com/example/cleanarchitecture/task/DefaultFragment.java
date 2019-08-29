package com.example.cleanarchitecture.task;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.common.utils.StringUtils;

/**
 * Created by tigris on 2017-08-02.
 * 작성자 : 황의택
 */
public class DefaultFragment extends Fragment {
    public int getColor(@ColorRes int id, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getActivity().getResources().getColor(id, theme);
        } else {
            return getActivity().getResources().getColor(id);
        }
    }
    public static <T extends DefaultFragment> T create(Class<T> clazz) {
        T fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }
    public static <T extends DefaultFragment> T create(Class<T> clazz, Bundle params) {
        T fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment != null) fragment.setArguments(params);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String layout = "fragment_" + StringUtils.getAliasWithUnderBar(this.getClass().getSimpleName().replace("Fragment", ""));
        View view = inflater.inflate(getActivity().getResources().getIdentifier(layout, "layout", getActivity().getPackageName()), container, false);
        return onCreateView(view, inflater, container, savedInstanceState);
    }
    public View onCreateView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }
}