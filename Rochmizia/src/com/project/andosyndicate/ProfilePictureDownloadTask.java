package com.project.andosyndicate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ProfilePictureDownloadTask extends AsyncTask<String, Void, Bitmap>{


	private final ImageView imageView;
	private final Resources resources;

	public ProfilePictureDownloadTask(ImageView imageView, Resources resources) {
		this.imageView = imageView;
		this.resources = resources;
	}

	@Override
	protected Bitmap doInBackground(String... userIds) {
		return downloadProfilePicture(userIds[0]);
	}

	private Bitmap downloadProfilePicture(String userId) {
		URL url = null;
		try {
			url = new URL("https://graph.facebook.com/" + userId + "/picture?type=large");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap sourceImage) {

		Bitmap mask = BitmapFactory.decodeResource(resources, R.drawable.mask);
		Bitmap image = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY));
		Canvas c = new Canvas();
		c.setBitmap(image);
		c.drawBitmap(Bitmap.createScaledBitmap(sourceImage, image.getWidth(), image.getHeight(), false), 0, 0, null);
		c.drawBitmap(mask, 0, 0, paint);

		imageView.setImageBitmap(image);
	}

}
