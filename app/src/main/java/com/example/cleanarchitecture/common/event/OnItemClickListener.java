package com.example.cleanarchitecture.common.event;

import android.view.View;

/**
 * Created by Hwang on 2018-03-26.
 *
 * Description : 공통 아이템 클릭 이벤트
 */
public interface OnItemClickListener<T> {
    void onItemClick(View v, int position, T obj);
}