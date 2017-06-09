package com.beta.tacademy.hellomoneycustomer.module.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

public class HelloMoneyCustomerGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        /*
          현재 Glide이 관리하는 캐쉬사이즈에 10%를 증가한다.
         */
        int customMemoryCacheSize = (int) (1.1 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.1 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //더 선명하게 보여줄 팀은 DecodeFormat.PREFER_ARGB_8888로 설정(메모리소모많음)
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);

        // 디스크캐쉬 설정방법
       /* String downloadDirectoryPath =
       Environment.getDownloadCacheDirectory().getPath();
        int  diskCacheSize = 1024 * 1024 * 100; //100 메가
        builder.setDiskCache(
                new DiskLruCacheFactory(downloadDirectoryPath, diskCacheSize)
        );*/

    }
    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
