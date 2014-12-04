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
import android.content.res.ColorStateList;
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
import android.util.LruCache;
import android.util.TypedValue;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.siegenthaler.quantum_paper.interceptor.ButtonInterceptor;
import me.siegenthaler.quantum_paper.filter.TextFieldHandleDrawableFilter;
import me.siegenthaler.quantum_paper.interceptor.TextFieldInterceptor;
import me.siegenthaler.quantum_paper.filter.ButtonDrawableFilter;
import me.siegenthaler.quantum_paper.filter.TextFieldDrawableFilter;

/**
 * Implementation for {@link android.content.res.Resources} to allow intercept resources
 * to allow tinting drawable at runtime.
 */
public final class QuantumResources extends Resources {
    /**
     * Define an interface for intercepting drawables.
     */
    public interface DrawableFilter {
        public Drawable getDrawable(QuantumResources resources, Drawable drawable, int resId);
    }

    /**
     * Define an interface for intercepting bitmaps.
     */
    public interface BitmapFilter {
        public Bitmap getDrawable(QuantumResources resources, Bitmap bitmap, TypedValue value);
    }

    /**
     * Define an interface for intercepting views.
     */
    public interface ViewInterceptor {
        public View getView(QuantumResources resources, String name, Context context, AttributeSet attributes);
    }

    private final Context mContext;
    private final TypedValue mTypedValue;
    private final List<DrawableFilter> mDrawableFilters = new ArrayList<>();
    private final List<ViewInterceptor> mViewFilters = new ArrayList<>();
    private final List<BitmapFilter> mBitmapFilters = new ArrayList<>();

    private final ColorFilterLruCache mColorFilterCache = new ColorFilterLruCache(6);
    private ColorStateList mDefaultColorStateList;

