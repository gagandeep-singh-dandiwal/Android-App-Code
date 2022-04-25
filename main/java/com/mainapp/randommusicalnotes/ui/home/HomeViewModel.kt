package com.mainApp.randommusicalnotes.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.net.Uri
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.mainApp.randommusicalnotes.Model
import com.mainApp.randommusicalnotes.R
import com.mainApp.randommusicalnotes.SharedViewModel

class HomeViewModel : ViewModel() {


    var sharedViewModel: SharedViewModel = SharedViewModel()
    var prevNote : String = ""
    var falseScaleNotes : MutableList<String> = mutableListOf()
    var falseRootNote : String = ""

    private val _sharpOrflatText = MutableLiveData<String>().apply {
        value = ""
    }
    var sharpOrflatText: MutableLiveData<String> = _sharpOrflatText

    private val _chordType = MutableLiveData<String>().apply {
        value = ""
    }
    var ChordType: MutableLiveData<String> = _chordType

    lateinit var SelectedScaleType: String
    var flag  = false

    private val _txtNote = MutableLiveData<String>().apply {
        value = "C"
    }
    var TextNote: MutableLiveData<String> = _txtNote


//    private val _textTempo = MutableLiveData<String>().apply {
//        value = "120"
//    }
//    var TextTempo: MutableLiveData<String> = _textTempo


    var btnStartStopText : MutableLiveData<Int> = MutableLiveData(R.string.btn_Start)
    var btnclickcount : Int = 0


    private val _radioButton1 = MutableLiveData<Boolean>().apply {
        value = false
    }
    var radioButton1: MutableLiveData<Boolean> = _radioButton1
        set(value) {
            field = value
        }

    private val _radioButton2 = MutableLiveData<Boolean>().apply {
        value = false
    }
    var radioButton2: MutableLiveData<Boolean> = _radioButton2

    var model : Model = Model()
    //var notelooperObject : NoteLooperthread = NoteLooperthread(this, model)
    var metronomeLooperObject : MetronomeThread = MetronomeThread(this, model)


    var tempo : Long = 60000/ sharedViewModel.Tempo.value!!.toLong()
    lateinit var context : Context
    lateinit var mediaPlayer1 : MediaPlayer
    lateinit var mediaPlayer2 : MediaPlayer

