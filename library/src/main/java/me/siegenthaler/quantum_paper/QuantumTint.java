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

import android.graphics.PorterDuff;

/**
 * Define a structure for tinting anything.
 */
public final class QuantumTint {
    /**
     * Default tinting mode for Drawables.
     */
    public final static PorterDuff.Mode DEFAULT = PorterDuff.Mode.SRC_IN;

    protected final int mAttributeColor;
    protected final int mAttributeColorAlpha;
    protected final PorterDuff.Mode mAttributeTintMode;

    /**
     * Default constructor.
     *
     * @param color the color of the drawable.
     */
    public QuantumTint(int color) {
        this(color, -1, DEFAULT);
    }

    /**
     * Default constructor with alpha.
     *
     * @param color      the color of the drawable.
     * @param colorAlpha the alpha color of the drawable.
     */
    public QuantumTint(int color, int colorAlpha) {
        this(color, colorAlpha, DEFAULT);
    }

    /**
     * Full constructor for {@link QuantumTint}.
     *
     * @param color      the color of the drawable.
     * @param colorAlpha the alpha color of the drawable.
     * @param tintMode   the mode of the tinting.
     */
    public QuantumTint(int color, int colorAlpha, PorterDuff.Mode tintMode) {
        this.mAttributeColor = color;
        this.mAttributeColorAlpha = colorAlpha;
        this.mAttributeTintMode = tintMode;
    }
}
