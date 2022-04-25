package com.mainApp.randommusicalnotes.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mainApp.randommusicalnotes.Model

class DashboardViewModel : ViewModel() {


    public val model: Model = Model()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}