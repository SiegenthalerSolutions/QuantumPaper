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
package me.siegenthaler.quantum_paper.theme.material;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import me.siegenthaler.quantum_paper.QuantumResources;
import me.siegenthaler.quantum_paper.R;
import me.siegenthaler.quantum_paper.filter.SimpleColorFilter;
import me.siegenthaler.quantum_paper.filter.SimpleColorStateListFilter;
import me.siegenthaler.quantum_paper.interceptor.SimpleBackgroundInterceptor;

/**
 * Helper methods for the Material theme.
 */
public final class ThemeMaterial {
    /**
     * A collection of default filters.
     */
    public final static String FILTER_COLOR_CONTROL_NORMAL = "ColorControlNormal";
    public final static String FILTER_COLOR_CONTROL_ACTIVATED = "ColorControlActivated";
    public final static String FILTER_COLOR_CONTROL_HIGHLIGHT = "ColorControlHighlight";
    public final static String FILTER_COLOR_BUTTON_NORMAL = "ColorButtonNormal";
    public final static String FILTER_COLOR_BACKGROUND = "ColorBackground";
    public final static String FILTER_DEFAULT_COLOR_STATE_LIST = "DefaultColorStateList";
    public final static String FILTER_ACTION_BAR_BACKGROUND = "ActionBarBackground";

    /**
     * Add default {@link me.siegenthaler.quantum_paper.QuantumResources.Filter}.
     *
     * @param manager a reference to the manager
     */
    public static void addDefaultFilters(QuantumResources manager) {
        manager.addFilter(FILTER_COLOR_CONTROL_NORMAL,
                new SimpleColorFilter(R.attr.colorControlNormal));
        manager.addFilter(FILTER_COLOR_CONTROL_ACTIVATED,
                new SimpleColorFilter(R.attr.colorControlActivated));
        manager.addFilter(FILTER_COLOR_CONTROL_HIGHLIGHT,
                new SimpleColorFilter(R.attr.colorControlHighlight));
        manager.addFilter(FILTER_COLOR_BACKGROUND,
                new SimpleColorFilter(android.R.attr.colorBackground, -1, PorterDuff.Mode.MULTIPLY));
        manager.addFilter(FILTER_DEFAULT_COLOR_STATE_LIST,
                new SimpleColorStateListFilter(getDefaultColorStateList(manager)));
    }

    /**
     * Add default {@link me.siegenthaler.quantum_paper.R.drawable}.
     *
     * @param manager a reference to the manager
     */
    public static void addDefaultResources(QuantumResources manager) {
        manager.addResources(FILTER_COLOR_CONTROL_NORMAL,
                R.drawable.abc_btn_default_mtrl_shape,
                R.drawable.abc_textfield_search_default_mtrl_alpha,
                R.drawable.abc_textfield_default_mtrl_alpha,
                R.drawable.abc_ic_ab_back_mtrl_am_alpha,
                R.drawable.abc_ic_clear_mtrl_alpha,
                R.drawable.abc_ic_commit_search_api_mtrl_alpha,
                R.drawable.abc_ic_go_search_api_mtrl_alpha,
                R.drawable.abc_ic_menu_copy_mtrl_am_alpha,
                R.drawable.abc_ic_menu_cut_mtrl_alpha,
                R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha,
                R.drawable.abc_ic_menu_paste_mtrl_am_alpha,
                R.drawable.abc_ic_menu_selectall_mtrl_alpha,
                R.drawable.abc_ic_menu_share_mtrl_alpha,
                R.drawable.abc_ic_search_api_mtrl_alpha,
                R.drawable.abc_ic_voice_search_api_mtrl_alpha);
        manager.addResources(FILTER_COLOR_CONTROL_ACTIVATED,
                R.drawable.abc_textfield_search_activated_mtrl_alpha,
                R.drawable.abc_textfield_activated_mtrl_alpha,
                R.drawable.abc_text_select_handle_left_mtrl_alpha,
                R.drawable.abc_text_select_handle_right_mtrl_alpha,
                R.drawable.abc_text_select_handle_middle_mtrl_alpha,
                R.drawable.abc_text_cursor_mtrl_alpha);
        manager.addResources(FILTER_COLOR_CONTROL_HIGHLIGHT,
                R.drawable.abc_btn_default_material,
                R.drawable.abc_btn_borderless_material);
        manager.addResources(FILTER_COLOR_BACKGROUND,
                R.drawable.abc_cab_background_internal_bg);
        manager.addResources(FILTER_DEFAULT_COLOR_STATE_LIST,
                R.drawable.abc_edit_text_material,
                R.drawable.abc_textfield_search_material);
    }

    /**
     * Add default {@link me.siegenthaler.quantum_paper.QuantumResources.Interceptor}.
     *
     * @param manager a reference to the manager
     */
    public static void addDefaultInterceptors(QuantumResources manager) {
        manager.addInterceptor("EditText", new SimpleBackgroundInterceptor(
                android.R.attr.editTextStyle) {
            @Override
            protected View instance(Context context, AttributeSet attributes, int defStyle) {
                return new EditText(context, attributes, defStyle);
            }
        });
        manager.addInterceptor("AutoCompleteTextView", new SimpleBackgroundInterceptor(
                android.R.attr.autoCompleteTextViewStyle) {
            @Override
            protected View instance(Context context, AttributeSet attributes, int defStyle) {
                return new AutoCompleteTextView(context, attributes, defStyle);
            }
        });
        manager.addInterceptor("Button", new SimpleBackgroundInterceptor(
                android.R.attr.buttonStyle) {
            @Override
            protected View instance(Context context, AttributeSet attributes, int defStyle) {
                return new Button(context, attributes, defStyle);
            }
        });
    }

    /**
     * Retrieve the default {@link android.content.res.ColorStateList} for controls.
     *
     * @param manager a reference to the manager for retrieving the attributes.
     *
     * @return a reference to the default ColorStateList.
     */
    private static ColorStateList getDefaultColorStateList(QuantumResources manager) {
        final int colorControlNormal = manager.getThemeAttrColor(R.attr.colorControlNormal);
        final int colorControlActivated = manager.getThemeAttrColor(R.attr.colorControlActivated);

        final int[][] states = new int[7][];
        final int[] colors = new int[7];
        int i = 0;

        // Disabled state
        states[i] = new int[]{-android.R.attr.state_enabled};
        colors[i] = manager.getThemeAttrColor(R.attr.colorControlNormal, android.R.attr.disabledAlpha);
        i++;

        states[i] = new int[]{android.R.attr.state_focused};
        colors[i] = colorControlActivated;
        i++;

        states[i] = new int[]{android.R.attr.state_activated};
        colors[i] = colorControlActivated;
        i++;

        states[i] = new int[]{android.R.attr.state_pressed};
        colors[i] = colorControlActivated;
        i++;

        states[i] = new int[]{android.R.attr.state_checked};
        colors[i] = colorControlActivated;
        i++;

        states[i] = new int[]{android.R.attr.state_selected};
        colors[i] = colorControlActivated;
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = colorControlNormal;
        return new ColorStateList(states, colors);
    }
}
