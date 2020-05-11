# android-util
## 工具类
[CommonUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/CommonUtil.java)(包含关闭显示输入法、
截图、获取通知栏高度、应用是否在前台、检验应用签名、打电话、校验邮箱和手机号等)、[NetworkStateUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/NetworkStateUtil.java)、[DESUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/DESUtil.java)、[ParseJsonUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/ParseJsonUtil.java)、[OpenFileUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/OpenFileUtil.java)（打开word、PPT、excel文件）、[FileUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/FileUtil.java)、[BitmapUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/BitmapUtil.java)、[VideoFrameLoader](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/VideoFrameLoader.java)（加载视频某一帧）、[PermissionUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/PermissionUtil.java)、
[DownloadImageUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/DownloadImageUtil.java)、[TimeUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/TimeUtil.java)、
[NotchPhoneUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/NotchPhoneUtil.java)、[FileUriUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/FileUriUtil.java) (android10文件uri和file互相转化)
## networklibrary
[已经单独作为一个仓库NetworklibraryDemo](https://github.com/yanxing/NetworklibraryDemo)

对retrofit2+rxjava2网络请求的简单封装（包括网络请求时显示等待对话框和请求完成后上下拉刷新控件置为完成状态）。
* gradle 
```java
 compile 'com.yanxing:networklibrary:1.2.1'
 ```
 androidx
 ```java
 compile 'com.yanxing:networklibrary:2.0.4'
 ```
[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/NetworkLibraryFragment.kt)
```java
 RetrofitManage.getInstance().init(mBaseUrl, true)
 RetrofitManage.getInstance().retrofit.create(DouBanAPI::class.java)
                .getTopMovie(0, 10)
                .compose(Transformer<DouBan>().iOMainHasProgress(this, fragmentManager, "请稍等..."))
                .subscribe(object : BaseAbstractObserver<DouBan>(context, fragmentManager) {
                    override fun onCall(douBan: DouBan) {

                    }
                })
```
其中使用了Rxlifecycle2来防止RxJava的内存泄露，所以基类需要继承Rxlifecycle2相关类，或者实现LifecycleProvider接口

### 适配器
ListView、GridView适配器，对BaseAdapter、ViewHolder的封装。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/CommonAdapterFragment.kt)
```Java
mMenu.add("菜单1");
mMenu.add("菜单2");
mMenu.add("菜单3");
mMyGridView.setAdapter(new CommonAdapter<String>(mMenu,R.layout.adapter_grid_item) {
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setText(R.id.content,mMenu.get(position));
        viewHolder.setBackgroundResource(R.id.img,IMG_MENU[position]);
    }
});
```
RecyclerView适配器。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/CommonAdapterFragment.kt)
```Java
mStrings.add("1");
mStrings.add("2");
mStrings.add("3");
//线性
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerViewAdapter = new RecyclerViewAdapter<String>(mStrings,R.layout.adapter_recycler_view){

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder,int position) {
        holder.setText(R.id.text,mStrings.get(position));
    }
};
mRecyclerView.setAdapter(mRecyclerViewAdapter);
```
## baidumaplibrary
百度地图封装(定位、添加marker、路线、POI、搜索建议)。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/BaiduMapFragment.kt)

## amaplibrary
高德地图封装（定位，添加marker）。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/AMapActivity.kt)

