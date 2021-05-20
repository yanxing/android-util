package com.yanxing.ui


import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.yanxing.base.BaseFragment
import com.yanxing.sortlistviewlibrary.CityListActivity
import com.yanxing.ui.animation.AnimationMainFragment
import com.yanxing.ui.tablayout.TabLayoutPagerActivity
import com.yanxing.ui.work.TaskJobService
import com.yanxing.ui.work.TaskWork
import com.yanxing.util.EventBusUtil
import com.yanxing.util.PermissionUtil
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit


/**
 * 菜单列表
 * Created by lishuangxiang on 2016/12/21.
 */
class MainFragment : BaseFragment(){

    override fun getLayoutResID(): Int {
        return R.layout.fragment_main
    }

    override fun afterInstanceView() {
        PermissionUtil.requestPermission(this,arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_SETTINGS)
        ) { result ->
            result?.forEach {
                if (!it.value) {
                    showToast(it.key+"授权失败")
                }
            }
        }

        adapterButton.setOnClickListener {
            replace(CommonAdapterFragment())
        }
        hideTitleBottom.setOnClickListener {
            replace(HideTitleBottomFragment())
        }
        animation.setOnClickListener {
            replace(AnimationMainFragment())
        }
        tabLayoutPager.setOnClickListener {
            val intent=Intent(requireActivity(),TabLayoutPagerActivity::class.java)
            startActivity(intent)
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
        val launcher = registerForActivityResult(ResultContract()) { result -> showToast(result) };
        //城市列表
        sortListView.setOnClickListener {
            launcher.launch(getString(R.string.city_test))
        }
        inputEditButton.setOnClickListener {
            val intent=Intent(requireActivity(), InputEditButtonActivity::class.java)
            startActivity(intent)
        }
        tableView.setOnClickListener {
            replace(TableViewFragment())
        }
        navigationTop.setOnClickListener {
            replace(NavigationTopFragment())
        }
        //workManager例子
        workManager.setOnClickListener {
            val data=Data.Builder().putString("data","workManager周期性测试").build()
            //源码最小周期15分钟
            val periodicWorkRequest= PeriodicWorkRequest.Builder(TaskWork::class.java,15, TimeUnit.SECONDS)
                    .setInputData(data)
                    .build()
            WorkManager.getInstance(requireActivity()).enqueue(periodicWorkRequest)
            val status=WorkManager.getInstance(requireActivity()).getWorkInfoByIdLiveData(periodicWorkRequest.id) as LiveData<WorkInfo>
            status.observe(this,object : Observer<WorkInfo>{

                override fun onChanged(t: WorkInfo) {
                    Log.d("TaskWork任务状态",t.state.name)
                }
            })

            //jobservice
            val componentName = ComponentName(requireActivity(), TaskJobService::class.java)
            val builder = JobInfo.Builder(1000, componentName).setPeriodic(16*60 * 1000L)
            val tm = getSystemService(requireActivity(),JobScheduler::class.java)
            tm?.schedule(builder.build())


        }

    }

    /**
     * 替换新的Fragment
     */
    private fun replace(fragment: androidx.fragment.app.Fragment) {
        EventBusUtil.getDefault().post(fragment)
    }

    class ResultContract: ActivityResultContract<String,String>() {

        override fun createIntent(context: Context, input: String?): Intent {
            val intent=Intent(context,CityListActivity::class.java)
            intent.putExtra("city",input)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            return intent!!.extras!!.getString("city")!!
        }
    }
}
