package com.example.cleanarchitecture.task.detail;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.UsersRepository;
import com.example.cleanarchitecture.task.detail.domain.usecase.GetUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hwang on 2018-03-28.
 *
 * Description :
 */
public class DetailPresenterTest {
    private static final Model.User USER_MODEL = new Model.User(10, "Charles", 22, "Republic of Korea");

    @Mock private UsersRepository repository;
    @Mock private DetailContract.View view;

    private DetailPresenter presenter;

    private DetailPresenter getPresenter(int userId) {
        GetUser getUser = new GetUser(repository);

        return new DetailPresenter(userId, view, getUser);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(view.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        presenter = getPresenter(USER_MODEL.getId());
        verify(view).setPresenter(presenter);
    }

    @Test
    public void getUserFromRepositoryAndLoadIntoView() {
        presenter = getPresenter(USER_MODEL.getId());
        presenter.create();

        verify(repository).getUser(eq(USER_MODEL.getId()));
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).setLoading(true);

        inOrder.verify(view).setLoading(false);
        verify(view).show(USER_MODEL);
    }
}
