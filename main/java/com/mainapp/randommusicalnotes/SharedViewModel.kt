package com.mainApp.randommusicalnotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {


    var btnclickcount: Int = 0
    var txtSelectedChords: String = "Maj,min"
    private val _tempo = MutableLiveData<String>().apply {
        value = "50"
    }
    var Tempo: MutableLiveData<String> = _tempo

    private val _practiceType_homeScreen = MutableLiveData<Int>().apply {
        value = R.string.lbl_Chords
    }
    var PracticeType_homeScreen: MutableLiveData<Int> = _practiceType_homeScreen


    var ScaleNotes: MutableList<String> = mutableListOf("C","D","E","F","G","A")

    public val model : Model = Model()

    private val _practiceType = MutableLiveData<String>().apply {
        value = model.PracticeType[1]
    }
    var PracticeType: MutableLiveData<String> = _practiceType


    private val _showNoteDegreeChecked = MutableLiveData<Boolean>().apply {
        value = false
    }
    var ShowNoteDegreeChecked: MutableLiveData<Boolean> = _showNoteDegreeChecked


    private val _notesType = MutableLiveData<String>().apply {
        value = model.NotesType[0]
    }
    var NotesType: MutableLiveData<String> = _notesType


    private val _scaleType = MutableLiveData<String>().apply {
        value = model.ScaleType[0]
    }
    var ScaleType: MutableLiveData<String> = _scaleType

    private val _rootNote = MutableLiveData<String>().apply {
        value = model.RootNotes[0]
    }
    var RootNote: MutableLiveData<String> = _rootNote

    private val _chordType = arrayListOf<String>(model.ChordTypes[0],model.ChordTypes[1])
    var ChordType: ArrayList<String> = _chordType

    var selectedchordsboolean = booleanArrayOf(true,true,false,false,false,false,false,false,false)

    var selectedChordsIndexList : ArrayList<Int> = ArrayList()

}