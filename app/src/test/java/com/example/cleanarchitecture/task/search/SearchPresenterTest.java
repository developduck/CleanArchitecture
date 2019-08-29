package com.example.cleanarchitecture.task.search;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.UsersRepository;
import com.example.cleanarchitecture.task.search.domain.usecase.GetSearchedUsers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hwang on 2018-03-28.
 *
 * Description :
 */
public class SearchPresenterTest {
    private static final int SEARCH_CONDITION = 20;
    private static final List<Model.User> USER_MODEL_LIST = new ArrayList<>();

    @Mock private UsersRepository repository;
    @Mock private SearchContract.View view;

    private SearchPresenter presenter;

    private SearchPresenter getPresenter(int age) {
        GetSearchedUsers getSearchedUsers = new GetSearchedUsers(repository);
        return new SearchPresenter(age, view, getSearchedUsers);
    }

    @Before
    public void setup() {
        USER_MODEL_LIST.add(new Model.User(10, "Charles", 22, "Republic of Korea"));
        USER_MODEL_LIST.add(new Model.User(14, "Carlos", 19, "Japan"));

        MockitoAnnotations.initMocks(this);
        when(view.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        presenter = getPresenter(SEARCH_CONDITION);
        verify(view).setPresenter(presenter);
    }

    @Test
    public void getSearchedUsersFromRepositoryAndLoadIntoView() {
        presenter = getPresenter(SEARCH_CONDITION);
        presenter.create();

        verify(repository).getSearchedUsers(eq(SEARCH_CONDITION));
        verify(view).show(USER_MODEL_LIST);
    }
}
