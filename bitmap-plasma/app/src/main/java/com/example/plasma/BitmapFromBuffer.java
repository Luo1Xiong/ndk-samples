package com.example.plasma;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class BitmapFromBuffer {

	@TargetApi(Build.VERSION_CODES.KITKAT)
	protected void createBitmapFromBufferData(ImageView imageView) {
		imageView.setDrawingCacheEnabled(true);

		Bitmap bitmap = imageView.getDrawingCache();
		System.out.println("bitmapInfo: width:" + bitmap.getWidth() + " height:" + bitmap.getHeight() + " pixelCount:" + bitmap.getAllocationByteCount());

		Class<?> bitmapClass = bitmap.getClass();
		try {
			@SuppressLint("PrivateApi") Field field = bitmapClass.getDeclaredField("mBuffer");
			field.setAccessible(true);
			byte[] mBuffer = (byte[]) field.get(bitmap);

			Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
			newBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(mBuffer));

			System.out.println("mBuffer:" + mBuffer.length);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
