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
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import me.siegenthaler.quantum_paper.QuantumResources;
import me.siegenthaler.quantum_paper.R;
import me.siegenthaler.quantum_paper.filter.SimpleColorFilter;
import me.siegenthaler.quantum_paper.filter.SimpleColorStateListFilter;
import me.siegenthaler.quantum_paper.interceptor.SimpleBackgroundInterceptor;
import me.siegenthaler.quantum_paper.interceptor.SimpleImageInterceptor;

/**
 * Helper methods for the Material theme.
 */
public final class ThemeMaterial {
    /**
     * A collection of default filters.
     */
    public final static String FILTER_COLOR_CONTROL_NORMAL = "NORMAL";
    public final static String FILTER_COLOR_CONTROL_ACTIVATED = "ACTIVATED";
    public final static String FILTER_COLOR_CONTROL_HIGHLIGHT = "HIGHLIGHT";
    public final static String FILTER_COLOR_FOREGROUND = "FOREGROUND";
    public final static String FILTER_COLOR_BUTTON_NORMAL = "BUTTON_NORMAL";
    public final static String FILTER_COLOR_BACKGROUND = "BACKGROUD";
    public final static String FILTER_DEFAULT_COLOR_STATE_LIST = "COLOR_STATE_LIST";
    public final static String FILTER_DEFAULT_CONTROL_ACTIVATED_STATE_LIST = "CONTROL_ACTIVATED_STATE_LIST";
    public final static String FILTER_DEFAULT_ACTIVATED_STATE_LIST = "ACTIVATED_STATE_LIST";
    private final static String FILTER_ACTION_BAR = "ACTION_BAR";

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
        manager.addFilter(FILTER_COLOR_FOREGROUND,
                new SimpleColorFilter(android.R.attr.colorForeground));
        manager.addFilter(FILTER_COLOR_BACKGROUND,
                new SimpleColorFilter(android.R.attr.colorBackground, -1, PorterDuff.Mode.MULTIPLY));
        manager.addFilter(FILTER_DEFAULT_COLOR_STATE_LIST,
                new SimpleColorStateListFilter(getDefaultColorStateList(manager)));
        manager.addFilter(FILTER_DEFAULT_CONTROL_ACTIVATED_STATE_LIST,
                new SimpleColorStateListFilter(getActivatedButtonStateList(manager)));
        manager.addFilter(FILTER_DEFAULT_ACTIVATED_STATE_LIST,
                new SimpleColorStateListFilter(getActivatedStateList(manager)));
        manager.addFilter(FILTER_ACTION_BAR, new ActionBarCustomFilter());
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
                R.drawable.abc_ic_voice_search_api_mtrl_alpha,
                R.drawable.abc_scrollbar_handle_mtrl_alpha,
                R.drawable.abc_fastscroll_track_mtrl_alpha,
                R.drawable.abc_fastscroll_track_material);
        manager.addResources(FILTER_COLOR_CONTROL_ACTIVATED,
                R.drawable.abc_textfield_search_activated_mtrl_alpha,
                R.drawable.abc_textfield_activated_mtrl_alpha,
                R.drawable.abc_text_select_handle_left_mtrl_alpha,
                R.drawable.abc_text_select_handle_right_mtrl_alpha,
                R.drawable.abc_text_select_handle_middle_mtrl_alpha,
                R.drawable.abc_text_cursor_mtrl_alpha,
                R.drawable.abc_cab_background_top_mtrl_alpha,
                R.drawable.abc_cab_background_bottom_mtrl_alpha,
                R.drawable.abc_fastscroll_label_left_material,
                R.drawable.abc_fastscroll_label_right_material);
        manager.addResources(FILTER_COLOR_CONTROL_HIGHLIGHT,
                R.drawable.abc_btn_default_material,
                R.drawable.abc_btn_borderless_material);
        manager.addResources(FILTER_COLOR_FOREGROUND,
                R.drawable.abc_list_divider_mtrl_alpha);
        manager.addResources(FILTER_COLOR_BACKGROUND,
                R.drawable.abc_cab_background_internal_bg,
                R.drawable.abc_dialog_background_material);
        manager.addResources(FILTER_DEFAULT_CONTROL_ACTIVATED_STATE_LIST,
                R.drawable.abc_fastscroll_thumb_material);
        manager.addResources(FILTER_DEFAULT_COLOR_STATE_LIST,
                R.drawable.abc_edit_text_material,
                R.drawable.abc_textfield_search_material);
        manager.addResources(FILTER_DEFAULT_ACTIVATED_STATE_LIST,
                R.drawable.abc_activated_background_material);
        manager.addResources(FILTER_ACTION_BAR,
                R.drawable.abc_cab_background_top_material,
                R.drawable.abc_cab_background_bottom_material);
    }

    /**
     * Add default {@link me.siegenthaler.quantum_paper.QuantumResources.Interceptor}.
     *
     * @param manager a reference to the manager
     */
    public static void addDefaultInterceptors(QuantumResources manager) {

        manager.addInterceptor("ImageView", new SimpleImageInterceptor(0) {
            @Override
            protected ImageView instance(Context context, AttributeSet attributes, int defStyle) {
                return new ImageView(context, attributes, defStyle);
            }
        });
        manager.addInterceptor("TextView", new SimpleBackgroundInterceptor(
                android.R.attr.textViewStyle) {
            @Override
            protected View instance(Context context, AttributeSet attributes, int defStyle) {
                return new TextView(context, attributes, defStyle);
            }
        });
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
        manager.addInterceptor("com.android.internal.widget.ActionBarContextView", new SimpleBackgroundInterceptor(
                android.R.attr.actionModeStyle) {
            @Override
            protected View instance(Context context, AttributeSet attributes, int defStyle) {
                try {
                    return (View) Class.forName("com.android.internal.widget.ActionBarContextView")
                            .getConstructor(Context.class, AttributeSet.class, int.class)
                            .newInstance(context, attributes, defStyle);
                } catch (Exception ignored) {
                }
                return null;
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

    /**
     * Retrieve the default {@link android.content.res.ColorStateList} for controls.
     *
     * @param manager a reference to the manager for retrieving the attributes.
     *
     * @return a reference to the default ColorStateList.
     */
    private static ColorStateList getActivatedStateList(QuantumResources manager) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;


        states[i] = new int[]{android.R.attr.state_activated};
        colors[i] = manager.getThemeAttrColor(R.attr.colorControlActivated);
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = manager.getColor(android.R.color.transparent);
        return new ColorStateList(states, colors);
    }

    /**
     * Retrieve the default {@link android.content.res.ColorStateList} for controls.
     *
     * @param manager a reference to the manager for retrieving the attributes.
     *
     * @return a reference to the default ColorStateList.
     */
    private static ColorStateList getActivatedButtonStateList(QuantumResources manager) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;


        states[i] = new int[]{android.R.attr.state_activated};
        colors[i] = manager.getThemeAttrColor(R.attr.colorControlActivated);
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = manager.getThemeAttrColor(R.attr.colorControlNormal);
        return new ColorStateList(states, colors);
    }

    /**
     * Define a custom filter for the ActionBar (TODO: Remove this and fix Drawable mutation).
     */
    private static class ActionBarCustomFilter implements QuantumResources.Filter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Drawable getDrawable(QuantumResources manager, Drawable drawable, int resId) {
            final LayerDrawable layer = (LayerDrawable) drawable;

            if (resId == R.drawable.abc_cab_background_top_material) {
                layer.invalidateDrawable(manager.getDrawableFromTheme(
                        layer.getDrawable(0), R.drawable.abc_cab_background_internal_bg));
                layer.invalidateDrawable(manager.getDrawableFromTheme(
                        layer.getDrawable(1), R.drawable.abc_cab_background_top_mtrl_alpha));
            } else {
                layer.invalidateDrawable(manager.getDrawableFromTheme(
                        layer.getDrawable(1), R.drawable.abc_cab_background_internal_bg));
                layer.invalidateDrawable(manager.getDrawableFromTheme(
                        layer.getDrawable(0), R.drawable.abc_cab_background_bottom_mtrl_alpha));
            }
            return drawable;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Bitmap getBitmap(QuantumResources manager, Bitmap bitmap, int resId) {
            return null;
        }
    }
}