    /**
     * Default constructor.
     *
     * @param context   the context on which the resources are.
     * @param resources the resources reference for the drawables.
     */
    public QuantumResources(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mContext = context;
        this.mTypedValue = new TypedValue();

        addBitmapDrawableFilter(new TextFieldHandleDrawableFilter());

        addViewFilter(new TextFieldInterceptor());
        addViewFilter(new ButtonInterceptor());

        addDrawableFilter(new TextFieldDrawableFilter());
        addDrawableFilter(new ButtonDrawableFilter());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getDrawable(int resId) throws NotFoundException {
        return getThemeDrawable(super.getDrawable(resId), resId);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public Drawable getDrawableForDensity(int resId, int density) throws NotFoundException {
        return getThemeDrawable(super.getDrawableForDensity(resId, density), resId);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.L)
    @Override
    public Drawable getDrawable(int resId, Theme theme) throws NotFoundException {
        return getThemeDrawable(super.getDrawable(resId, theme), resId);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.L)
    @Override
    public Drawable getDrawableForDensity(int resId, int density, Theme theme) {
        return getThemeDrawable(super.getDrawableForDensity(resId, density, theme), resId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream openRawResource(int resId, TypedValue value) throws NotFoundException {
        final Bitmap bitmap = getBitmapFromResource(resId, value);

        Bitmap result;
        if (bitmap != null) {
            for (final BitmapFilter filter : mBitmapFilters) {
                result = filter.getDrawable(this, bitmap, value);
                if (result != null)
                    return getStreamFromBitmap(result);
            }
        }
        return getStreamFromBitmap(bitmap);
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
                this, value, original,
                new Rect(), options);
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
        final byte[] bitmapData = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException e) { /* ignore */}

        return new ByteArrayInputStream(bitmapData);
    }

    /**
     * Retrieve a drawable from the theme manager.
     *
     * @param drawable a reference to the drawable
     * @param resId    the reference to the drawable identifier
     *
     * @return a reference to the drawable tinted
     */
    public Drawable getThemeDrawable(Drawable drawable, int resId) {
        Drawable myDrawable;
        for (final DrawableFilter filter : mDrawableFilters) {
            myDrawable = filter.getDrawable(this, drawable, resId);
            if (myDrawable != null)
                return myDrawable;
        }
        return drawable;
    }

    /**
     * Retrieve a color from the theme.
     *
     * @param resId the unique identifier of the resource.
     *
     * @return an integer with the format ARGB.
     */
    public int getThemeAttrColor(int resId) {
        if (mContext.getTheme().resolveAttribute(resId, mTypedValue, true)) {
            if (mTypedValue.type >= TypedValue.TYPE_FIRST_INT
                    && mTypedValue.type <= TypedValue.TYPE_LAST_INT) {
                return mTypedValue.data;
            } else if (mTypedValue.type == TypedValue.TYPE_STRING) {
                return super.getColor(mTypedValue.resourceId);
            }
        }
        return 0;
    }

    /**
     * Retrieve a color from the theme with alpha applied.
     *
     * @param resId the unique identifier of the resource.
     * @param alpha the alpha to apply to the resource.
     *
     * @return an integer with the format ARGB.
     */
    public int getThemeAttrColor(int resId, float alpha) {
        final int color = getThemeAttrColor(resId);
        final int originalAlpha = Color.alpha(color);
        return (color & 0x00ffffff) | (Math.round(originalAlpha * alpha) << 24);
    }

    /**
     * Retrieve a color from the theme using disabled attr color.
     *
     * @param resId the unique identifier of the resource.
     *
     * @return an integer with the format ARGB.
     */
    public int getDisabledThemeAttrColor(int resId) {
        mContext.getTheme().resolveAttribute(android.R.attr.disabledAlpha, mTypedValue, true);
        return getThemeAttrColor(resId, mTypedValue.getFloat());
    }

    /**
     * Retrieve a drawable with a tint provided.
     *
     * @param drawable the drawable to be tinted by the theme.
     * @param tint     the reference to the tint properties.
     *
     * @return a new reference to the drawable with the tint applied.
     */
    public Drawable getThemeDrawable(Drawable drawable, QuantumTint tint) {
        final int color = getThemeAttrColor(tint.mAttributeColor);
        PorterDuffColorFilter filter = mColorFilterCache.get(color, tint.mAttributeTintMode);
        if (filter == null) {
            mColorFilterCache.put(color, tint.mAttributeTintMode,
                    filter = new PorterDuffColorFilter(color, tint.mAttributeTintMode));
        }
        drawable.setColorFilter(filter);
        if (tint.mAttributeColorAlpha != -1) {
            drawable.setAlpha(tint.mAttributeColorAlpha);
        }
        return drawable;
    }

    /**
     * Retrieve the default {@link android.content.res.ColorStateList} for controls.
     *
     * @return a reference to the default ColorStateList.
     */
    public ColorStateList getDefaultColorStateList() {
        if (mDefaultColorStateList == null) {
            final int colorControlNormal = getThemeAttrColor(R.attr.colorControlNormal);
            final int colorControlActivated = getThemeAttrColor(R.attr.colorControlActivated);

            final int[][] states = new int[7][];
            final int[] colors = new int[7];
            int i = 0;

            // Disabled state
            states[i] = new int[]{-android.R.attr.state_enabled};
            colors[i] = getDisabledThemeAttrColor(R.attr.colorControlNormal);
            i++;

            states[i] = new int[]{android.R.attr.state_focused};
            colors[i] = colorControlActivated;
            i++;

            states[i] = new int[]{android.R.attr.state_activated};
            colors[i] = colorControlActivated;
            i++;

            states[i] = new int[]{android.R.attr.state_pressed};
            colors[i] = colorControlActivated;
            i++;

            states[i] = new int[]{android.R.attr.state_checked};
            colors[i] = colorControlActivated;
            i++;

            states[i] = new int[]{android.R.attr.state_selected};
            colors[i] = colorControlActivated;
            i++;

            // Default enabled state
            states[i] = new int[0];
            colors[i] = colorControlNormal;
            mDefaultColorStateList = new ColorStateList(states, colors);
        }
        return mDefaultColorStateList;
    }

    /**
     * Adds a new drawable filter.
     *
     * @param filter a reference to the filter implementation.
     */
    public void addDrawableFilter(DrawableFilter filter) {
        mDrawableFilters.add(0, filter);
    }

    /**
     * Adds a new drawable filter.
     *
     * @param filter a reference to the filter implementation.
     */
    public void addBitmapDrawableFilter(BitmapFilter filter) {
        mBitmapFilters.add(0, filter);
    }

    /**
     * Adds a new view filter.
     *
     * @param filter a reference to the filter implementation.
     */
    public void addViewFilter(ViewInterceptor filter) {
        mViewFilters.add(0, filter);
    }

    /**
     * Retrieve a view for the given context.
     *
     * @param name    the name of the view.
     * @param context the context on which the resources are.
     * @param attrs   the attributes for the view.
     *
     * @return a reference to the view constructed
     */
    public View getView(String name, Context context, AttributeSet attrs) {
        for (ViewInterceptor filter : mViewFilters) {
            final View view = filter.getView(this, name, context, attrs);
            if (view != null) {
                return view;
            }
        }
        return null;
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
    public static Bitmap applyColor(Bitmap bitmap, int accentColor) {
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