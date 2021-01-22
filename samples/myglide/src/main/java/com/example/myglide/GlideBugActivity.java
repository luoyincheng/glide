package com.example.myglide;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.mine.Logger.PrettyLogger;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.util.Arrays;
import java.util.List;

public class GlideBugActivity extends AppCompatActivity {
   private Drawable mDrawable;
   private AppCompatImageView mIvSource, mIvSmall;
   //   private AppCompatTextView mTvMemAvailable, mTvMemUsed;
   private CustomTarget<Bitmap> mCustomTargetSource, mCustomTargetSmall;
   private List<String> mUrls = Arrays.asList(
         "https://clubimg.club.vmall.com/data/attachment/forum/201905/17/082652qm7mayijp9eua6d5.jpg",
         "https://ottimg.cdn.pandora.xiaomi.com/e52cdffbf10abaef0a5daeebeebb48cc.jpg.webp",
         "https://img.leitu123.com/article/UploadPic/2012-7/20127271358077420.jpg",
         "https://ottimg.cdn.pandora.xiaomi.com/6eacc8d2da4296ceea1c54ef38da4f83.jpg.webp",
         "https://images.hdqwalls.com/download/small-memory-lp-1920x1080.jpg",
         "https://ottimg.cdn.pandora.xiaomi.com/a1f6deee50943e8c6e64c21ca2851b97.png.webp",
         "https://images.hdqwalls.com/download/not-in-mood-4k-qy-1920x1080.jpg",
         "https://ottimg.cdn.pandora.xiaomi.com/d22c2450041578e96ac3aa258998122f.jpg.webp",
         "https://www.winwallpapers.net/w1/2018/08/Windows-Logo-Surface-Cubes-1920x1080.jpg",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/b7eece58ee3aa3ab572bf7bec51f5ad2.webp",
         "https://www.winwallpapers.net/w1/2018/08/Windows-Clouds-Sky-Blue-White-1920x1080.jpg",
         "https://ottimg.cdn.pandora.xiaomi.com/6b3b60ce22ffe8e9501ae9759433367c.jpeg.webp",
         "https://www.winwallpapers.net/w1/2018/08/Windows-Logo-Operating-System-1920x1080.jpg",
         "https://ottimg.cdn.pandora.xiaomi.com/ff5eef11775003a9a14c537bbc8f2f08.webp"
   );

   private int mNextIndex = mUrls.size() - 1;

   private String getNextUrl(boolean isNext) {
      if (isNext) { mNextIndex++; } else { mNextIndex--; }
      if (mNextIndex == mUrls.size()) { mNextIndex = 0; }
      if (mNextIndex == -1) { mNextIndex = mUrls.size() - 1; }
      return mUrls.get(mNextIndex);
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      switch (event.getAction()) {
         case MotionEvent.ACTION_DOWN:
            huapingBug(event.getX() > 1080);
            showMemInfo();
            break;
      }
      return true;
   }

   private void huapingBug(boolean isNext) {
      String url = getNextUrl(isNext);
      if (mCustomTargetSource != null) {
         Glide.with(isNext ? GlideBugActivity.this : getApplicationContext()).clear(mCustomTargetSource);
         mCustomTargetSource = null;
      }

//      mCustomTarget = new CustomTarget<Bitmap>(320, 180) {
      mCustomTargetSource = new CustomTarget<Bitmap>() {

         @Override
         public void onLoadCleared(@Nullable Drawable placeholder) {
            PrettyLogger.glideFlow();
         }

         @Override
         public void onLoadStarted(@Nullable Drawable placeholder) {
            PrettyLogger.glideFlow();
            super.onLoadStarted(placeholder);
         }

         @Override
         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            PrettyLogger.glideFlow();
            mDrawable = new BitmapDrawable(getResources(), resource);
            mIvSource.setImageDrawable(mDrawable);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            PrettyLogger.glideFlow(errorDrawable);
            super.onLoadFailed(errorDrawable);
            mIvSource.setImageDrawable(errorDrawable);
         }
      };

      mCustomTargetSmall = new CustomTarget<Bitmap>() {

         @Override
         public void onLoadCleared(@Nullable Drawable placeholder) {
            PrettyLogger.glideFlow();
         }

         @Override
         public void onLoadStarted(@Nullable Drawable placeholder) {
            PrettyLogger.glideFlow();
            super.onLoadStarted(placeholder);
         }

         @Override
         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            PrettyLogger.glideFlow();
            mDrawable = new BitmapDrawable(getResources(), resource);
            mIvSource.setImageDrawable(mDrawable);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            PrettyLogger.glideFlow(errorDrawable);
            super.onLoadFailed(errorDrawable);
            mIvSource.setImageDrawable(errorDrawable);
         }
      };
      RequestManager requestManagerSource = Glide.with(GlideBugActivity.this);
      RequestBuilder<Bitmap> requestBuilderSource = requestManagerSource
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false);
      requestBuilderSource = requestBuilderSource.load(url);
      requestBuilderSource.into(mCustomTargetSource);

      RequestManager requestManagerSmall = Glide.with(GlideBugActivity.this);
      RequestBuilder<Bitmap> requestBuilderSmall = requestManagerSmall
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false);
      requestBuilderSmall = requestBuilderSmall.load(url);
      requestBuilderSmall.into(mCustomTargetSmall);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_glide_bug);
      mIvSource = findViewById(R.id.iv_source);
      mIvSmall = findViewById(R.id.iv_small);
//      mTvMemAvailable = findViewById(R.id.tv_available_mem);
//      mTvMemUsed = findViewById(R.id.tv_mem_in_use);
      //"https://w.wallhaven.cc/full/rd/wallhaven-rd3pjw.jpg";
   }

   public void showMemInfo() {
      ActivityManager activityManager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
      ActivityManager.MemoryInfo curMemInfo = new ActivityManager.MemoryInfo();
      activityManager.getMemoryInfo(curMemInfo);
//      mTvMemAvailable.setText("totalMem: " + curMemInfo.totalMem / 1024 + " | availMem: " + curMemInfo.availMem / 1024);
//      mTvMemUsed.setText("lowMemory: " + curMemInfo.lowMemory + " | threshold: " + curMemInfo.threshold / 1024);
   }
}