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
import android.util.TypedValue;

import me.siegenthaler.quantum_paper.QuantumResources;
import me.siegenthaler.quantum_paper.R;

/**
 * Default {@link me.siegenthaler.quantum_paper.QuantumResources.BitmapFilter}
 * for {@link android.widget.EditText}
 * and {@link android.widget.AutoCompleteTextView} handlers.
 */
public class TextFieldHandleDrawableFilter implements QuantumResources.BitmapFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    public Bitmap getDrawable(QuantumResources resources, Bitmap bitmap, TypedValue value) {
        if (value.resourceId == R.drawable.abc_text_select_handle_left_mtrl_alpha ||
                value.resourceId == R.drawable.abc_text_select_handle_right_mtrl_alpha ||
                value.resourceId == R.drawable.abc_text_select_handle_middle_mtrl_alpha ||
                value.resourceId == R.drawable.abc_text_cursor_mtrl_alpha) {
            return QuantumResources.applyColor(bitmap, resources.getThemeAttrColor(R.attr.colorControlActivated));
        }
        return null;
    }
}
