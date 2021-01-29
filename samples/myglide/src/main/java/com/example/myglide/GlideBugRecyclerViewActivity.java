package com.example.myglide;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myglide.data.GlideDataAdapter;

public class GlideBugRecyclerViewActivity extends BaseTVActivity {
   RecyclerView mRecyclerView;
   GlideDataAdapter mGlideDataAdapter;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mRecyclerView = findViewById(R.id.recycler);
      mGlideDataAdapter = new GlideDataAdapter();
      GridLayoutManager gridLayoutManager = new GridLayoutManager(
            this,
            4,
            VERTICAL,
            false);
      mRecyclerView.setLayoutManager(gridLayoutManager);
      mRecyclerView.setAdapter(mGlideDataAdapter);
   }

   @Override
   int withLayoutId() {
      return R.layout.activity_glide_recycler;
   }
}
