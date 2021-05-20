# android-util
## 工具类
[CommonUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/CommonUtil.java)(包含关闭显示输入法、
截图、获取通知栏高度、应用是否在前台、检验应用签名、打电话、校验邮箱和手机号等)、[NetworkStateUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/NetworkStateUtil.java)、[DESUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/DESUtil.java)、[ParseJsonUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/ParseJsonUtil.java)、[OpenFileUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/OpenFileUtil.java)（打开word、PPT、excel文件）、[FileUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/FileUtil.java)、[BitmapUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/BitmapUtil.java)、[VideoFrameLoader](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/VideoFrameLoader.java)（加载视频某一帧）、[PermissionUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/PermissionUtil.kt)、
[DownloadImageUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/DownloadImageUtil.java)、[TimeUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/TimeUtil.java)、
[NotchPhoneUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/NotchPhoneUtil.java)、[FileUriUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/FileUriUtil.java) (android10图片视频uri和file互相转化)、[UploadFileUtil](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/util/UploadFileUtil.java)(retrofit上传文件，封装带有回调进度的MultipartBody)


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
百度地图封装(定位、添加marker、路线、POI、搜索建议)。

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
TabLayout+ViewPager2封装,懒加载。

[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/tablayout/TabLayoutPagerActivity.kt)
```XML
 <com.yanxing.tablayoutlibrary.TabLayoutPager
      android:id="@+id/tabLayoutPager"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:tabLayoutBackground="@color/white"
      app:tabLayoutIndicatorColor="@color/colorGreen"
      app:tabLayoutSelectedTextColor="@color/colorGreen"/>
```
```kotlin
mFragmentList.add(TabLayoutPager1Fragment())
mFragmentList.add(TabLayoutPager2Fragment())
mFragmentList.add(TabLayoutPager3Fragment())
mStringList.add(getString(R.string.menu1))
mStringList.add(getString(R.string.menu2))
mStringList.add(getString(R.string.menu3))
tabLayoutPager.addTab(this, mFragmentList, mStringList)
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

## TableView
表格，上滑标题不动，右滑，第一列不动，[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/TableViewFragment.kt)<br>
![image](https://github.com/yanxing/android-util/raw/master/image/1.gif)
