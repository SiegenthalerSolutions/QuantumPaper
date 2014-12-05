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
import android.widget.ImageView;

import me.siegenthaler.quantum_paper.QuantumResources;

/**
 * Simple {@link me.siegenthaler.quantum_paper.QuantumResources.Interceptor} for any widget
 * that only requires to tint the src attribute.
 */
public abstract class SimpleImageInterceptor implements QuantumResources.Interceptor {
    /**
     * Default attributes for tinting.
     */
    private static final int[] TINT_ATTRS = {
            android.R.attr.src
    };

    private final int mDefaultStyle;

    /**
     * Default constructor for the interceptor.
     *
     * @param style a number that represent the default style of the widget.
     */
    public SimpleImageInterceptor(int style) {
        this.mDefaultStyle = style;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getWidget(QuantumResources manager, Context context, AttributeSet attributes) {
        final ImageView instance = instance(context, attributes, mDefaultStyle);
        final TypedArray array = context.obtainStyledAttributes(attributes, TINT_ATTRS, mDefaultStyle, 0);
        try {
            instance.setImageResource(array.getResourceId(0, 0));
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            array.recycle();
        }
        return instance;
    }

    /**
     * Creates a new instance of the given {@link android.view.View}.
     *
     * @param context    the context on which the view executes.
     * @param attributes the attributes for the view.
     * @param defStyle   the style of the widget.
     *
     * @return a reference to the view created.
     */
    protected abstract ImageView instance(Context context, AttributeSet attributes, int defStyle);
}
