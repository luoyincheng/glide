package com.example.myglide;

import static com.example.myglide.data.ImgUrls.mWEBPs;

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
            if (curIndex > mWEBPs.size() - 1) { break; }
            Glide
                  .with(this)
                  .load(mWEBPs.get(curIndex))
                  .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                  .into(imageView);
         }
      }
      mRootContainer.addView(gridLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
   }
}