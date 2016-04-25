package lvy.so.mvpdemo.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import lvy.so.mvpdemo.R;
import lvy.so.mvpdemo.model.DataEntity;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ping on 2016/4/25.
 * 显示图片详情界面  支持手势放大缩小
 */
public class ShowPhotoDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_showPhoto)
    ImageView ivShowPhoto;
    private DataEntity dataEntity;

    @Override
    public void initPresenter() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        Bundle bun  = getIntent().getBundleExtra("dataEntityBundle");
        if (bun != null ) {
            dataEntity = (DataEntity) bun.getSerializable("dataEntity");
        }
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(dataEntity.getDesc()+"");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this).load(dataEntity.getUrl()).centerCrop().into(ivShowPhoto);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(ivShowPhoto);
        photoViewAttacher.update();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_showphotodetail;
    }
}
