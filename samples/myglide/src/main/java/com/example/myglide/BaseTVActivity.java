package com.example.myglide;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.mine.Logger.PrettyLogger;
import java.util.Stack;

public abstract class BaseTVActivity extends AppCompatActivity {
   final OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
         PrettyLogger.commonLog();
         v.setBackground(hasFocus ? checkedColorDrawable : uncheckedColorDrawable);
      }
   };

   final ColorDrawable checkedColorDrawable = new ColorDrawable(Color.parseColor("#11ff0000"));
   final ColorDrawable uncheckedColorDrawable = new ColorDrawable(Color.parseColor("#33ffffff"));

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(withLayoutId());
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
}