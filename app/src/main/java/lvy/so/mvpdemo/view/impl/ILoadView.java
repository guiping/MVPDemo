package lvy.so.mvpdemo.view.impl;

import java.util.List;

import lvy.so.mvpdemo.model.DataEntity;
import lvy.so.mvpdemo.model.WelfareEntity;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.11:27
 * @filename ILoadView.class
 * @description
 * @TODO
 */
public interface ILoadView extends IBaseView{
    void showProgress();
    void hideProgress();
    void loadSucess(List<DataEntity> list);
    void loadFail();
}
