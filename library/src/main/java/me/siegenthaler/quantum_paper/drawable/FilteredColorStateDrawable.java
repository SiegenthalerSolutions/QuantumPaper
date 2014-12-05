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
package me.siegenthaler.quantum_paper.drawable;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Define a {@link me.siegenthaler.quantum_paper.drawable.FilteredDrawable} that contains
 * a color state list.
 */
public class FilteredColorStateDrawable extends FilteredDrawable {
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
    public FilteredColorStateDrawable(Drawable drawable, ColorStateList tintStateList, PorterDuff.Mode tintMode) {
        super(drawable);
        this.mTintStateList = tintStateList;
        this.mTintMode = tintMode;
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
     * Update the tintint of the {@link android.graphics.drawable.Drawable} child.
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
