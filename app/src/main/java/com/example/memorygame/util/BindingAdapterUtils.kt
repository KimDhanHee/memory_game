package com.example.memorygame.util

import android.widget.NumberPicker
import androidx.databinding.BindingAdapter

@BindingAdapter("app:max")
fun max(view: NumberPicker, max: Int) { view.maxValue = max }

@BindingAdapter("app:min")
fun min(view: NumberPicker, min: Int) { view.minValue = min }