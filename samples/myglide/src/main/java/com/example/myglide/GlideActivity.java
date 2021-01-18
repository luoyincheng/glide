package com.example.myglide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GlideActivity extends AppCompatActivity {
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_glide);
   }

   public void glideBug(View view) {
      startActivity(new Intent(this, GlideBugActivity.class));
   }

   public void glideFlow(View view) {
      startActivity(new Intent(this, GlideFlowActivity.class));
   }
}
