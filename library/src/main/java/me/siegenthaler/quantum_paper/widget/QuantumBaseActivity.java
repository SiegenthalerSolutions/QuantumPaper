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
package me.siegenthaler.quantum_paper.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import me.siegenthaler.quantum_paper.QuantumResources;
import me.siegenthaler.quantum_paper.theme.material.ThemeMaterial;

/**
 * Base {@link Activity} for the Quantum Paper framework.
 */
public class QuantumBaseActivity extends Activity {
    private QuantumResources mResources = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Resources getResources() {
        if (mResources == null) {
            mResources = new QuantumResources(this, super.getResources());

            ThemeMaterial.addDefaultFilters(mResources);
            ThemeMaterial.addDefaultResources(mResources);
            ThemeMaterial.addDefaultInterceptors(mResources);
        }
        return mResources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = mResources.getWidget(name, context, attrs);
        if (view == null) {
            view = super.onCreateView(name, context, attrs);
        }
        return view;
    }
}
