package com.example.myglide;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.mine.Logger.PrettyLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GlideBugHalfImageActivity extends BaseTVActivity {
   private static int mDefaultColor = Color.parseColor("#ee0000");
   private AppCompatImageView mIvFull, mIvHalf;
   private File mFullFile, mHalfFile;

   //读取一个完整的Bitmap需要的次数
   private long readTimes = 0;

   @Override
   int withLayoutId() {
      return R.layout.activity_half_image;
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mFullFile = getFullImageLocalFile();
      mHalfFile = getHalfImageLocalFile();
      mIvFull = findViewById(R.id.iv_full);
      mIvHalf = findViewById(R.id.iv_half);
      PrettyLogger.commonLog(mFullFile);
      PrettyLogger.commonLog(mHalfFile);
      generateBitmapAndSaveToFullFile();
      Glide.with(this).load(mFullFile).into(mIvFull);
      readToHalfFile();
//      readToHalfFile();
//      Glide.with(this).load(mHalfFile).into(mIvHalf);
   }

   private void generateBitmapAndSaveToFullFile() {
      Bitmap bitmap = Bitmap.createBitmap(500, 500, Config.ARGB_8888);
      for (int i = 0; i < 500; i++) {
         for (int j = 0; j < 500; j++) {
            bitmap.setPixel(i, j, mDefaultColor);
         }
      }
      try {
         FileOutputStream fileOutputStream = new FileOutputStream(mFullFile);
         bitmap.compress(CompressFormat.WEBP, 100, fileOutputStream);
         fileOutputStream.flush();
         fileOutputStream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private File getHalfImageLocalFile() {
      return new File(this.getExternalCacheDir() + "/halfImg");
   }

   private File getFullImageLocalFile() {
      return new File(this.getExternalCacheDir() + "/fullImg");
   }

   private void readToHalfFile() {
      if (mHalfFile == null) { throw new IllegalStateException("mHalfFile does not exists!!!"); }
      byte[] cache = new byte[4 * 1024];

      FileInputStream fileInputStream = null;
      FileOutputStream fileOutputStream = null;
      try {
         fileInputStream = new FileInputStream(mFullFile);
         fileOutputStream = new FileOutputStream(mHalfFile);
         int curRead;
         while ((curRead = fileInputStream.read()) != -1) {
            readTimes++;
            fileOutputStream.write(cache, 0, curRead);
         }
         fileInputStream.close();
         fileOutputStream.flush();
         fileOutputStream.close();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if (fileInputStream != null) {
            try {
               fileInputStream.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         if (fileOutputStream != null) {
            try {
               fileOutputStream.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         PrettyLogger.commonLog("readCount:" + readTimes);
      }
   }
}
