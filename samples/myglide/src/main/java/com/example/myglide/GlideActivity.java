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
}