package com.example.myglide;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class GlideBugTVActivity extends BaseTVActivity {
   private ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
   private Drawable mDrawableSource, mDrawableSmall;
   private AppCompatImageView mIvSource, mIvSmall;
   //   private AppCompatTextView mTvMemAvailable, mTvMemUsed;
   private CustomTarget<Bitmap> mCustomTargetSource, mCustomTargetSmall;

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
//            showMemInfo();
            break;
      }
      return true;
   }

   private void huapingBug(boolean isNext) {
      String url = getNextUrl(isNext);
      if (mCustomTargetSource != null) {
         Glide.with(isNext ? GlideBugTVActivity.this : getApplicationContext()).clear(mCustomTargetSource);
         mCustomTargetSource = null;
      }

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
            mDrawableSource = new BitmapDrawable(getResources(), resource);
            mDrawableSource.setAlpha(255);
            valueAnimator.start();
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            PrettyLogger.glideFlow(errorDrawable);
            super.onLoadFailed(errorDrawable);
            mIvSource.setImageDrawable(errorDrawable);
         }
      };

      mCustomTargetSmall = new CustomTarget<Bitmap>(320, 180) {

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
            mDrawableSmall = new BitmapDrawable(getResources(), resource);
            mIvSmall.setImageDrawable(mDrawableSmall);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            PrettyLogger.glideFlow(errorDrawable);
            super.onLoadFailed(errorDrawable);
            mIvSmall.setImageDrawable(errorDrawable);
         }
      };
      RequestManager requestManagerSource = Glide.with(getApplicationContext());
      RequestBuilder<Bitmap> requestBuilderSource = requestManagerSource
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false);
      requestBuilderSource = requestBuilderSource.load(url);
      requestBuilderSource.into(mCustomTargetSource);

      RequestManager requestManagerSmall = Glide.with(GlideBugTVActivity.this);
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
      mIvSource = findViewById(R.id.iv_source);
      mIvSmall = findViewById(R.id.iv_small);
//      mTvMemAvailable = findViewById(R.id.tv_available_mem);
//      mTvMemUsed = findViewById(R.id.tv_mem_in_use);
      //"https://w.wallhaven.cc/full/rd/wallhaven-rd3pjw.jpg";
      valueAnimator.setDuration(2000);
      valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
         @Override
         public void onAnimationUpdate(ValueAnimator animation) {
            float curVal = (float) animation.getAnimatedValue();
            PrettyLogger.commonLog("curVal:" + curVal);
            mDrawableSource.setAlpha((int) (255 * curVal));
            mIvSource.setImageDrawable(mDrawableSource);
         }
      });
   }

   @Override
   int withLayoutId() {
      return R.layout.activity_glide_bug_tv;
   }

   public void leftClick(View view) {
      huapingBug(false);
   }

   public void rightClick(View view) {
      huapingBug(true);
   }

   public void showMemInfo() {
      ActivityManager activityManager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
      ActivityManager.MemoryInfo curMemInfo = new ActivityManager.MemoryInfo();
      activityManager.getMemoryInfo(curMemInfo);
//      mTvMemAvailable.setText("totalMem: " + curMemInfo.totalMem / 1024 + " | availMem: " + curMemInfo.availMem / 1024);
//      mTvMemUsed.setText("lowMemory: " + curMemInfo.lowMemory + " | threshold: " + curMemInfo.threshold / 1024);
   }
}
