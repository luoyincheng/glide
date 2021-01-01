package com.bumptech.glide.signature;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import java.security.MessageDigest;

/**
 * An empty key that is always equal to all other empty keys.
 */
public final class EmptySignature implements Key {
   private static final EmptySignature EMPTY_KEY = new EmptySignature();

   private EmptySignature() {
      // Empty.
   }

   @NonNull
   public static EmptySignature obtain() {
      return EMPTY_KEY;
   }

   @Override
   public String toString() {
      return "EmptySignature";
   }

   @Override
   public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
      // Do nothing.
   }
}
