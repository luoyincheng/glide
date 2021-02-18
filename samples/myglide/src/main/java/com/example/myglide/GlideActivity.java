package com.example.myglide;

import android.content.Intent;
import android.view.View;

public class GlideActivity extends BaseTVActivity {

   @Override
   int withLayoutId() {
      return R.layout.activity_glide;
   }

   public void glideBug(View view) {
      startActivity(new Intent(this, GlideBugActivity.class));
   }

   public void glideTVBug(View view) {
      startActivity(new Intent(this, GlideBugTVActivity.class));
   }

   public void glideFlow(View view) {
      startActivity(new Intent(this, GlideFlowActivity.class));
   }

   public void glideTVBugRecycler(View view) {
      startActivity(new Intent(this, GlideBugRecyclerViewActivity.class));
   }

   public void glideTVBugHalfImage(View view) {
      startActivity(new Intent(this, GlideBugHalfImageActivity.class));
   }

   public void ultimateAdapter(View view) {
      startActivity(new Intent(this, UltimateRecyclerviewActivity.class));
   }

   public void intentActionView(View view) {
   }
}