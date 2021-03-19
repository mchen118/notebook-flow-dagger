package com.muen.notebook.adatper

import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun setText(view: EditText, note: String?) {
    Log.d("BindingAdapter.setText", "called")
    if (note == null) {
        Log.d("BindingAdapter.setText", "note is null")
        view.setText("")
        return
    }
    view.setText(note)
}