## titlebarlibrary
自定义标题栏。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/TitleBarFragment.kt)
```XML
<com.yanxing.titlebarlibrary.TitleBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:title_main_center="true"
        app:back_img_resource="@mipmap/goback_dark"
        app:backgroundColor="@color/gery"
        app:right_icon="@mipmap/share"
        app:title_main_color="@android:color/black"
        app:title_main="标题居中更换返回图标"/>
```
支持属性：
```XML
<declare-styleable name = "MyTitleBar">
        <!-- 标题-->
        <attr name="title_main" format = "string" />
        <!-- 标题居中true,默认靠左false -->
        <attr name="title_main_center" format = "boolean" />
        <!-- 标题颜色-->
        <attr name="title_main_color" format = "color" />
        <!-- 右菜单,如果设置，将可见，默认不可见-->
        <attr name="title_right" format="string"/>
        <!-- titleBar背景色-->
        <attr name="backgroundColor" format="reference|color" />
        <!-- 返回图片隐藏false，默认显示true-->
        <attr name="back_img_visible" format="boolean" />
        <!--返回图片资源-->
        <attr name="back_img_resource" format="reference" />
        <!--右菜单图标，设置将可见，默认不可见-->
        <attr name="right_icon" format="reference"/>
        <!--右菜单文字颜色-->
        <attr name="titleRightColor" format="color"/>
   </declare-styleable>
```
## tablayoutlibrary
TabLayout+ViewPager封装。
* gradle  
```java
 compile 'com.yanxing:tablayoutlibrary:2.0.2'
 ```
[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/tablayout/TabLayoutPagerFragment.kt)
```XML
 <com.yanxing.tablayoutlibrary.TabLayoutPager
      android:id="@+id/tabLayoutPager"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:tabLayoutBackground="@color/white"
      app:tabLayoutIndicatorColor="@color/colorGreen"
      app:tabLayoutSelectedTextColor="@color/colorGreen"/>
```
```Java
mFragmentList.add(new TabLayoutPager1Fragment_());
mFragmentList.add(new TabLayoutPager2Fragment_());
mFragmentList.add(new TabLayoutPager3Fragment_());
mStringList.add("菜单一");
mStringList.add("菜单二");
mStringList.add("菜单三");
mTabLayoutPager.addTab(mFragmentList,mStringList);
mTabLayoutPager.getTabLayout().setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mTabLayoutPager.getViewPager().setCurrentItem(tab.getPosition());
        showToast("第"+(tab.getPosition()+1)+"个");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});
```
## sortlistviewlibrary
城市列表，修改自[http://blog.csdn.net/jdsjlzx/article/details/41052953](http://blog.csdn.net/jdsjlzx/article/details/41052953)。修改UI，增加当前和热门城市。
```Java
intent.setClass(getApplicationContext(), CityListActivity.class);
//当前城市
intent.putExtra("city","上海");
startActivityForResult(intent,QUESTION_SORT_LISTVIEW_CODE);
```
![image](https://github.com/yanxing/android-util/raw/master/sortlistviewlibrary/1.png)
## photodialoglibrary
图片选择（拍照和从图库选择）。
```Java
Intent intent=new Intent(getApplicationContext(), SelectPhotoActivity.class);
mImageName = System.currentTimeMillis() + ".png";
PhotoParam photoParam=new PhotoParam();
photoParam.setName(mImageName);
photoParam.setPath(FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE);
photoParam.setCut(true);
photoParam.setOutputX(480);
photoParam.setOutputY(480);
Bundle bundle=new Bundle();
bundle.putParcelable("photoParam",photoParam);
intent.putExtras(bundle);
startActivityForResult(intent, QUESTION_IMAGE_CODE);
```
## downloadlibrary
多线程下载，支持断点续传。
```Java
DownloadUtils.getInstance().startDownload(getApplicationContext(),url
        , new SimpleDownloadListener() {
    @Override
    public void onStart() {
    }

    @Override
    public void onProgress(int progress, int totalSize) {
    }

    @Override
    public void onError(int state, String message) {
    }

    @Override
    public void onFinish() {
    }
});
```
其他方法:
```Java
stopDownload();//停止下载
resumeDownload();//恢复下载
delete(Context context, String url);//删除下载记录
getDownloadProgressByUrl(Context context, String url);//获取已下载的进度，用作刚进入界面显示用
```
## TableView
表格，上滑标题不动，右滑，第一列不动，[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/TableViewFragment.kt)<br>
![image](https://github.com/yanxing/android-util/raw/master/image/1.gif)
