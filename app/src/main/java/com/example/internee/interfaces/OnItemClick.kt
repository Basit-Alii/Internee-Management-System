package com.example.internee.interfaces

import android.view.View

interface OnItemClick {
    fun update(view: View, position: Int)
    fun delete(view: View, position: Int)
}