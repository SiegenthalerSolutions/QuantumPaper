/*
 * Copyright (C) 2014 Siegenthaler Solutions.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.siegenthaler.quantum_paper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.siegenthaler.quantum_paper.drawable.FilteredDrawable;

/**
 * Define the manager for the <b>Quantum Paper</b> framework.
 */
public final class QuantumResources extends Resources {
    private final Context mContext;
    private final SparseIntArray mColors;
    private final TypedValue mTypedValue;

    private final SparseArray<Filter> mFilterMapping;
    private final SparseArray<Interceptor> mInterceptorMapping;
    private final SparseIntArray mResourceMapping;
    private final ColorFilterLruCache mColorFilterCache = new ColorFilterLruCache(6);

    /**
     * Default constructor.
     *
     * @param context   the context on which the resources are at.
     * @param resources the resources reference for the drawables.
     */
    public QuantumResources(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mContext = context;
        this.mColors = new SparseIntArray();
        this.mTypedValue = new TypedValue();
        this.mFilterMapping = new SparseArray<>();
        this.mInterceptorMapping = new SparseArray<>();
        this.mResourceMapping = new SparseIntArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getDrawable(int resId) throws NotFoundException {
        return getDrawableFromTheme(super.getDrawable(resId), resId);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public Drawable getDrawableForDensity(int resId, int density) throws NotFoundException {
        return getDrawableFromTheme(super.getDrawableForDensity(resId, density), resId);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.L)
    @Override
    public Drawable getDrawable(int resId, Theme theme) throws NotFoundException {
        return getDrawableFromTheme(super.getDrawable(resId, theme), resId);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.L)
    @Override
    public Drawable getDrawableForDensity(int resId, int density, Theme theme) {
        return getDrawableFromTheme(super.getDrawableForDensity(resId, density, theme), resId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream openRawResource(int resId, TypedValue value) throws NotFoundException {
        return getBitmapStreamFromTheme(resId, value);
    }

    /**
     * Register a filter.
     *
     * @param name   a unique name for the given filter.
     * @param filter a reference to the instance of the filter.
     */
    public void addFilter(String name, Filter filter) {
        mFilterMapping.append(name.hashCode(), filter);
    }

    /**
     * Register an interceptor for the given view.
     *
     * @param name        the name of the view to intercept.
     * @param interceptor the reference to the interceptor.
     */
    public void addInterceptor(String name, Interceptor interceptor) {
        mInterceptorMapping.append(name.hashCode(), interceptor);
    }

    /**
     * Register a resources to the given filter.
     *
     * @param filter a reference to the filter's name.
     * @param resId  the unique identifier of the resource.
     */
    public void addResource(String filter, int resId) {
        mResourceMapping.append(resId, filter.hashCode());
    }

    /**
     * Register a list of resources to the given filter.
     *
     * @param filter a reference to the filter's class.
     * @param resIds a collection that contains the resources ids.
     */
    public void addResources(String filter, int... resIds) {
        final int filterId = filter.hashCode();
        for (int resId : resIds) {
            mResourceMapping.append(resId, filterId);
        }
    }

    /**
     * Retrieve a widget from the resources.
     *
     * @param name    the name of the view to intercept.
     * @param context the context on which the view is created.
     * @param attrs   the attributes of the given view.
     *
     * @return a new reference to the view created, or null if wasn't intercepted.
     */
    public View getWidget(String name, Context context, AttributeSet attrs) {
        final Interceptor interceptor = mInterceptorMapping.get(name.hashCode(), null);
        if (interceptor != null) {
            return interceptor.getWidget(this, context, attrs);
        }
        return null;
    }

    /**
     * Retrieve a drawable with the proper tinting.
     *
     * @param drawable the reference to the drawable.
     * @param resId    the unique identifier of the resource.
     *
     * @return a reference to the drawable tinted or wrapped.
     */
    public Drawable getDrawableFromTheme(Drawable drawable, int resId) {
        final Filter filter = getFilter(resId);
        if (filter != null) {
            drawable = filter.getDrawable(this, drawable, resId);
        }
        return drawable;
    }

    /**
     * Retrieve a stream to a Bitmap with the proper tinting.
     *
     * @param resId the unique identifier of the resource.
     * @param value the reference to the typed value.
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public InputStream getBitmapStreamFromTheme(int resId, TypedValue value) {
        return getStreamFromBitmap(
                getBitmapFromTheme(getBitmapFromResource(resId, value), resId));
    }

    /**
     * Retrieve a bitmap with the proper tinting.
     *
     * @param resId the unique identifier of the resource.
     * @param value the reference to the typed value.
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public Bitmap getBitmapFromTheme(int resId, TypedValue value) {
        if (value == null) {
            value = new TypedValue();
        }
        return getBitmapFromTheme(getBitmapFromResource(resId, value), resId);
    }

    /**
     * Retrieve a bitmap with the proper tinting.
     *
     * @param bitmap the reference to the bitmap.
     * @param resId  the unique identifier of the resource.
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public Bitmap getBitmapFromTheme(Bitmap bitmap, int resId) {
        final Filter filter = getFilter(resId);
        if (bitmap != null && filter != null) {
            return filter.getBitmap(this, bitmap, resId);
        }
        return bitmap;
    }

    /**
     * Retrieve a color from the palette.
     *
     * @param resId a unique identifier of the color.
     *
     * @return a reference to the color in ARGB format.
     */
    public int getThemeAttrColor(int resId) {
        int resource = mColors.get(resId, -1);
        if (resource == -1 && mContext.getTheme().resolveAttribute(resId, mTypedValue, true)) {
            if (mTypedValue.type >= TypedValue.TYPE_FIRST_INT
                    && mTypedValue.type <= TypedValue.TYPE_LAST_INT) {
                resource = mTypedValue.data;
            } else if (mTypedValue.type == TypedValue.TYPE_STRING) {
                resource = super.getColor(mTypedValue.resourceId);
            }
            mColors.append(resId, resource);
        }
        return resource;
    }

    /**
     * Retrieve a color from the palette with alpha applied.
     *
     * @param resId a unique identifier of the color.
     * @param alpha alpha the alpha value to applied.
     *
     * @return a reference to the color in ARGB format.
     */
    public int getThemeAttrColor(int resId, float alpha) {
        final int color = getThemeAttrColor(resId);
        final int originalAlpha = Color.alpha(color);
        return (color & 0x00ffffff) | (Math.round(originalAlpha * alpha) << 24);
    }

    /**
     * Retrieve a color from the palette with alpha applied from a resource.
     *
     * @param resId      a unique identifier of the color.
     * @param alphaResId a unique identifier of the alpha resource.
     *
     * @return a reference to the color in ARGB format.
     */
    public int getThemeAttrColor(int resId, int alphaResId) {
        mContext.getTheme().resolveAttribute(alphaResId, mTypedValue, true);
        return getThemeAttrColor(resId, mTypedValue.getFloat());
    }

    /**
     * Apply color to the given {@link android.graphics.drawable.Drawable} using
     * {@link android.graphics.PorterDuff.Mode#SRC_IN}.
     *
     * @param drawable  a reference to the drawable.
     * @param attribute a resource identifier of the attribute for color.
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public Drawable applyColor(Drawable drawable, int attribute) {
        return applyColor(drawable, attribute, -1, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Apply color to the given {@link android.graphics.drawable.Drawable}.
     *
     * @param drawable  a reference to the drawable.
     * @param attribute a resource identifier of the attribute for color.
     * @param mode      the mode of the tint
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public Drawable applyColor(Drawable drawable, int attribute, PorterDuff.Mode mode) {
        return applyColor(drawable, attribute, -1, mode);
    }

    /**
     * Apply color to the given {@link android.graphics.drawable.Drawable} using
     * {@link android.graphics.PorterDuff.Mode#SRC_IN} and the given alpha value.
     *
     * @param drawable  a reference to the drawable.
     * @param attribute a resource identifier of the attribute for color.
     * @param alpha     a number that represent the alpha value.
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public Drawable applyColor(Drawable drawable, int attribute, int alpha) {
        return applyColor(drawable, attribute, alpha, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Apply color to the given {@link android.graphics.drawable.Drawable}.
     *
     * @param drawable  a reference to the drawable.
     * @param attribute a resource identifier of the attribute for color.
     * @param alpha     a number that represent the alpha value.
     * @param mode      the mode of the tint.
     *
     * @return a reference to the bitmap tinted or wrapped.
     */
    public Drawable applyColor(Drawable drawable, int attribute, int alpha, PorterDuff.Mode mode) {
        final int color = getThemeAttrColor(attribute);
        PorterDuffColorFilter filter = mColorFilterCache.get(color, mode);
        if (filter == null) {
            mColorFilterCache.put(color, mode,
                    filter = new PorterDuffColorFilter(color, mode));
        }
        drawable.setColorFilter(filter);
        if (alpha != -1) {
            drawable.setAlpha(alpha);
        }
        return drawable;
    }

    /**
     * Creates a copy of the bitmap by replacing the color of every pixel
     * by accentColor while keeping the alpha value.
     *
     * @param bitmap      The original bitmap.
     * @param accentColor The color to apply to every pixel.
     *
     * @return A copy of the given bitmap with the accent color applied.
     */
    public Bitmap applyColor(Bitmap bitmap, int accentColor) {
        final int r = Color.red(accentColor);
        final int g = Color.green(accentColor);
        final int b = Color.blue(accentColor);

        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixels.length; i++) {
            final int color = pixels[i];
            final int alpha = Color.alpha(color);
            pixels[i] = Color.argb(alpha, r, g, b);
        }
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }

    /**
     * Retrieve the {@link Filter} for the given resource.
     *
     * @param resId a unique identifier for a resource.
     *
     * @return a reference to the filter of the given resource.
     */
    private Filter getFilter(int resId) {
        final int id = mResourceMapping.get(resId, -1);
        if (id == -1) {
            return null;
        }
        return mFilterMapping.get(id);
    }

    /**
     * Retrieve a bitmap from the device.
     *
     * @param resId the unique identifier of the resource.
     * @param value the values of the resource.
     *
     * @return a reference to the resources loaded.
     */
    private Bitmap getBitmapFromResource(int resId, TypedValue value) {
        final InputStream original = super.openRawResource(resId, value);
        value.density = getDisplayMetrics().densityDpi;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        options.inScreenDensity = getDisplayMetrics().densityDpi;
        return BitmapFactory.decodeResourceStream(
                this, value, original, new Rect(), options);
    }

    /**
     * Retrieve an input stream to the given resource.
     *
     * @param bitmap the reference to the resource bitmap.
     *
     * @return a reference to the stream.
     */
    private InputStream getStreamFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        final byte[] bitmapData = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException ignored) {
        }

        return new ByteArrayInputStream(bitmapData);
    }

    /**
     * Define an interface for intercepting drawables and bitmaps.
     */
    public interface Filter {
        public Drawable getDrawable(QuantumResources manager, Drawable drawable, int resId);

        public Bitmap getBitmap(QuantumResources manager, Bitmap bitmap, int resId);
    }

    /**
     * Define an interface for intercepting views.
     */
    public interface Interceptor {
        public View getWidget(QuantumResources manager, Context context, AttributeSet attributes);
    }

    /**
     * Define a {@link android.util.LruCache} for filters.
     */
    private static final class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int maxSize) {
            super(maxSize);
        }

        public PorterDuffColorFilter get(int color, PorterDuff.Mode mode) {
            return get(generateCacheKey(color, mode));
        }

        public PorterDuffColorFilter put(int color, PorterDuff.Mode mode, PorterDuffColorFilter filter) {
            return put(generateCacheKey(color, mode), filter);
        }

        private static int generateCacheKey(int color, PorterDuff.Mode mode) {
            int hashCode = 1;
            hashCode = 31 * hashCode + color;
            hashCode = 31 * hashCode + mode.hashCode();
            return hashCode;
        }
    }
}
