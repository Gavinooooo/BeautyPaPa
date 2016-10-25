package com.rance.beautypapa.view;

import com.rance.beautypapa.base.BaseView;
import com.rance.beautypapa.model.VideoEntity;

import java.util.List;

/**
 * 作者：Rance on 2016/10/25 15:19
 * 邮箱：rance935@163.com
 */
public interface MainView extends BaseView {

    void getVideoSuccess(List<VideoEntity> videoEntity);

    void getVideoFail(String msg);

}
