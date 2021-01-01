package com.example.myglide;

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
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.util.Arrays;
import java.util.List;

public class GlideFlowActivity extends AppCompatActivity {
   private Drawable mDrawable1, mDrawable2, mDrawable3, mDrawable4;
   private AppCompatImageView mIv1, mIv2, mIv3, mIv4;
   private CustomTarget<Bitmap> mCustomTarget1, mCustomTarget2, mCustomTarget3, mCustomTarget4;
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

   private String getNextUrl() {
      mNextIndex++;
      if (mNextIndex == mUrls.size()) { mNextIndex = 0; }
      if (mNextIndex == -1) { mNextIndex = mUrls.size() - 1; }
      return mUrls.get(mNextIndex);
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      switch (event.getAction()) {
         case MotionEvent.ACTION_DOWN:
            glideFLow();
            break;
      }
      return true;
   }

   private void glideFLow() {
      String url1 = getNextUrl();
      String url2 = getNextUrl();
      String url3 = getNextUrl();
      String url4 = getNextUrl();
      if (mCustomTarget1 != null) {
         Glide.with(GlideFlowActivity.this).clear(mCustomTarget1);
         mCustomTarget1 = null;
      }
      if (mCustomTarget2 != null) {
         Glide.with(GlideFlowActivity.this).clear(mCustomTarget2);
         mCustomTarget2 = null;
      }
      if (mCustomTarget3 != null) {
         Glide.with(GlideFlowActivity.this).clear(mCustomTarget3);
         mCustomTarget3 = null;
      }
      if (mCustomTarget4 != null) {
         Glide.with(GlideFlowActivity.this).clear(mCustomTarget4);
         mCustomTarget4 = null;
      }
//      Glide.with(GlideFlowActivity.this).load(url1).into(mIv1);
//      Glide.with(GlideFlowActivity.this).load(url2).into(mIv2);
//      Glide.with(GlideFlowActivity.this).load(url3).into(mIv3);
//      Glide.with(GlideFlowActivity.this).load(url4).into(mIv4);

      mCustomTarget1 = new CustomTarget<Bitmap>() {

         @Override
         public void onLoadCleared(@Nullable Drawable placeholder) {

         }

         @Override
         public void onLoadStarted(@Nullable Drawable placeholder) {
            super.onLoadStarted(placeholder);
         }

         @Override
         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            mDrawable1 = new BitmapDrawable(getResources(), resource);
            mIv1.setImageDrawable(mDrawable1);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            mIv1.setImageDrawable(errorDrawable);
         }
      };

      mCustomTarget2 = new CustomTarget<Bitmap>() {

         @Override
         public void onLoadCleared(@Nullable Drawable placeholder) {

         }

         @Override
         public void onLoadStarted(@Nullable Drawable placeholder) {
            super.onLoadStarted(placeholder);
         }

         @Override
         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            mDrawable2 = new BitmapDrawable(getResources(), resource);
            mIv2.setImageDrawable(mDrawable2);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            mIv2.setImageDrawable(errorDrawable);
         }
      };

      mCustomTarget3 = new CustomTarget<Bitmap>() {

         @Override
         public void onLoadCleared(@Nullable Drawable placeholder) {

         }

         @Override
         public void onLoadStarted(@Nullable Drawable placeholder) {
            super.onLoadStarted(placeholder);
         }

         @Override
         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            mDrawable3 = new BitmapDrawable(getResources(), resource);
            mIv3.setImageDrawable(mDrawable3);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            mIv3.setImageDrawable(errorDrawable);
         }
      };

      mCustomTarget4 = new CustomTarget<Bitmap>() {

         @Override
         public void onLoadCleared(@Nullable Drawable placeholder) {

         }

         @Override
         public void onLoadStarted(@Nullable Drawable placeholder) {
            super.onLoadStarted(placeholder);
         }

         @Override
         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            mDrawable4 = new BitmapDrawable(getResources(), resource);
            mIv4.setImageDrawable(mDrawable4);
         }

         @Override
         public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            mIv4.setImageDrawable(errorDrawable);
         }
      };

//      TODO: 21-1-13 参数推断为Context，kotlin中会怎样呢？
//      RequestManager requestManager = Glide.with(isNext ? GlideFlowActivity.this : getApplicationContext());
//
      RequestManager requestManager = Glide.with(GlideFlowActivity.this);
      RequestBuilder<Bitmap> requestBuilder1 = requestManager.asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE);
      RequestBuilder<Bitmap> requestBuilder2 = requestManager.asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE);
      RequestBuilder<Bitmap> requestBuilder3 = requestManager.asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE);
      RequestBuilder<Bitmap> requestBuilder4 = requestManager.asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE);
      requestBuilder4 = requestBuilder4.load(url4);
      requestBuilder4.into(mCustomTarget4);
      requestBuilder3 = requestBuilder3.load(url3);
      requestBuilder3.into(mCustomTarget3);
      requestBuilder2 = requestBuilder2.load(url2);
      requestBuilder2.into(mCustomTarget2);
      requestBuilder1 = requestBuilder1.load(url1);
      requestBuilder1.into(mCustomTarget1);

//      requestBuilder1 = requestBuilder1.load(url1);
//      Target<Bitmap> target = requestBuilder1.into(mCustomTarget1);
//      requestBuilder1 = requestBuilder1.load(url2);
//      requestBuilder1.into(mCustomTarget2);
//      requestBuilder1 = requestBuilder1.load(url3);
//      requestBuilder1.into(mCustomTarget3);
//      requestBuilder1 = requestBuilder1.load(url4);
//      requestBuilder1.into(mCustomTarget4);
   }


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_glide_flow);
      mIv1 = findViewById(R.id.iv1);
      mIv2 = findViewById(R.id.iv2);
      mIv3 = findViewById(R.id.iv3);
      mIv4 = findViewById(R.id.iv4);
      //"https://w.wallhaven.cc/full/rd/wallhaven-rd3pjw.jpg";
   }

}
