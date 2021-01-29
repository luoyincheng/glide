package com.example.myglide;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myglide.data.UltimateAdapter;

public class UltimateRecyclerviewActivity extends BaseTVActivity {
   RecyclerView mRecyclerView;
   UltimateAdapter mUltimateAdapter;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mRecyclerView = findViewById(R.id.recycler);
      mUltimateAdapter = new UltimateAdapter();
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
      linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
      mRecyclerView.setLayoutManager(linearLayoutManager);
      mRecyclerView.setAdapter(mUltimateAdapter);
   }

   @Override
   int withLayoutId() {
      return R.layout.activity_glide_recycler;
   }
}
