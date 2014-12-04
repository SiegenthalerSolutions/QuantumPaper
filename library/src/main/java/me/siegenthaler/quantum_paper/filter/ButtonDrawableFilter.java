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

import android.graphics.drawable.Drawable;

import me.siegenthaler.quantum_paper.QuantumResources;
import me.siegenthaler.quantum_paper.QuantumTint;
import me.siegenthaler.quantum_paper.R;

/**
 * Default {@link me.siegenthaler.quantum_paper.QuantumResources.DrawableFilter}
 * for {@link android.widget.Button}
 * and {@link android.widget.AutoCompleteTextView}.
 */
public class ButtonDrawableFilter implements QuantumResources.DrawableFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getDrawable(QuantumResources resources, Drawable drawable, int resId) {
        if (resId == R.drawable.abc_btn_default_material ||
                resId == R.drawable.abc_btn_borderless_material) {
            return resources.getThemeDrawable(drawable, new QuantumTint(R.attr.colorControlHighlight));
        } else if (resId == R.drawable.abc_btn_default_mtrl_shape) {
            return resources.getThemeDrawable(drawable, new QuantumTint(R.attr.colorControlNormal));
        }
        return null;
    }
}
