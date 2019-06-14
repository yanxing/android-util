package com.yanxing.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.*

import com.photo.ui.PhotoUtilsActivity
import com.yanxing.base.BaseFragment
import com.yanxing.dialog.PhotoParam
import com.yanxing.dialog.SelectPhotoActivity
import com.yanxing.sortlistviewlibrary.CityListActivity
import com.yanxing.ui.animation.AnimationMainFragment
import com.yanxing.ui.tablayout.TabLayoutPagerFragment
import com.yanxing.ui.work.TaskWork
import com.yanxing.util.ConstantValue
import com.yanxing.util.EventBusUtil
import com.yanxing.util.FileUtil
import com.yanxing.util.PermissionUtil


import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit
import android.app.job.JobInfo
import androidx.work.impl.Schedulers.schedule
import android.content.Context.JOB_SCHEDULER_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.job.JobScheduler
import android.content.Context
import android.content.ComponentName
import androidx.work.impl.utils.futures.SettableFuture
import com.yanxing.ui.work.TaskJobService


/**
 * 菜单列表
 * Created by lishuangxiang on 2016/12/21.
 */
class MainFragment : BaseFragment(){
    //选择的图片名称
    private var mImageName: String? = null
    private val QUESTION_IMAGE_CODE = 1
    private val QUESTION_SORT_LISTVIEW_CODE = 2
    private val QUESTION_LOCATION = 3
    private val REQUEST_CODE_CHOOSE = 4

    override fun getLayoutResID(): Int {
        return R.layout.fragment_main
    }

    override fun afterInstanceView() {
        // showToast("测试Tinker热更新");
        checkPermission()
        adapterButton.setOnClickListener {
            replace(CommonAdapterFragment())
        }
        hideTitleBottom.setOnClickListener {
            replace(HideTitleBottomFragment())
        }
        downloadlibrary.setOnClickListener {
            replace(DownloadLibraryFragment())
        }
        RxJava2.setOnClickListener {
            replace(NetworkLibraryFragment())
        }
        animation.setOnClickListener {
            replace(AnimationMainFragment())
        }
        tabLayoutPager.setOnClickListener {
            replace(TabLayoutPagerFragment())
        }
        selectCity.setOnClickListener {
            val selectCityFragment = SelectCityFragment()
            val bundle = Bundle()
            bundle.putString("currentCity", getString(R.string.city_test))
            selectCityFragment.arguments = bundle
            replace(selectCityFragment)
        }
        titleBar.setOnClickListener {
            replace(TitleBarFragment())
        }
        map.setOnClickListener {
            replace(BaiduMapFragment())
        }
        extendRecyclerView.setOnClickListener {
            replace(ExtendRecyclerViewFragment())
        }
        amap.setOnClickListener {
            val intent=Intent(activity,AMapActivity::class.java)
            startActivity(intent)
        }
        MPAndroidChart.setOnClickListener {
            replace(MPAndroidChartFragment())
        }
        val intent = Intent()
        val bundle = Bundle()
        selectImage.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            intent.setClass(activity, PhotoUtilsActivity::class.java)
            mImageName = currentTime.toString() + ".png "
            bundle.putString("path", FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE)
            bundle.putString("name", mImageName)
            bundle.putBoolean("cut", true)
            intent.putExtras(bundle)
            startActivityForResult(intent, QUESTION_IMAGE_CODE)
        }
        selectImageDialog.setOnClickListener {
            val currentTimeDialog = System.currentTimeMillis()
            intent.setClass(activity, SelectPhotoActivity::class.java)
            mImageName = currentTimeDialog.toString() + ".png"
            val photoParam = PhotoParam()
            photoParam.name = mImageName
            photoParam.path = FileUtil.getStoragePath() + ConstantValue.CACHE_IMAGE
            val bundle1 = Bundle()
            bundle1.putParcelable("photoParam", photoParam)
            intent.putExtras(bundle1)
            startActivityForResult(intent, QUESTION_IMAGE_CODE)
        }
        //城市列表
        sortListView.setOnClickListener {
            intent.setClass(activity, CityListActivity::class.java)
            intent.putExtra("city", getString(R.string.city_test))
            startActivityForResult(intent, QUESTION_SORT_LISTVIEW_CODE)
        }
        inputEditButton.setOnClickListener {
            intent.setClass(activity, InputEditButtonActivity::class.java)
            startActivity(intent)
        }
        tableView.setOnClickListener {
            replace(TableViewFragment())
        }
        navigationTop.setOnClickListener {
            replace(NavigationTopFragment())
        }
        webOpenPhoto.setOnClickListener {
            intent.setClass(activity, WebOpenPhotoActivity::class.java)
            startActivity(intent)
        }
        //workManager例子
        workManager.setOnClickListener {
            val data=Data.Builder().putString("data","workManager周期性测试").build()
            //源码最小周期15分钟
            val periodicWorkRequest= PeriodicWorkRequest.Builder(TaskWork::class.java,15, TimeUnit.SECONDS)
                    .setInputData(data)
                    .build()
            WorkManager.getInstance(activity!!).enqueue(periodicWorkRequest)
            val status=WorkManager.getInstance(activity!!).getWorkInfoByIdLiveData(periodicWorkRequest.id) as LiveData<WorkInfo>
            status.observe(this,object : Observer<WorkInfo>{

                override fun onChanged(t: WorkInfo) {
                    Log.d("TaskWork任务状态",t.state.name)
                }
            })

            //jobservice
            val componentName = ComponentName(activity, TaskJobService::class.java)
            val builder = JobInfo.Builder(1000, componentName).setPeriodic(16*60 * 1000L)
            val tm = getSystemService(activity!!,JobScheduler::class.java)
            tm?.schedule(builder.build())


        }

    }

    /**
     * 申请定位权限
     */
    fun checkPermission() {
        if (PermissionUtil.findNeedRequestPermissions(activity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS)).size > 0) {
            PermissionUtil.requestPermission(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS), QUESTION_LOCATION)
        }
    }

    /**
     * 替换新的Fragment
     */
    private fun replace(fragment: androidx.fragment.app.Fragment) {
        EventBusUtil.getDefault().post(fragment)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == androidx.fragment.app.FragmentActivity.RESULT_OK) {
            if (requestCode == QUESTION_IMAGE_CODE) {
                showToast("图片地址:"+data?.getStringExtra("image"))
            } else if (requestCode == QUESTION_SORT_LISTVIEW_CODE) {
                showToast(data!!.extras!!.getString("city")!!)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == QUESTION_LOCATION) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    for (permission in permissions) {
                        PermissionUtil.getPermissionTip(permission)
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
