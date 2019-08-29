package com.example.cleanarchitecture.task;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

/**
 * Created by Hwang on 2018-04-18.
 *
 * Description :
 */
public class PlaygroundTest {
    @Before
    public void setup() {

    }
    @Test
    public void playground() {
        List list = mock(List.class);// mock 생성

        for (int i = 0; i < 10; i++) {
            when(list.get(i)).thenReturn(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(list.get(i));
        }
        for (int i = 0; i < 10; i++) {
            verify(list).get(i);
        }
    }
}
