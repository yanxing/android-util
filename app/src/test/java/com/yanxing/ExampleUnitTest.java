package com.yanxing;

import com.yanxing.util.DESUtil;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String test= DESUtil.decrypt("lKa+InqySfRYF3SJs7%2BzvkOzBaRZvJGLaAs5RFAOXlMmVM7YBPPRh9lxzxySWL6d");
        System.out.println(test);
//        assertEquals(4, 2 + 2);
    }
}