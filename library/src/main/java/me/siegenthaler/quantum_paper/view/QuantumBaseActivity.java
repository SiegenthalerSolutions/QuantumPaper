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
package me.siegenthaler.quantum_paper.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import me.siegenthaler.quantum_paper.QuantumResources;

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
        }
        return mResources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        final View view = mResources.getView(name, context, attrs);
        if (view != null) {
            return view;
        }
        return super.onCreateView(name, context, attrs);
    }
}
