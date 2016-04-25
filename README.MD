## MVP在项目中的应用

> [百科对于MVP的解释](http://baike.baidu.com/subview/7294/10754979.htm) 

> [Google官方MVP](https://github.com/googlesamples/android-architecture)

* MVC模式的交互图

 ![](https://github.com/guiping/MVPDemo/tree/master/app/art/MVC.png)
 ![](https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTNLwhzM4Rn9ub67IbYyJIKKExu5Pj4J2Y3QbtL4TS_7PcgiwqRjg)
 
 1. View：android中的UI界面，展现界面给用户的界面，负责和用户交互
 2. Model：android中的数据层，实体类，数据库，文件等一些数据模型。
 3. Control： android 负责接收用户的事件，进行逻辑处理。例：进行网络请求，读取数据库，保存文件等等一系列逻辑控制处理
   
> android开发中，往往UI是易变的，指不定什么时候产品就修改一次，最痛苦的是一个Ui界面有时候会处理很多逻辑。而MVC模式，允许View和Model直接交互，如果view既可以和Model交互又可以通过control和model交互，会导致View代码臃肿,想想都有点混乱。这样如果产品UI和逻辑修改，会很痛苦的。

* MVP模式

![](https://github.com/guiping/MVPDemo/tree/master/app/art/MVP图.png)

     1. View：android中的UI界面，展现界面给用户的界面，负责和用户交互
     2. Model：android中的数据层，实体类，数据库，文件等一些数据模型。
     3. Presenter:中间人和MVC中的Control类似
     4. IView： 回调接口，负责更新View界面。
 
 > MVP开发不允许View和Model直接交互，必须通过P来控制View和M交互。 这样V只有V和P的交互，没有v和M的交互，这样如果UI修改了我们只需要修改UI逻辑处理层不需要改变，如果逻辑修改了我们直接修改p层，不会在v中去修改逻辑，代码耦合度大大的降低。P层的代码也可以多界面复用。
 
##MVPDemo详解
>借http://gank.io/api/开源Api 实现一个MVP的Demo

* 项目整体框架图：

![](https://github.com/guiping/MVPDemo/tree/master/app/art/MVP代码.png)


* 需求就是通过请求api接口，实现读取数据，并且可以加载更多，和刷新内容。
* 数据刷新采用V4包里面的SwipeRefreshLayout控件，加载更多是重写RecycleView的onScrollStateChanged方法判断如果到可显示最后第二条数据的时候就去请求加载更多数据。--->也就是一个分页
>RecycleView加载更多：

        
	      @Override
	    public void onScrollStateChanged(int state) {
	        LinearLayoutManager llm = (LinearLayoutManager) getLayoutManager();
	        if (state == RefreshRecyclerView.SCROLL_STATE_IDLE) {
	            int lastVisiblePosition = llm.findLastCompletelyVisibleItemPosition(); //当前可显示最后一条数据
	            int totalVisiblePosition = llm.getItemCount(); //获取当前界面总共有多少条数据
	            if (lastVisiblePosition == (totalVisiblePosition - 1)) {  // 判断可见 数据最后一天是不是 是总数据的倒数第二条 如果是就接口回调加载更多
	                if (loadMoreListener != null) {
	                    loadMoreListener.loadMore();
	                }
	            }
	        }
	        super.onScrollStateChanged(state);
	    }

	    private OnLoadMoreListener loadMoreListener;
	    /**设置接口*/
	    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) { 
	        this.loadMoreListener = onLoadMoreListener;
	    }
	    /**加载更多的接口*/
	    public interface OnLoadMoreListener {
	        void loadMore();
	    }


* 接下来看看IView 的实现：

        public interface ILoadView extends IBaseView{
		    void showProgress();  //显示对话框回调接口
		    void hideProgress();  //隐藏对话框回调接口
		    void loadSucess(List<DataEntity> list); //数据请求成功回调接口
		    void loadFail();      //数据请求失败回调接口
        }

* Presenter的代码实现

	    public class LoadPresenter extends BasePresenter<ILoadView> {
	    private Call<WelfareEntity> welfareEntityCall;
	    public LoadPresenter(Context context, ILoadView iBaseView) {
	        super(context, iBaseView);
	    }
	
	    @Override
	    public void init() {
	        super.init();
	    }
	
	    public void loadData(String type, int pager) {  //请求数据
	        if( mIBaseView != null ) {
	            mIBaseView.showProgress();
	        }
	         welfareEntityCall = HttpClient.getDataRetrofitInstanse().getData(type, pager);
	        welfareEntityCall.enqueue(new Callback<WelfareEntity>() {
	            @Override
	            public void onResponse(Call<WelfareEntity> call, Response<WelfareEntity> response) {
	                if( mIBaseView != null ) {
	                    mIBaseView.hideProgress();
	                   mIBaseView.loadSucess(response.body().get福利List());
	                }
	            }
	
	            @Override
	            public void onFailure(Call<WelfareEntity> call, Throwable t) {
	                Log.e("TAG",t.getMessage()+"");
	                if( mIBaseView != null ) {
	                    mIBaseView.hideProgress();
	                    mIBaseView.loadFail();
	                }
	            }
	        });
	    }
	
	    @Override
	    public void replease() {  //释放资源 在View的onDestory()中调用
	        super.replease();
	        if (welfareEntityCall != null) {
	            welfareEntityCall.cancel();   //取消网络请求
	        }
	
	     }
	    }

    
* 接下来看看View界面实现代码：


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
		    public void initPresenter() {   //初始化Presenter 和View绑定
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
		                mLoadPresenter.loadData("福利",pager);   //
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
		    public void loadMore() { //加载更多
		        pager ++;
		        isLoader = true;
		        mLoadPresenter.loadData("福利",pager);
		    }
		
		    @Override
		    public void onRefresh() {  //刷新数据
		        pager = 1;
		        isLoader = false;
		        mLoadPresenter.loadData("福利",pager);
		    }
		
		    @Override
		    protected void onDestroy() {
		        super.onDestroy();
		        mLoadPresenter.replease();  //释放Presenter绑定的View
		    }
	    }




>具体细节可以查看代码实现

>感谢 @gank.io 提供开源api

>网络请求用的是Retrofit [详细](http://square.github.io/retrofit/)
 

