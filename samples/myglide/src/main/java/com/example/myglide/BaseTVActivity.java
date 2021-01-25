package com.example.myglide;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import com.bumptech.glide.Glide;
import com.bumptech.glide.mine.Logger.PrettyLogger;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public abstract class BaseTVActivity extends AppCompatActivity {
   private ConstraintLayout mRootContainer;

   List<String> mUrls = Arrays.asList(
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

   final OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
         PrettyLogger.commonLog();
         v.setBackground(hasFocus ? checkedColorDrawable : uncheckedColorDrawable);
      }
   };

   final ColorDrawable checkedColorDrawable = new ColorDrawable(Color.parseColor("#11ff0000"));
   final ColorDrawable uncheckedColorDrawable = new ColorDrawable(Color.parseColor("#33ffffff"));

   @RequiresApi(api = VERSION_CODES.LOLLIPOP)
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_base_tv);
      mRootContainer = findViewById(R.id.csl_tv_base);
      fullFillWithImageView();
//      LayoutInflater layoutInflater = LayoutInflater.from(this);
//      ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
//      mRootContainer.addView(layoutInflater.inflate(withLayoutId(), null, false), layoutParams);
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

   @RequiresApi(api = VERSION_CODES.LOLLIPOP)
   private void fullFillWithImageView() {
      GridLayout gridLayout = new GridLayout(this);
      gridLayout.setColumnCount(4);
      gridLayout.setRowCount(5);
      gridLayout.setBackground(new ColorDrawable(Color.parseColor("#ff0000")));
//      gridLayout.setPadding(100, 100, 100, 100);
      for (int i = 0; i < gridLayout.getRowCount(); i++) {
         for (int j = 0; j < gridLayout.getColumnCount(); j++) {
            PrettyLogger.commonLog("i:" + i, "j:" + j);
//            AppCompatImageView imageView = new AppCompatImageView(this);
            AppCompatTextView textView= new AppCompatTextView(this);
            textView.setBackground(new ColorDrawable(Color.parseColor("#3c5f78")));
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1.0f);
            GridLayout.Spec columnSpec = GridLayout.spec(j, 1.0f);
            GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
            gridLayoutParams.setGravity(Gravity.CENTER);
            gridLayoutParams.width = 320;
            gridLayoutParams.height = 180;
            gridLayoutParams.setMargins(5, 5, 5, 5);
            gridLayout.addView(textView, gridLayoutParams);
//            PrettyLogger.commonLog("imageView:" + gridLayout.getMeasuredWidth() + ":" + gridLayout.getHeight() + ":" + imageView.getWidth() + ':' + imageView.getHeight());
//            Glide.with(this).load(i * gridLayout.getColumnCount() + j).into(imageView);
         }
      }
      mRootContainer.addView(gridLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
   }
}