    var tempScale : MutableList<String> = mutableListOf()
    val totalNotes = 12
    lateinit var rootNote : String
    var chordsToDisplayAccordingToSelected : List<MutableList<String>> = listOf(mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf())


//    init {
//
//        if (sharedViewModel.NotesType.value == model.NotesType[0]){
//            tempScale = model.NaturalNotes as MutableList<String>
//        }
//        else if(sharedViewModel.NotesType.value == model.NotesType[1]){
//            tempScale = model.AllNotes as MutableList<String>
//        }
//        else if(sharedViewModel.NotesType.value == model.NotesType[2])
//        {
//            rootNote = sharedViewModel.RootNote.value.toString()
//            tempScale.add(rootNote)
//            var indexOfRootNote = model.RootNotes.indexOf(rootNote)
//            var tempIndex = indexOfRootNote
//            for (i in 1..6){
//                if (i!=3)
//                {
//                    if (tempIndex + 2 > 12){
//                        tempIndex = (tempIndex + 2) - 13
//                        tempScale.add(model.RootNotes[tempIndex])
//                    }
//                    else
//                        tempScale.add(model.RootNotes[tempIndex])
//                }
//                else{
//                    if (tempIndex + 1 > 12){
//                        tempIndex = (tempIndex + 1) - 13
//                        tempScale.add(model.RootNotes[tempIndex])
//                    }
//                    else
//                        tempScale.add(model.RootNotes[tempIndex])
//                }
//            }
//        }
//        sharedViewModel.ScaleNotes = tempScale
//    }

//    init {
//        sharedViewModel.Tempo = TextTempo
//    }
    fun onStartClick(context: Context) {
        this.context = context
        tempo = 60000/ sharedViewModel.Tempo.value!!.toLong()
        mediaPlayer1 = MediaPlayer.create( context,R.raw.one)
        mediaPlayer1.setVolume(1.0F, 1.0F)
        mediaPlayer2 = MediaPlayer.create( context,R.raw.rest )
        mediaPlayer2.setVolume(1.0F, 1.0F)
//        mediaPlayer1 = MediaPlayer()
//        mediaPlayer2 = MediaPlayer()
//        var oneUri: Uri  = Uri.parse("android.resource://" + this.context.packageName + "/" + R.raw.one)
//        var twoUri: Uri  = Uri.parse("android.resource://" + this.context.packageName + "/" + R.raw.rest)
//        mediaPlayer1.setDataSource(context,oneUri)
//        mediaPlayer1.prepareAsync()
//        mediaPlayer2.setDataSource(context,twoUri)
//        mediaPlayer2.prepareAsync()

        if (btnStartStopText.value == R.string.btn_Stop && sharedViewModel.btnclickcount == 0) {
            if (sharedViewModel.PracticeType.value == model.PracticeType[1] && sharedViewModel.ChordType.count() != 0){

                //preparing array for chords display
                for (item in sharedViewModel.ChordType)
                {
                    //"Maj","min" ,"dim" ,"Maj7","min7","dom7","sus2","sus4"
                    if (item == model.ChordTypes[0]){
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[3].add(item)
                        chordsToDisplayAccordingToSelected[4].add(item)
                    }
                    else if( item == model.ChordTypes[1]) {
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[2].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                    else if(item == model.ChordTypes[2]) {
                        chordsToDisplayAccordingToSelected[6].add(item)
                    }
                    else if(item == model.ChordTypes[3]) {
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[3].add(item)
                    }
                    else if(item == model.ChordTypes[4]) {
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[2].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                    else if(item == model.ChordTypes[5]) {
                        chordsToDisplayAccordingToSelected[4].add(item)
                    }
                    else if(item == model.ChordTypes[6]) {
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[3].add(item)
                        chordsToDisplayAccordingToSelected[4].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                    else if(item == model.ChordTypes[7]) {
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[2].add(item)
                        chordsToDisplayAccordingToSelected[4].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                }

                //chosing false root depending on scale selection
                if(sharedViewModel.ScaleType.value == model.ScaleType[0]){
                    falseRootNote = sharedViewModel.RootNote.toString()
                    falseScaleNotes = sharedViewModel.ScaleNotes
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[1]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 2 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 2]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 2]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[2]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 4 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 4]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 4]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[3]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 5 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 5]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 5]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[4]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 7 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 7]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 7]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[5]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 9 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 9]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 9]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[6]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 11 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 11]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 11]
                    }
                }
                if(sharedViewModel.ScaleType.value != model.ScaleType[0]) {
                    tempScale = mutableListOf()
                    tempScale.add(falseRootNote)
                    var indexOfRootNote = model.RootNotes.indexOf(falseRootNote)
                    var tempIndex = indexOfRootNote
                    for (i in 1..6) {
                        if (i != 3) {
                            tempIndex += 2
                            if (tempIndex > 11) {
                                tempIndex -= 12
                            }
                            tempScale.add(model.RootNotes[tempIndex])
                        } else {
                            tempIndex += 1
                            if (tempIndex > 11) {
                                tempIndex -= 12
                            }
                            tempScale.add(model.RootNotes[tempIndex])
                        }
                    }
                    sharedViewModel.ScaleNotes = tempScale
                }

                if (!metronomeLooperObject.isAlive)
                    metronomeLooperObject.start()
                else
                    metronomeLooperObject.run()
            }
            else if(sharedViewModel.PracticeType.value == model.PracticeType[1] && sharedViewModel.ChordType.count() == 0)
            {
                btnStartStopText.value = R.string.btn_Start
                Toast.makeText(this.context, R.string.msg_select_chords, Toast.LENGTH_LONG)
                var dialog : AlertDialog.Builder = AlertDialog.Builder(this.context)
                dialog.setTitle(R.string.msg_select_chords)
                dialog.setCancelable(false)
                dialog.setPositiveButton("Ok"){ dialogInterface: DialogInterface, i: Int ->

                }
                dialog.show()
            }
            else if(sharedViewModel.PracticeType.value == model.PracticeType[0]) {
                if (!metronomeLooperObject.isAlive)
                    metronomeLooperObject.start()
                else
                    metronomeLooperObject.run()
            }

        }
            else if(btnStartStopText.value == R.string.btn_Stop && sharedViewModel.btnclickcount > 0) {
            if (sharedViewModel.PracticeType.value == model.PracticeType[1]){
                for (item in sharedViewModel.ChordType)
                {
                    if (item == "Maj"){
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[3].add(item)
                        chordsToDisplayAccordingToSelected[4].add(item)
                    }
                    else if(item == "min") {
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[2].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                    else if(item == "dim") {
                        chordsToDisplayAccordingToSelected[6].add(item)
                    }
                    else if(item == "Maj7") {
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[3].add(item)
                    }
                    else if(item == "min7") {
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[2].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                    else if(item == "dom") {
                        chordsToDisplayAccordingToSelected[4].add(item)
                    }
                    else if(item == "sus2") {
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[3].add(item)
                        chordsToDisplayAccordingToSelected[4].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                    else if(item == "sus4") {
                        chordsToDisplayAccordingToSelected[0].add(item)
                        chordsToDisplayAccordingToSelected[1].add(item)
                        chordsToDisplayAccordingToSelected[2].add(item)
                        chordsToDisplayAccordingToSelected[4].add(item)
                        chordsToDisplayAccordingToSelected[5].add(item)
                    }
                }
                if(sharedViewModel.ScaleType.value == model.ScaleType[0]){
                    falseRootNote = sharedViewModel.RootNote.toString()
                    falseScaleNotes = sharedViewModel.ScaleNotes
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[1]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 2 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 2]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 2]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[2]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 4 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 4]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 4]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[3]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 5 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 5]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 5]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[4]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 7 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 7]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 7]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[5]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 9 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 9]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 9]
                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[6]){
                    var indexOfRootNote = model.RootNotes.indexOf(sharedViewModel.RootNote.value)
                    if(indexOfRootNote - 11 >= 0){
                        falseRootNote = model.RootNotes[indexOfRootNote - 11]
                    }
                    else{
                        falseRootNote = model.RootNotes[12 + indexOfRootNote - 11]
                    }
                }
                if(sharedViewModel.ScaleType.value != model.ScaleType[0]) {
                    tempScale = mutableListOf()
                    tempScale.add(falseRootNote)
                    var indexOfRootNote = model.RootNotes.indexOf(falseRootNote)
                    var tempIndex = indexOfRootNote
                    for (i in 1..6) {
                        if (i != 3) {
                            tempIndex += 2
                            if (tempIndex > 11) {
                                tempIndex -= 12
                            }
                            tempScale.add(model.RootNotes[tempIndex])
                        } else {
                            tempIndex += 1
                            if (tempIndex > 11) {
                                tempIndex -= 12
                            }
                            tempScale.add(model.RootNotes[tempIndex])
                        }
                    }
                    sharedViewModel.ScaleNotes = tempScale
                }
            }
            radioButton1.value = false
            metronomeLooperObject.run()
        }
            else if (btnStartStopText.value == R.string.btn_Start) {
                radioButton1.value = false
                radioButton2.value = false
        }
        btnclickcount++
    }

    fun onMinusOneClicked() {
        if(sharedViewModel.Tempo.value!!.toLong() > 10) {
            sharedViewModel.Tempo.value = (sharedViewModel.Tempo.value!!.toLong() - 1).toString()
            tempo = 60000 / sharedViewModel.Tempo.value!!.toLong()
        }
    }

    fun onMinusFiveClicked() {
        if(sharedViewModel.Tempo.value!!.toLong() > 14) {
            sharedViewModel.Tempo.value = (sharedViewModel.Tempo.value!!.toLong() - 5).toString()
            tempo = 60000 / sharedViewModel.Tempo.value!!.toLong()
        }
    }

    fun onPlusFiveClicked() {
        if(sharedViewModel.Tempo.value!!.toLong() < 196) {
            sharedViewModel.Tempo.value =  (sharedViewModel.Tempo.value!!.toLong() + 5).toString()
            tempo = 60000 / sharedViewModel.Tempo.value!!.toLong()
        }
    }

    fun onPlusOneClicked() {
        if(sharedViewModel.Tempo.value!!.toLong() < 200) {
            sharedViewModel.Tempo.value = (sharedViewModel.Tempo.value!!.toLong() + 1).toString()
            tempo = 60000 / sharedViewModel.Tempo.value!!.toLong()
        }
    }

    var lostFocusFlag : Boolean = false
    fun HomeFragmentPaused() {
//      if(mediaPlayer1!=null){}

//        if(mediaPlayer2!=null)
//        mediaPlayer2.stop()
//        lostFocusFlag = true
        btnStartStopText.value = R.string.btn_Start
        metronomeLooperObject.interrupt()
    }


}

