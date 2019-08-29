package com.example.cleanarchitecture.di.component;

import com.example.cleanarchitecture.task.main.MainActivity;

/**
 * Created by Hwang on 2018-03-15.
 *
 * Description : 네트워크 모듈을 주입하기 위한 컴포넌트(대거 현재 미사용)
 */
//@Singleton
//@Component(modules={ NetworkModule.class })
public interface NetworkComponent {
    void inject(MainActivity activity);
}
