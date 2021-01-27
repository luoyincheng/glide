package com.example.myglide;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.getAlphaSafeConfig;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
      Glide.with(this).load(mFullFile).diskCacheStrategy(DiskCacheStrategy.NONE).into(mIvFull);
      readToHalfFile();
      Glide.with(this).load(mHalfFile).diskCacheStrategy(DiskCacheStrategy.NONE).into(mIvHalf);
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
      byte[] cache = new byte[500];

      FileInputStream fileInputStream = null;
      FileOutputStream fileOutputStream = null;
      try {
         fileInputStream = new FileInputStream(mFullFile);
         fileOutputStream = new FileOutputStream(mHalfFile);
         int curRead;
         curRead = fileInputStream.read(cache);
         fileOutputStream.write(cache, 0, curRead);
         fileOutputStream.flush();
//         while ((curRead = fileInputStream.read(cache)) != -1) {
//            readTimes++;
//            if (readTimes < 200) {
//               fileOutputStream.write(cache, 0, curRead);
//               fileOutputStream.flush();
//            }
//         }
         fileInputStream.close();
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
         PrettyLogger.commonLog("readTimes:" + readTimes);
      }
   }

   /**
    * 因为圆角的四个角落需要用透明色填充，因此生成的这个Bitmap必须有Alpha通道
    */
   private Bitmap roundedCorners(Bitmap inBitmap){
      // Alpha is required for this transformation.
      Bitmap.Config safeConfig = Bitmap.Config.ARGB_8888;
      // 将inBitmap转化为含有Alpha通道的Bitmap
      Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
      Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), safeConfig);

      result.setHasAlpha(true);

      BitmapShader shader =
            new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      paint.setShader(shader);
      RectF rect = new RectF(0, 0, result.getWidth(), result.getHeight());
      BITMAP_DRAWABLE_LOCK.lock();
      try {
         Canvas canvas = new Canvas(result);
         canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
         drawRoundedCornerFn.drawRoundedCorners(canvas, paint, rect);
         clear(canvas);
      } finally {
         BITMAP_DRAWABLE_LOCK.unlock();
      }

      if (!toTransform.equals(inBitmap)) {
         pool.put(toTransform);
      }

      return result;
   }
}
