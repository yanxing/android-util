package com.yanxing.base;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author 李双祥 on 2017/11/10.
 */
public class SampleApplication extends TinkerApplication {

    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.yanxing.base.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