class MetronomeThread(var homeViewModel : HomeViewModel, var model : Model) : Thread() {
    var  handler : Handler = Handler()



    override fun run() {
        Log.d("loop9","MetronomeRun")
        if (homeViewModel.btnStartStopText.value == R.string.btn_Stop) {
            if (Looper.myLooper() == null)
                Looper.prepare()
            handler.post {
                Log.d("loop9","1/2")
                homeViewModel.radioButton1.value = !homeViewModel.radioButton1.value!!
                if(homeViewModel.btnStartStopText.value == R.string.btn_Stop){
                if(homeViewModel.radioButton1.value == true){
                    displayNotes(homeViewModel.sharedViewModel.NotesType.value.toString(), homeViewModel.sharedViewModel.ScaleNotes ,homeViewModel.sharedViewModel.RootNote.value)
                    homeViewModel.mediaPlayer1.start()
                    //homeViewModel.mediaPlayer2.pause()
                }
                else {
                    //homeViewModel.mediaPlayer1.pause()
                    homeViewModel.mediaPlayer2.start()
                }
                }
                homeViewModel.radioButton2.value = !homeViewModel.radioButton1.value!!
                handler.postDelayed(this,homeViewModel.tempo)
            }
            Log.d("loop9","Loop Metronome")
            Looper.loop()

        }
        else
            return
    }

