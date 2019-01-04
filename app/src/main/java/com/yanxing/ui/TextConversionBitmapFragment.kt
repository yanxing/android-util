package com.yanxing.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri

import com.nostra13.universalimageloader.core.ImageLoader
import com.yanxing.base.BaseFragment
import com.yanxing.util.FileUtil

import java.io.File
import java.io.FileOutputStream
import kotlinx.android.synthetic.main.fragment_text_conversion_bitmap.*

/**
 * 文字转化为图片，宽度一致，绘画
 * Created by lishuangxiang on 2016/11/7.
 */

class TextConversionBitmapFragment : BaseFragment() {

    override fun getLayoutResID(): Int {
        return R.layout.fragment_text_conversion_bitmap
    }

    override fun afterInstanceView() {
        val content = "把秋衣扎进秋裤/n把秋裤扎进/n袜子/n是对冬天最起码的/n尊重"
        ImageLoader.getInstance().displayImage("file://" + createTextImage(content, "javaScript.png"), image)
    }

    /**
     * 创建图片
     *
     * @param fileName
     * @return
     */
    fun createTextImage(content: String, fileName: String): String {
        val baseFontSize = 20//最小的字为20;
        val bitmap = Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)
        val paint = Paint()
        paint.isDither = true
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint.typeface = Typeface.DEFAULT
        paint.textSize = baseFontSize.toFloat()
        var y = 6
        val x = 10
        val rowLength: Int
        //换行情况，关系：宽度=字符串长度*字体大小
        if (content.contains("/n")) {
            val temp = content.split("/n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val maxLength = maxLength(temp)
            //行文字宽度
            rowLength = if (maxLength * baseFontSize > 200) 200 else maxLength * baseFontSize
            for (i in temp.indices) {
                paint.textSize = (rowLength / temp[i].length).toFloat()
                if (i == 0) {
                    canvas.drawText(temp[i], x.toFloat(), (y + getBaseLine(paint)).toFloat(), paint)
                    y = y + getFontHeight(paint) - 3
                } else {
                    canvas.drawText(temp[i], x.toFloat(), (y + getBaseLine(paint)).toFloat(), paint)
                    y = y + getFontHeight(paint) - 6
                }
            }
        } else {
            canvas.drawText(content, x.toFloat(), (y + getBaseLine(paint)).toFloat(), paint)
        }
        canvas.save()// 保存
        canvas.restore()// 存储
        return save(bitmap, fileName)
    }

    /**
     * 获取最长的字符串长度
     *
     * @param content
     * @return
     */
    fun maxLength(content: Array<String>): Int {
        var temp = 0
        for (i in content.indices) {
            if (temp < content[i].length) {
                temp = content[i].length
            }
        }
        return temp
    }

    /**
     * 获取字体高度
     *
     * @param paint
     * @return
     */
    fun getFontHeight(paint: Paint): Int {
        val fm = paint.fontMetrics
        return Math.ceil((fm.descent - fm.ascent).toDouble()).toInt()
    }

    /**
     * 获取字体baseline线,结合实际显示效果，这里取得粗略值
     * baseline计算http://blog.csdn.net/carrey1989/article/details/10399727
     * http://blog.csdn.net/hursing/article/details/18703599
     *
     * @param paint
     * @return
     */
    fun getBaseLine(paint: Paint): Int {
        val fm = paint.fontMetrics
        return Math.abs(fm.ascent).toInt()
    }

    /**
     * 保存bitmap到外存设备
     *
     * @param bitmap
     * @param fileName
     * @return
     */
    fun save(bitmap: Bitmap, fileName: String): String {
        val file = File(FileUtil.getStoragePath(), fileName)
        val path = file.absolutePath
        try {
            if (!file.exists()) {
                file.mkdirs()
            }
            if (file.exists()) {
                file.delete()
            }
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            activity.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return path
    }
}
