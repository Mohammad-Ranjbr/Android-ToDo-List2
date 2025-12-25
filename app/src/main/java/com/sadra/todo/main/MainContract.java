package com.sadra.todo.main;

import com.sadra.todo.util.BasePresenter;
import com.sadra.todo.util.BaseView;

public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

    }

}
