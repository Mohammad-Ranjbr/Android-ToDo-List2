package com.sadra.todo.util;

public interface BasePresenter<T extends BaseView> {

    void onAttach(T view);
    void onDetach();

}
