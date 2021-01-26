package com.example.myglide;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.mine.Logger.PrettyLogger;
import com.bumptech.glide.request.RequestOptions;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public abstract class BaseTVActivity extends AppCompatActivity {
   private ConstraintLayout mRootContainer;

   List<String> mUrls = Arrays.asList(
         "https://ottimg.cdn.pandora.xiaomi.com/e52cdffbf10abaef0a5daeebeebb48cc.jpg.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/6eacc8d2da4296ceea1c54ef38da4f83.jpg.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/a1f6deee50943e8c6e64c21ca2851b97.png.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/d22c2450041578e96ac3aa258998122f.jpg.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/b7eece58ee3aa3ab572bf7bec51f5ad2.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/6b3b60ce22ffe8e9501ae9759433367c.jpeg.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/ff5eef11775003a9a14c537bbc8f2f08.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/24999f2fe9613479866b071dde61fd2a.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/48da4e170a22c830dc7c18ddc21188df.jpg.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/9bc9afecbbd2bfb33716d25e9c7b95a9.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/3040eb9f7d3cae1ae0dc819c4b658340.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/6a3c88bb67f22d193d51e83a87f0d0d2.jpg.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/e5fa6ccb25babf9c66b353b8c7717df9.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/adfcbb7d521122f9f99478c963be8083.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/a77b7c25ef5ce1cb74e30396da9efae7.png.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/9c90d12633ce44483a1252fe72771f6b.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/790d7adf9880293ab5d67dd69f761d2f.jpg.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/9468936113b2abef9b224ccf67f37088.webp",
         "https://ottimg.cdn.pandora.xiaomi.com/04fc547a6908fa5cd80d26f03d6c2efc.webp",
         "https://image.cdn.pandora.xiaomi.com/mitv/10007/0/b7eece58ee3aa3ab572bf7bec51f5ad2.webp"//重复
   );

   final OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
         PrettyLogger.commonLog();
         v.setBackground(hasFocus ? checkedColorDrawable : uncheckedColorDrawable);
      }
   };

   final ColorDrawable checkedColorDrawable = new ColorDrawable(Color.parseColor("#44ff0000"));
   final ColorDrawable uncheckedColorDrawable = new ColorDrawable(Color.parseColor("#33ffffff"));

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_base_tv);
      mRootContainer = findViewById(R.id.csl_tv_base);
      LayoutInflater layoutInflater = LayoutInflater.from(this);
      ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
      mRootContainer.addView(layoutInflater.inflate(withLayoutId(), null, false), layoutParams);
      registerFocusListenerForAllViews(getWindow().getDecorView());
   }

   abstract int withLayoutId();

   private void registerFocusListenerForAllViews(View rootView) {
      Stack<View> viewStack = new Stack<>();
      viewStack.add(rootView);
      while (!viewStack.isEmpty()) {
         View view = viewStack.pop();
         if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
               viewStack.push(((ViewGroup) view).getChildAt(i));
            }
         } else {
            view.setOnFocusChangeListener(onFocusChangeListener);
         }
      }
   }

   void fullFillWithImageView() {
      GridLayout gridLayout = new GridLayout(this);
      gridLayout.setColumnCount(4);
      gridLayout.setRowCount(5);
      for (int i = 0; i < gridLayout.getRowCount(); i++) {
         for (int j = 0; j < gridLayout.getColumnCount(); j++) {
            PrettyLogger.commonLog("i:" + i, "j:" + j);
            AppCompatImageView imageView = new AppCompatImageView(this);
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1.0f);
            GridLayout.Spec columnSpec = GridLayout.spec(j, 1.0f);
            GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
            gridLayoutParams.setGravity(Gravity.CENTER);
            gridLayoutParams.width = 320;
            gridLayoutParams.height = 180;
            gridLayoutParams.setMargins(5, 5, 5, 5);
            gridLayout.addView(imageView, gridLayoutParams);
            int curIndex = i * gridLayout.getColumnCount() + j;
            if (curIndex > mUrls.size() - 1) { break; }
            Glide
                  .with(this)
                  .load(mUrls.get(curIndex))
                  .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                  .into(imageView);
         }
      }
      mRootContainer.addView(gridLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
   }
}