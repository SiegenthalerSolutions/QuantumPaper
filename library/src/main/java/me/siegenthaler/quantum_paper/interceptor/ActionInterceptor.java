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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import me.siegenthaler.quantum_paper.QuantumResources;

/**
 * Default {@link me.siegenthaler.quantum_paper.QuantumResources.ViewInterceptor}
 * for com.android.internal.widget.ActionBarContextView.
 */
public class ActionInterceptor implements QuantumResources.ViewInterceptor {
    /**
     * Default attributes for tinting.
     */
    private static final int[] TINT_ATTRS = {
            android.R.attr.background
    };

    /**
     * Default style of the filter.
     */
    private static final int DEFAULT_ACTION_MODE_STYLE = android.R.attr.actionModeStyle;

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(QuantumResources resources, String name, Context context, AttributeSet attributes) {
        switch (name.toUpperCase()) {
            case "COM.ANDROID.INTERNAL.WIDGET.ACTIONBARCONTEXTVIEW":
                return getContextView(resources, context, attributes);

        }
        return null;
    }

    /**
     * (non-doc)
     */
    private View getContextView(QuantumResources resources, Context context, AttributeSet attributes) {
        View instance = null;
        try {
            instance = (View) Class.forName("com.android.internal.widget.ActionBarContextView")
                    .getConstructor(Context.class, AttributeSet.class, int.class)
                    .newInstance(context, attributes, DEFAULT_ACTION_MODE_STYLE);
            final TypedArray array
                    = context.obtainStyledAttributes(attributes, TINT_ATTRS, DEFAULT_ACTION_MODE_STYLE, 0);
            instance.setBackground(resources.getDrawable(array.getResourceId(0, 0)));
            array.recycle();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return instance;
    }
}