    private fun displayNotes(notestype: String, scaleNotes: List<String>, rootNote: String?){

        var temp : String = ""
        homeViewModel.sharedViewModel.ChordType

        //region Notes practice
        if(homeViewModel.sharedViewModel.PracticeType.value == model.PracticeType[0]) {

            //region Natural Notes
            if (notestype == model.NotesType[0]) {
                homeViewModel.sharpOrflatText.value = ""
                temp = model.NaturalNotes.asSequence().shuffled().find { true }.toString()
                while (temp == homeViewModel.prevNote) {
                    temp = model.NaturalNotes.asSequence().shuffled().find { true }.toString()
                }
                if(homeViewModel.sharedViewModel.ShowNoteDegreeChecked.value == true)
                    homeViewModel.TextNote.value = (model.NaturalNotes.indexOf(temp) + 1).toString()
                else
                homeViewModel.TextNote.value = temp
            }
            //endregion

            //region All Notes
            else if (notestype == model.NotesType[1]) {
                temp = model.AllNotes.asSequence().shuffled().find { true }.toString()
                while (temp == homeViewModel.prevNote) {
                    temp = model.AllNotes.asSequence().shuffled().find { true }.toString()
                }
                var displayNote = temp
                var sharpnote = displayNote?.contains("#")
                displayNote = displayNote?.replace("#", "", ignoreCase = true)
                if(homeViewModel.sharedViewModel.ShowNoteDegreeChecked.value == true)
                    homeViewModel.TextNote.value = (model.NaturalNotes.indexOf(displayNote) + 1).toString()
                else
                homeViewModel.TextNote.value = displayNote?.replace("#", "", ignoreCase = true)
                if (sharpnote == true) {
                    homeViewModel.sharpOrflatText.value = "#"
                } else {
                    homeViewModel.sharpOrflatText.value = ""
                }
            }
            //endregion

            //region Major Scale Notes
            else if(notestype == model.NotesType[2]){
                if(homeViewModel.sharedViewModel.ShowNoteDegreeChecked.value == false) {
                    temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    while (temp == homeViewModel.prevNote) {
                        temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    }
                    var displayNote = temp
                    var sharpnote = displayNote?.contains("#")
                    homeViewModel.TextNote.value = displayNote?.replace("#", "", ignoreCase = true)
                    if (sharpnote == true) {
                        homeViewModel.sharpOrflatText.value = "#"
                    } else {
                        homeViewModel.sharpOrflatText.value = ""
                    }
                }
                else{
                    homeViewModel.sharpOrflatText.value = ""
                    temp = model.DegreeOfNotes.asSequence().shuffled().find { true }.toString()
                    while (temp == homeViewModel.prevNote) {
                        temp = model.DegreeOfNotes.asSequence().shuffled().find { true }.toString()
                    }
                    homeViewModel.TextNote.value = temp
                }
            }
            //endregion

            //region Minor Scale Notes
            else if(notestype == model.NotesType[3]){
                if(homeViewModel.sharedViewModel.ShowNoteDegreeChecked.value == false) {
                    temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    while (temp == homeViewModel.prevNote) {
                        temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    }
                    var displayNote = temp
                    var sharpnote = displayNote?.contains("#")
                    homeViewModel.TextNote.value = displayNote?.replace("#", "", ignoreCase = true)
                    if (sharpnote == true) {
                        homeViewModel.sharpOrflatText.value = "#"
                    } else {
                        homeViewModel.sharpOrflatText.value = ""
                    }
                }
                else{
                    homeViewModel.sharpOrflatText.value = ""
                    temp = model.DegreeOfNotes.asSequence().shuffled().find { true }.toString()
                    while (temp == homeViewModel.prevNote) {
                        temp = model.DegreeOfNotes.asSequence().shuffled().find { true }.toString()
                    }
                    homeViewModel.TextNote.value = temp
                }
            }
            //endregion


            //set the displayed note as previous note for the next loop
            homeViewModel.prevNote = temp

        }
        //endregion


        //region Chords Practice
        else
        {
            if(homeViewModel.sharedViewModel.ShowNoteDegreeChecked.value == false) {
                temp = scaleNotes.random().toString()
                //temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                //count == 0 is handled on Start click only
                //if there is only one chord then it should not check with prev note as it will be same which in case of dim and dom chords
                if (homeViewModel.sharedViewModel.ChordType.count() == 1
                    && (homeViewModel.sharedViewModel.ChordType.contains(model.ChordTypes[2])
                            || homeViewModel.sharedViewModel.ChordType.contains(model.ChordTypes[5]))
                ) {
                    //no need to check with previous note because there is only one note
                    while (homeViewModel.chordsToDisplayAccordingToSelected[scaleNotes.indexOf(temp)].isEmpty()) {
                        temp = scaleNotes.random().toString()
                        //temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    }
                }
                //rest all cases i.e maj, min , maj7 etc there are more than one chords so it needs to be checked with the previous displayed not as well
                else {
                    //need to check if it is like previous note or the index is empty in chordsToDisplayAccordingToSelected
                    while (temp == homeViewModel.prevNote || homeViewModel.chordsToDisplayAccordingToSelected[scaleNotes.indexOf(temp)].isEmpty()
                    ) {
                        temp = scaleNotes.random().toString()
                        //temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    }
                }
                var displayNote = temp
                var sharpnote = displayNote?.contains("#")
                homeViewModel.TextNote.value = displayNote?.replace("#", "", ignoreCase = true)
                if (sharpnote == true) {
                    homeViewModel.sharpOrflatText.value = "#"
                } else {
                    homeViewModel.sharpOrflatText.value = ""
                }
                var indexof = scaleNotes.indexOf(displayNote)

                //chose a random chord from the assigned note for eg. at 1 it can me Maj, Maj7, sus2 or sus4 so it chooses one randomly out of these
                homeViewModel.ChordType.value =
                    homeViewModel.chordsToDisplayAccordingToSelected[indexof].random()


            }
            else{
                if (homeViewModel.sharedViewModel.ChordType.count() == 1
                    && (homeViewModel.sharedViewModel.ChordType.contains(model.ChordTypes[2])
                            || homeViewModel.sharedViewModel.ChordType.contains(model.ChordTypes[5]))
                ) {
                    temp = scaleNotes.random().toString()
                    //no need to check with previous note because there is only one note
                    while (homeViewModel.chordsToDisplayAccordingToSelected[scaleNotes.indexOf(temp)].isEmpty()) {
                        temp = scaleNotes.random().toString()
                        //temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    }
                }
                //rest all cases i.e maj, min , maj7 etc there are more than one chords so it needs to be checked with the previous displayed not as well
                else {
                    //need to check if it is like previous note or the index is empty in chordsToDisplayAccordingToSelected
                    temp = scaleNotes.random().toString()
                    while (temp == homeViewModel.prevNote || homeViewModel.chordsToDisplayAccordingToSelected[scaleNotes.indexOf(temp)].isEmpty()
                    ) {
                        temp = scaleNotes.random().toString()
                        //temp = scaleNotes.asSequence().shuffled().find { true }.toString()
                    }
                }
                homeViewModel.TextNote.value = (scaleNotes.indexOf(temp) + 1).toString()
                homeViewModel.sharpOrflatText.value = ""
                //chose a random chord from the assigned note for eg. at 1 it can me Maj, Maj7, sus2 or sus4 so it chooses one randomly out of these
                homeViewModel.ChordType.value =
                    homeViewModel.chordsToDisplayAccordingToSelected[scaleNotes.indexOf(temp)].random()
            }
            //save it as previous note so that it is not repeated
            homeViewModel.prevNote = temp
        }
        //endregion
        //else if(scaletype = homeViewModel.sharedViewModel.NotesType)
        //homeViewModel.prevNote = homeViewModel.TextNote.value.toString()
        }
    }





