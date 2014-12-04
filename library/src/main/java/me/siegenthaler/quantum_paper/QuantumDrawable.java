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
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Base wrapper that delegates all calls to another {@link Drawable}. The wrapped {@link Drawable}
 * <em>must</em> be fully released from any {@link android.view.View} before wrapping,
 * otherwise internal {@link Drawable.Callback} may be dropped.
 */
public final class QuantumDrawable extends Drawable implements Drawable.Callback {
    private final Drawable mDrawable;
    private final ColorStateList mTintStateList;
    private final PorterDuff.Mode mTintMode;

    private int mCurrentColor;

    /**
     * Constructor.
     *
     * @param drawable      the reference to the wrapped drawable.
     * @param tintStateList the reference to the color state.
     * @param tintMode      the tint mode of the drawable.
     */
    public QuantumDrawable(Drawable drawable, ColorStateList tintStateList, PorterDuff.Mode tintMode) {
        this.mDrawable = drawable;
        this.mDrawable.setCallback(this);
        this.mTintStateList = tintStateList;
        this.mTintMode = tintMode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas) {
        mDrawable.draw(canvas);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mDrawable.setBounds(left, top, right, bottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChangingConfigurations(int configs) {
        mDrawable.setChangingConfigurations(configs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getChangingConfigurations() {
        return mDrawable.getChangingConfigurations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDither(boolean dither) {
        mDrawable.setDither(dither);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFilterBitmap(boolean filter) {
        mDrawable.setFilterBitmap(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlpha(int alpha) {
        mDrawable.setAlpha(alpha);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColorFilter(ColorFilter cf) {
        mDrawable.setColorFilter(cf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStateful() {
        return (mTintStateList != null && mTintStateList.isStateful()) || mDrawable.isStateful();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setState(final int[] stateSet) {
        boolean handled = mDrawable.setState(stateSet);
        if (mTintStateList != null) {
            handled = updateTint(stateSet);
        }
        return handled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getState() {
        return mDrawable.getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpToCurrentState() {
        mDrawable.jumpToCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getCurrent() {
        return mDrawable.getCurrent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisible(visible, restart) || mDrawable.setVisible(visible, restart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOpacity() {
        return mDrawable.getOpacity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Region getTransparentRegion() {
        return mDrawable.getTransparentRegion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumWidth() {
        return mDrawable.getMinimumWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumHeight() {
        return mDrawable.getMinimumHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getPadding(Rect padding) {
        return mDrawable.getPadding(padding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean onLevelChange(int level) {
        return mDrawable.setLevel(level);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void setAutoMirrored(boolean mirrored) {
        mDrawable.setAutoMirrored(mirrored);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean isAutoMirrored() {
        return mDrawable.isAutoMirrored();
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTint(int tint) {
        mDrawable.setTint(tint);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTintList(ColorStateList tint) {
        mDrawable.setTintList(tint);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        mDrawable.setTintMode(tintMode);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHotspot(float x, float y) {
        mDrawable.setHotspot(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        mDrawable.setHotspotBounds(left, top, right, bottom);
    }

    /**
     * Update the tintint of the {@link Drawable} child.
     *
     * @param state an array that contains the state of the drawable.
     */
    private boolean updateTint(int[] state) {
        final int color = mTintStateList.getColorForState(state, mCurrentColor);
        final boolean isHandled = color != mCurrentColor;
        if (isHandled) {
            if (color != Color.TRANSPARENT) {
                setColorFilter(color, mTintMode);
            } else {
                clearColorFilter();
            }
            mCurrentColor = color;
        }
        return isHandled;
    }
}
