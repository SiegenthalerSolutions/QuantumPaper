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
package me.siegenthaler.quantum_paper.interceptor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import me.siegenthaler.quantum_paper.QuantumResources;

/**
 * Default {@link me.siegenthaler.quantum_paper.QuantumResources.ViewInterceptor}
 * for {@link android.widget.Button}.
 */
public class ButtonInterceptor implements QuantumResources.ViewInterceptor {
    /**
     * Default attributes for tinting.
     */
    private static final int[] TINT_ATTRS = {
            android.R.attr.background
    };

    /**
     * Default style of the filter.
     */
    private static final int DEFAULT_BUTTON_STYLE = android.R.attr.buttonStyle;

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(QuantumResources resources, String name, Context context, AttributeSet attributes) {
        switch (name.toUpperCase()) {
            case "BUTTON":
                return getButton(resources, context, attributes);

        }
        return null;
    }

    /**
     * (non-doc)
     */
    private View getButton(QuantumResources resources, Context context, AttributeSet attributes) {
        final Button view
                = new Button(context, attributes, DEFAULT_BUTTON_STYLE);
        return setViewBackground(resources, context, attributes, view, DEFAULT_BUTTON_STYLE);
    }

    /**
     * (non-doc)
     */
    private View setViewBackground(QuantumResources resources, Context context, AttributeSet attributes,
                                   View view, int defStyle) {
        final TypedArray array
                = context.obtainStyledAttributes(attributes, TINT_ATTRS, defStyle, 0);
        try {
            view.setBackground(
                    resources.getDrawable(array.getResourceId(0, 0)));
        } catch (Exception ignored) {
        } finally {
            array.recycle();
        }
        return view;
    }
}
