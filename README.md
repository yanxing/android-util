# android-util
## adapterlibrary
ListView、GridView适配器，对BaseAdapter、ViewHolder的封装。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/AdapterExampleActivity.java)
```Java
mMenu.add("菜单1");
mMenu.add("菜单2");
mMenu.add("菜单3");
mMenu.add("菜单4");
mMenu.add("菜单5");
mMenu.add("菜单6");
mMenu.add("菜单7");
mMenu.add("菜单8");
mMyGridView.setAdapter(new CommonAdapter<String>(mMenu,R.layout.adapter_grid_item) {
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setText(R.id.content,mMenu.get(position));
        viewHolder.setBackgroundResource(R.id.img,IMG_MENU[position]);
    }
});
```
## baidumaplibrary
对百度地图API的封装。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/BaiduMapExampleActivity.java)
```Java 
/**
 * 百度地图封装测试
 */
@EActivity(R.layout.activity_baidu_map_example)
public class BaiduMapExampleActivity extends BaseActivity implements RoutePlanResultListener{

    @ViewById(R.id.map)
    LinearLayout mMap;

    private BaiduMapView mBaiduMapView;

    @AfterViews
    @Override
    protected void afterInstanceView() {
        mBaiduMapView=new BaiduMapView(this, mMap);
        mBaiduMapView.setRoutePlanResultListener(this);
        //驾车路径
        LatLng fromLatLng = new LatLng(31.1145130000, 121.4112010000);
        PlanNode senderNode = PlanNode.withLocation(fromLatLng);
        LatLng toLatLng = new LatLng(31.2166060000, 121.4471340000);
        PlanNode receiverNode = PlanNode.withLocation(toLatLng);
        mBaiduMapView.drivingSearch((new DrivingRoutePlanOption()
                .policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST))
                .from(senderNode)
                .to(receiverNode));
        //设置视角中心
        mBaiduMapView.setCenterOnly(fromLatLng.latitude, fromLatLng.longitude);
        //添加覆盖物
        mBaiduMapView.setOverlay(31.1744546784,121.4980140000,R.mipmap.ic_launcher);
    }
    //回调略
  ```
## titlebarlibrary
自定义标题栏。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/TitleBarExampleActivity.java)
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
   </declare-styleable>
```
## tablayoutlibrary
TabLayout+ViewPager封装。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/TabLayoutPagerExampleActivity.java)
```XML
<com.yanxing.tablayoutlibrary.TabLayoutPager
        android:id="@+id/tabLayoutPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:tabLayoutTextColor="@color/colorDark"
        app:tabLayoutIndicatorColor="@color/colorPrimary"
        app:tabLayoutSelectedTextColor="@color/colorPrimary"/>
```
```Java
mFragmentList.add(new TabLayoutPagerFragment_());
mFragmentList.add(new TabLayoutPagerFragment_());
mFragmentList.add(new TabLayoutPagerFragment_());
mStringList.add("菜单一");
mStringList.add("菜单二");
mStringList.add("菜单三");
mTabLayoutPager.addTab(mFragmentList,mStringList);
mTabLayoutPager.setOnTabSelectedListener(new OnTabLayoutPagerListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position=tab.getPosition();
        if (position==0){
            EventBus.getDefault().post("fragment一");
        }else if (position==1){
            EventBus.getDefault().post("fragment二");
        }else if (position==2){
            EventBus.getDefault().post("fragment三");
        }
    }
});
```
