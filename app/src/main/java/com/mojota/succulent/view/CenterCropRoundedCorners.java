package com.mojota.succulent.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 *
 * Created by mojota on 18-8-24.
 */
public class CenterCropRoundedCorners extends CenterCrop {
    private static final String ID = "com.mojota.succulent.view.CenterCropRoundedCorners";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int mRoundingRadius;

    public CenterCropRoundedCorners(int roundingRadius) {
        this.mRoundingRadius = roundingRadius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int
            outWidth, int outHeight) {
        Bitmap cropBitmap = super.transform(pool, toTransform, outWidth, outHeight);
        return TransformationUtils.roundedCorners(pool, cropBitmap, mRoundingRadius);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CenterCropRoundedCorners) {
            CenterCropRoundedCorners other = (CenterCropRoundedCorners) o;
            return mRoundingRadius == other.mRoundingRadius;
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(), Util.hashCode(mRoundingRadius));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putInt(mRoundingRadius).array();
        messageDigest.update(radiusData);
    }

}
