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

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;

import me.siegenthaler.quantum_paper.QuantumResources;
import me.siegenthaler.quantum_paper.QuantumTint;
import me.siegenthaler.quantum_paper.R;

/**
 * Default {@link me.siegenthaler.quantum_paper.QuantumResources.DrawableFilter}
 * for {@link android.app.ActionBar}
 */
public class ActionDrawableFilter implements QuantumResources.DrawableFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    public Drawable getDrawable(QuantumResources resources, Drawable drawable, int resId) {
        if (resId == R.drawable.abc_ic_ab_back_mtrl_am_alpha ||
                resId == R.drawable.abc_ic_clear_mtrl_alpha ||
                resId == R.drawable.abc_ic_commit_search_api_mtrl_alpha ||
                resId == R.drawable.abc_ic_go_search_api_mtrl_alpha ||
                resId == R.drawable.abc_ic_menu_copy_mtrl_am_alpha ||
                resId == R.drawable.abc_ic_menu_cut_mtrl_alpha ||
                resId == R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha ||
                resId == R.drawable.abc_ic_menu_paste_mtrl_am_alpha ||
                resId == R.drawable.abc_ic_menu_selectall_mtrl_alpha ||
                resId == R.drawable.abc_ic_menu_share_mtrl_alpha ||
                resId == R.drawable.abc_ic_search_api_mtrl_alpha ||
                resId == R.drawable.abc_ic_voice_search_api_mtrl_alpha) {
            return resources.getThemeDrawable(drawable,
                    new QuantumTint(R.attr.colorControlNormal));
        } else if (resId == R.drawable.abc_cab_background_top_mtrl_alpha ||
                resId == R.drawable.abc_cab_background_bottom_mtrl_alpha) {
            Log.d("UTone", "BOTTOM_TOP");
            return resources.getThemeDrawable(drawable,
                    new QuantumTint(R.attr.colorControlActivated));
        } else if (resId == R.drawable.abc_cab_background_internal_bg) {
            Log.d("UTone", "INTERNAL_BACKGROUND");
            return resources.getThemeDrawable(drawable,
                    new QuantumTint(android.R.attr.colorBackground, -1, PorterDuff.Mode.MULTIPLY));
        } else if (resId == R.drawable.abc_cab_background_top_material ||
                resId == R.drawable.abc_cab_background_bottom_material) {
            Log.d("UTone", "Returning the same");
            return drawable;
        }
        return null;
    }
}
