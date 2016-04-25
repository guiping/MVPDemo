package lvy.so.mvpdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/25.14:18
 * @filename WelfareEntity.class
 * @description
 * @TODO
 */
public class WelfareEntity extends BaseEntity {
    @SerializedName("results")
    private List<DataEntity> 福利List;

    public List<DataEntity> get福利List() {
        return 福利List;
    }

    public void set福利List(List<DataEntity> 福利List) {
        this.福利List = 福利List;
    }
}
