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
标题栏。[example](https://github.com/yanxing/android-util/blob/master/app/src/main/java/com/yanxing/ui/TitleBarExampleActivity.java)
```Java
<com.yanxing.titlebarlibrary.TitleBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:title_main_center="true"
        app:back_img_resource="@mipmap/goback_dark"
        app:backgroundColor="@color/gery"
        app:right_icon="@mipmap/share"
        app:title_main_color="@android:color/black"
        app:title_main="@string/title5"/>
```
