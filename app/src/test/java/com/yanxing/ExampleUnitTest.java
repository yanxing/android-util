package com.yanxing;

import android.text.TextUtils;

import com.yanxing.util.DESUtil;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        String test= DESUtil.decrypt("lKa+InqySfRYF3SJs7%2BzvkOzBaRZvJGLaAs5RFAOXlMmVM7YBPPRh9lxzxySWL6d");
//        System.out.println(test);
////        assertEquals(4, 2 + 2);^((1[3,5,8][0-9])|(14[0-9])|(17[0-9]))\d{8}$  [\w\p{InCJKUnifiedIdeographs}-]{1,26}
        String REGEX_URL_WEB = "http://(m\\.weibo\\.cn){1}(/\\w*)*";
//        String REGEX_URL_WEB="http://(t\\.cn){1}/\\w*";
//        String REGEX_URL_WEB = "http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]";
//        assertEquals(4, 2 + 2);
        Pattern pattern=Pattern.compile("http://(m\\.weibo\\.cn){1}(/\\w*)*");
        Matcher matcher=pattern.matcher("日邮报》）...全文： http://m.weibo.cn/1806503894/4057624951927777");
        System.out.println(matcher.find());
    }

}