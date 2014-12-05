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

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import me.siegenthaler.quantum_paper.QuantumResources;

/**
 * Encapsulate a simple {@link me.siegenthaler.quantum_paper.QuantumResources.Filter} that tints
 * any drawable and bitmap using a single attribute.
 */
public class SimpleColorFilter implements QuantumResources.Filter {
    private final int mAttribute;
    private final int mAlpha;
    private final PorterDuff.Mode mMode;

    /**
     * Constructor for the filter using default {@link android.graphics.PorterDuff.Mode#SRC_IN}.
     *
     * @param attribute an attribute that the filter will use for tinting.
     */
    public SimpleColorFilter(int attribute) {
        this(attribute, -1, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Constructor for the filter.
     *
     * @param attribute an attribute that the filter will use for tinting.
     * @param alpha     the alpha value of the tintint, -1 means no.
     * @param mode      the mode of the tintint.
     */
    public SimpleColorFilter(int attribute, int alpha, PorterDuff.Mode mode) {
        this.mAttribute = attribute;
        this.mAlpha = alpha;
        this.mMode = mode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getDrawable(QuantumResources manager, Drawable drawable, int resId) {
        return manager.applyColor(drawable, mAttribute, mAlpha, mMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bitmap getBitmap(QuantumResources manager, Bitmap bitmap, int resId) {
        return manager.applyColor(bitmap, manager.getThemeAttrColor(mAttribute));
    }
}
