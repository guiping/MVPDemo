package lvy.so.mvpdemo.model;

import java.io.Serializable;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.14:13
 * @filename WelfareEntity.class
 * @description
 * @TODO
 */
public class DataEntity implements Serializable{

    /**
     * _id : 5719a5e267765974fbfcf94e
     * createdAt : 2016-04-22T12:17:38.977Z
     * desc : 4.22
     * publishedAt : 2016-04-22T12:18:52.507Z
     * source : chrome
     * type : 福利
     * url : http://ww2.sinaimg.cn/large/610dc034gw1f35cxyferej20dw0i2789.jpg
     * used : true
     * who : 代码家
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
