package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/**
 * An {@link java.io.InputStream} that catches {@link java.io.IOException}s during read and skip
 * calls and stores them so they can later be handled or thrown. This class is a workaround for a
 * framework issue where exceptions during reads while decoding bitmaps in {@link
 * android.graphics.BitmapFactory} can return partially decoded bitmaps.
 *
 * <p>Unlike the deprecated {@link ExceptionCatchingInputStream}, this class will both store and
 * re-throw any IOExceptions. Rethrowing works around bugs in wrapping streams that may not fully
 * obey the stream contract. This is really only useful if some middle layer is going to catch the
 * exception (like BitmapFactory) but we want to propagate the exception instead.
 *
 * <p>See https://github.com/bumptech/glide/issues/126 and #4438.
 */
public final class ExceptionPassthroughInputStream extends InputStream {

  private static final Queue<ExceptionPassthroughInputStream> QUEUE = Util.createQueue(0);

  private InputStream wrapped;
  private IOException exception;

  @NonNull
  public static ExceptionPassthroughInputStream obtain(@NonNull InputStream toWrap) {
    ExceptionPassthroughInputStream result;
    synchronized (QUEUE) {
      result = QUEUE.poll();
    }
    if (result == null) {
      result = new ExceptionPassthroughInputStream();
    }
    result.setInputStream(toWrap);
    return result;
  }

  // Exposed for testing.
  static void clearQueue() {
    while (!QUEUE.isEmpty()) {
      QUEUE.remove();
    }
  }

  ExceptionPassthroughInputStream() {
    // Do nothing.
  }

  void setInputStream(@NonNull InputStream toWrap) {
    wrapped = toWrap;
  }

  @Override
  public int available() throws IOException {
    return wrapped.available();
  }

  @Override
  public void close() throws IOException {
    wrapped.close();
  }

  @Override
  public void mark(int readLimit) {
    wrapped.mark(readLimit);
  }

  @Override
  public boolean markSupported() {
    return wrapped.markSupported();
  }

  @Override
  public int read(byte[] buffer) throws IOException {
    try {
      return wrapped.read(buffer);
    } catch (IOException e) {
      exception = e;
      throw e;
    }
  }

  @Override
  public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
    try {
      return wrapped.read(buffer, byteOffset, byteCount);
    } catch (IOException e) {
      exception = e;
      throw e;
    }
  }

  @Override
  public synchronized void reset() throws IOException {
    wrapped.reset();
  }

  @Override
  public long skip(long byteCount) throws IOException {
    try {
      return wrapped.skip(byteCount);
    } catch (IOException e) {
      exception = e;
      throw e;
    }
  }

  @Override
  public int read() throws IOException {
    try {
      return wrapped.read();
    } catch (IOException e) {
      exception = e;
      throw e;
    }
  }

  @Nullable
  public IOException getException() {
    return exception;
  }

  public void release() {
    exception = null;
    wrapped = null;
    synchronized (QUEUE) {
      QUEUE.offer(this);
    }
  }
}
