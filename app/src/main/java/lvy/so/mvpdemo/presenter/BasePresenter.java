package lvy.so.mvpdemo.presenter;

import android.content.Context;

import lvy.so.mvpdemo.view.impl.IBaseView;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.11:35
 * @filename BasePresenter.class
 * @description
 * @TODO
 */
public abstract class BasePresenter<T extends IBaseView> {
    public Context mContext;
    public T mIBaseView;

     public   BasePresenter(Context context, T iBaseView) {
        this.mContext = context;
        this.mIBaseView = iBaseView;
    }

    public void init() {
        mIBaseView.initView();
    }

    public void replease() {
        if (mContext != null) {
            mContext = null;
            mIBaseView = null;
        }
    }
}
