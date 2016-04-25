package lvy.so.mvpdemo.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lvy.so.mvpdemo.R;
import lvy.so.mvpdemo.model.DataEntity;
import lvy.so.mvpdemo.model.WelfareEntity;
import lvy.so.mvpdemo.presenter.LoadPresenter;
import lvy.so.mvpdemo.view.adapter.LoadDataAdapter;
import lvy.so.mvpdemo.view.impl.ILoadView;
import lvy.so.mvpdemo.widget.RefreshRecyclerView;

public class MainActivity extends BaseActivity implements ILoadView,RefreshRecyclerView.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycleview_showMessage)
    RefreshRecyclerView recycleviewShowMessage;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;
    private LoadPresenter mLoadPresenter;
    private LoadDataAdapter loadDataAdapter;
    private List<DataEntity> mList;
    private boolean isLoader = true;  //是否是加载数据
    private int pager = 1;   //加载那一页数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        initPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {   //初始化Presenter
        mLoadPresenter = new LoadPresenter(this, this);
        mLoadPresenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        if (!swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgress() {
        if (swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void loadSucess(List<DataEntity> list) {
        if (isLoader) {
            mList.addAll(list);
        } else {
            mList.clear();
            mList.addAll(list);

        }
        if (loadDataAdapter != null) {
            loadDataAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadFail() {
        Snackbar.make(recycleviewShowMessage, "请求出错了,请重新尝试", Snackbar.LENGTH_LONG).setAction("重新请求", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "重新请求--->>>");
                mLoadPresenter.loadData("福利",pager);
            }
        }).show();
    }

    @Override
    public void initView() {
        fab.setOnClickListener(new View.OnClickListener() {  //刷新
            @Override
            public void onClick(View view) {
                isLoader = false;
                pager = 1;  //请求第一页的数据
                mLoadPresenter.loadData("福利",pager);
            }
        });
        mList = new ArrayList<>();
        loadDataAdapter = new LoadDataAdapter(this, mList);
        recycleviewShowMessage.setLayoutManager(new LinearLayoutManager(this));
        recycleviewShowMessage.setAdapter(loadDataAdapter);
        recycleviewShowMessage.setOnLoadMoreListener(this);
        swiperefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public void loadMore() {
        pager ++;
        isLoader = true;
        mLoadPresenter.loadData("福利",pager);
    }

    @Override
    public void onRefresh() {
        pager = 1;
        isLoader = false;
        mLoadPresenter.loadData("福利",pager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadPresenter.replease();
    }
}
