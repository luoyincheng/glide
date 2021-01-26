package com.example.myglide.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myglide.R;
import com.example.myglide.data.GlideDataAdapter.GlideViewHolder;

public class GlideDataAdapter extends RecyclerView.Adapter<GlideViewHolder> {
   @NonNull
   @Override
   public GlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
      return new GlideViewHolder(itemView);
   }

   @Override
   public void onBindViewHolder(@NonNull GlideViewHolder holder, int position) {
      Glide
            .with(holder.itemView.getContext())
            .load(ImgUrls.allUrls.get(position))
            .dontAnimate()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.imageView);
   }

   @Override
   public int getItemCount() {
      return ImgUrls.allUrls.size();
   }

   static class GlideViewHolder extends RecyclerView.ViewHolder {
      private AppCompatImageView imageView;

      public GlideViewHolder(@NonNull View itemView) {
         super(itemView);
         this.imageView = itemView.findViewById(R.id.iv);
      }
   }
}
