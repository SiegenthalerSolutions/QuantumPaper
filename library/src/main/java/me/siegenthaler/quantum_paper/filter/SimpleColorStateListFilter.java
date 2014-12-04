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
package me.siegenthaler.quantum_paper.filter;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import me.siegenthaler.quantum_paper.drawable.FilteredColorStateDrawable;
import me.siegenthaler.quantum_paper.QuantumResources;

/**
 * Encapsulate a simple {@link me.siegenthaler.quantum_paper.QuantumResources.Filter} that tints
 * any drawable and bitmap using a {@link android.content.res.ColorStateList}.
 */
public class SimpleColorStateListFilter implements QuantumResources.Filter {
    private final ColorStateList mStateList;

    /**
     * Constructor for the filter using default {@link android.content.res.ColorStateList}.
     *
     * @param stateList the state list to apply.
     */
    public SimpleColorStateListFilter(ColorStateList stateList) {
        this.mStateList = stateList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getDrawable(QuantumResources manager, Drawable drawable) {
        return new FilteredColorStateDrawable(drawable, mStateList, PorterDuff.Mode.SRC_IN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bitmap getBitmap(QuantumResources manager, Bitmap bitmap) {
        return null;    // Cannot apply to bitmap.
    }
}
