package com.mvp.structure.utils;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by thai.cao on 8/1/2019.
 */

@GlideModule
public class GlobalGlideModule extends AppGlideModule{

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
