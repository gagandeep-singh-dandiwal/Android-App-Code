package com.mainApp.randommusicalnotes.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.mainApp.randommusicalnotes.Model
import com.mainApp.randommusicalnotes.SharedViewModel
import com.mainApp.randommusicalnotes.databinding.FragmentDashboardBinding
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()


    var model : Model = Model()
    lateinit var textSelectedChords : TextView

    var chordTypesArray = model.ChordTypes


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root




        val txtnotesType : TextView = binding.txtNoteType
        val txtScaleType : TextView = binding.txtScaleType
        val practiceTypeSpinner: Spinner = binding.practiceTypeSpinner
        val notesTypeSpinner : Spinner = binding.notesTypeSpinner
        val scaleTypeSpinner : Spinner = binding.scaleTypeSpinner
        val rootNotesSpinner : Spinner = binding.rootNoteSpinner
        textSelectedChords = binding.txtSelectedChords
        val showNoteDegreesCheckbox = binding.checkboxShowNoteDegree

        lateinit var tempScale : MutableList<String>
        lateinit var rootNote : String


        if(sharedViewModel.PracticeType.value == model.PracticeType[0])
        {
            notesTypeSpinner.isVisible = true
            txtnotesType.isVisible = true
            scaleTypeSpinner.isVisible = false
            txtScaleType.isVisible = false
        }
        else{
            notesTypeSpinner.isVisible = false
            txtnotesType.isVisible = false
            scaleTypeSpinner.isVisible = true
            txtScaleType.isVisible = true
        }



        //region Practice Type Spinner

        practiceTypeSpinner.dropDownVerticalOffset = 110
        practiceTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sharedViewModel.PracticeType.value =
                    adapterView?.getItemAtPosition(position).toString()
                //enable chord selection if practice type not notes
                textSelectedChords.isEnabled = sharedViewModel.PracticeType.value != model.PracticeType[0]
                if(sharedViewModel.PracticeType.value == model.PracticeType[0])
                {
                    notesTypeSpinner.isVisible = true
                    txtnotesType.isVisible = true
                    scaleTypeSpinner.isVisible = false
                    txtScaleType.isVisible = false
                    rootNotesSpinner.isEnabled =
                        !(sharedViewModel.NotesType.value == model.NotesType[0] || sharedViewModel.NotesType.value == model.NotesType[1])
                }
                else{
                    notesTypeSpinner.isVisible = false
                    txtnotesType.isVisible = false
                    scaleTypeSpinner.isVisible = true
                    txtScaleType.isVisible = true
                    rootNotesSpinner.isEnabled = true
                    rootNote = rootNotesSpinner.selectedItem.toString()
                    tempScale = mutableListOf()
                    tempScale.add(rootNote)
                    var indexOfRootNote = model.RootNotes.indexOf(rootNote)
                    var tempIndex = indexOfRootNote
                    if (scaleTypeSpinner.selectedItem.toString() == model.ScaleType[0]) {
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
                    } else if (sharedViewModel.NotesType.value == model.NotesType[3]) {
                        for (i in 1..6) {
                            if (i != 2 && i != 5) {
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
                    }
                    sharedViewModel.ScaleNotes = tempScale
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //endregion

        //region Notes Type Spinner

        notesTypeSpinner.dropDownVerticalOffset = 110
        notesTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sharedViewModel.NotesType.value =
                    adapterView?.getItemAtPosition(position).toString()
                if(sharedViewModel.NotesType.value == model.NotesType[0])
                    sharedViewModel.ScaleNotes = model.NaturalNotes as MutableList<String>
                else if(sharedViewModel.NotesType.value == model.NotesType[1])
                    sharedViewModel.ScaleNotes = model.AllNotes as MutableList<String>
                rootNotesSpinner.isEnabled =
                    !(sharedViewModel.NotesType.value == model.NotesType[0] || sharedViewModel.NotesType.value == model.NotesType[1])
                if(!(sharedViewModel.NotesType.value == model.NotesType[0] || sharedViewModel.NotesType.value == model.NotesType[1])) {
                    rootNote = rootNotesSpinner.selectedItem.toString()
                    tempScale = mutableListOf()
                    tempScale.add(rootNote)
                    var indexOfRootNote = model.RootNotes.indexOf(rootNote)
                    var tempIndex = indexOfRootNote
                    if (sharedViewModel.NotesType.value == model.NotesType[2]) {
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
                    } else if (sharedViewModel.NotesType.value == model.NotesType[3]) {
                        for (i in 1..6) {
                            if (i != 2 && i != 5) {
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
                    }
                    sharedViewModel.ScaleNotes = tempScale
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //endregion

        //region Scale Type Spinner

        scaleTypeSpinner.dropDownVerticalOffset = 110
        scaleTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sharedViewModel.ScaleType.value =
                    adapterView?.getItemAtPosition(position).toString()
                if(sharedViewModel.ScaleType.value == model.ScaleType[0]){
//                    rootNote = rootNotesSpinner.selectedItem.toString()
//                    tempScale = mutableListOf()
//                    tempScale.add(rootNote)
//                    var indexOfRootNote = model.RootNotes.indexOf(rootNote)
//                    var tempIndex = indexOfRootNote
//                    if (scaleTypeSpinner.selectedItem.toString() == model.ScaleType[0]) {
//                        for (i in 1..6) {
//                            if (i != 3) {
//                                tempIndex += 2
//                                if (tempIndex > 11) {
//                                    tempIndex -= 12
//                                }
//                                tempScale.add(model.RootNotes[tempIndex])
//                            } else {
//                                tempIndex += 1
//                                if (tempIndex > 11) {
//                                    tempIndex -= 12
//                                }
//                                tempScale.add(model.RootNotes[tempIndex])
//                            }
//                        }
//                    } else if (sharedViewModel.NotesType.value == model.NotesType[3]) {
//                        for (i in 1..6) {
//                            if (i != 2 && i != 5) {
//                                tempIndex += 2
//                                if (tempIndex > 11) {
//                                    tempIndex -= 12
//                                }
//                                tempScale.add(model.RootNotes[tempIndex])
//                            } else {
//                                tempIndex += 1
//                                if (tempIndex > 11) {
//                                    tempIndex -= 12
//                                }
//                                tempScale.add(model.RootNotes[tempIndex])
//                            }
//                        }
//                    }
                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[1]){

                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[2]){

                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[3]){

                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[4]){

                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[5]){

                }
                else if (sharedViewModel.ScaleType.value == model.ScaleType[6]){

                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //endregion

        //region Root Note

        rootNotesSpinner.dropDownVerticalOffset = 110
        rootNotesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                sharedViewModel.RootNote.value =
                    adapterView?.getItemAtPosition(position).toString()
                if(sharedViewModel.PracticeType.value == model.PracticeType[0]) {
                    rootNote = sharedViewModel.RootNote.value.toString()
                    tempScale = mutableListOf()
                    tempScale.add(rootNote)
                    var indexOfRootNote = model.RootNotes.indexOf(rootNote)
                    var tempIndex = indexOfRootNote
                    if (sharedViewModel.NotesType.value == model.NotesType[2]) {
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
                    }
                    else if (sharedViewModel.NotesType.value == model.NotesType[3]) {
                        for (i in 1..6) {
                            if (i != 2 && i != 5) {
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
                    }
                }
                else if (sharedViewModel.PracticeType.value == model.PracticeType[1]){
                    rootNote = rootNotesSpinner.selectedItem.toString()
                    tempScale = mutableListOf()
                    tempScale.add(rootNote)
                    var indexOfRootNote = model.RootNotes.indexOf(rootNote)
                    var tempIndex = indexOfRootNote
                    if (scaleTypeSpinner.selectedItem.toString() == model.ScaleType[0]) {
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
                    } else if (sharedViewModel.NotesType.value == model.NotesType[3]) {
                        for (i in 1..6) {
                            if (i != 2 && i != 5) {
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
                    }
                }
                sharedViewModel.ScaleNotes = tempScale
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //endregion

        //region Chords Types Pop up

        textSelectedChords.setOnClickListener(View.OnClickListener {
            var dialog : AlertDialog.Builder = AlertDialog.Builder(requireContext())
            dialog.setTitle("Select Chords")
            dialog.setCancelable(false)

            var addedSelectedChordsIndexList : ArrayList<Int> = ArrayList()
            var removedSelectedChordsIndexList : ArrayList<Int> = ArrayList()
            var count = sharedViewModel.selectedChordsIndexList.count()

            dialog.setMultiChoiceItems(chordTypesArray,sharedViewModel.selectedchordsboolean){ dialogInterface: DialogInterface, i: Int, b: Boolean ->
                if (b){
                    if(!removedSelectedChordsIndexList.contains(i))
                        addedSelectedChordsIndexList.add(i)
                    else
                        removedSelectedChordsIndexList.remove(i)
                    //sharedViewModel.selectedChordsList.add(i)
                    Collections.sort(sharedViewModel.selectedChordsIndexList)
                    //sharedViewModel.selectedchordsboolean[i] = true
                }
                else{
                    //sharedViewModel.selectedChordsList.remove(i)
                    if(!addedSelectedChordsIndexList.contains(i))
                        removedSelectedChordsIndexList.add(i)
                    else
                        addedSelectedChordsIndexList.remove(i)
                   // sharedViewModel.selectedchordsboolean[i] = false
                }
            }

            dialog.setPositiveButton("Ok"){ dialogInterface: DialogInterface, i: Int ->
                var stringbuilder = StringBuilder()
                sharedViewModel.ChordType.clear()
                for (item in addedSelectedChordsIndexList)
                {
                    sharedViewModel.selectedChordsIndexList.add(item)
                    sharedViewModel.selectedChordsIndexList.sort()
                    //sharedViewModel.ChordType.add(model.ChordTypes[item])
                }
                for (item in removedSelectedChordsIndexList)
                {
                    sharedViewModel.selectedChordsIndexList.remove(item)
                    sharedViewModel.selectedChordsIndexList.sort()
                    //sharedViewModel.ChordType.remove(model.ChordTypes[item])
                }
                for (item in sharedViewModel.selectedChordsIndexList) {

                    if (!sharedViewModel.ChordType.contains(chordTypesArray[item])) {
                        sharedViewModel.ChordType.add(chordTypesArray[item].toString())
                        stringbuilder.append(chordTypesArray[item])
                    } else {
                        sharedViewModel.ChordType.remove(chordTypesArray[item].toString())
                    }
                    if (sharedViewModel.selectedChordsIndexList.indexOf(item) != sharedViewModel.selectedChordsIndexList.size - 1) {
                        stringbuilder.append(",")
                    }
                }
                //finally set the string builder wether there are selected items or not
                textSelectedChords.text = stringbuilder.toString()
//                if(sharedViewModel.selectedChordsIndexList.count() != 0) {
//                    for (j in 0..sharedViewModel.selectedChordsIndexList.count() - 1) {
//
//                        if (!sharedViewModel.ChordType.contains(chordTypesArray[sharedViewModel.selectedChordsIndexList[j]])) {
//                            sharedViewModel.ChordType.add(chordTypesArray[sharedViewModel.selectedChordsIndexList[j]].toString())
//                            stringbuilder.append(chordTypesArray[sharedViewModel.selectedChordsIndexList[j]])
//                        } else {
//                            sharedViewModel.ChordType.remove(chordTypesArray[j].toString())
//                        }
//                        if (j != sharedViewModel.selectedChordsIndexList.size - 1) {
//                            stringbuilder.append(",")
//                        }
//                        textSelectedChords.text = stringbuilder.toString()
//                    }
//                }
//                else
//                {
//                    textSelectedChords.text = ""
//                    textSelectedChords.error = ""
//                    Toast.makeText(this.context,"Please select chords",Toast.LENGTH_LONG)
//                }
                sharedViewModel.txtSelectedChords = textSelectedChords.text.toString()
            }

            dialog.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->

                for(item in addedSelectedChordsIndexList){
                    sharedViewModel.selectedchordsboolean[item] = false
                }

                for(item in removedSelectedChordsIndexList){
                    sharedViewModel.selectedchordsboolean[item] = true
                }

//                for(j in 0..removedSelectedChordsIndexList.count() - 1){
//                    sharedViewModel.selectedChordsIndexList.add(removedSelectedChordsIndexList[j])
//                    sharedViewModel.selectedchordsboolean[removedSelectedChordsIndexList[j]] = true
//                    sharedViewModel.ChordType.add(model.ChordTypes[removedSelectedChordsIndexList[j]])
//                }
//                for(j in 0..addedSelectedChordsIndexList.count() - 1){
//                    sharedViewModel.selectedChordsIndexList.remove(addedSelectedChordsIndexList[j])
//                    sharedViewModel.selectedchordsboolean[addedSelectedChordsIndexList[j]] = false
//                    sharedViewModel.ChordType.remove(model.ChordTypes[addedSelectedChordsIndexList[j]])
//                }

                dialogInterface.cancel()
            }
            dialog.setOnCancelListener {
            }
            dialog.show()
        })


//endregion

        showNoteDegreesCheckbox.setOnClickListener {
            var x = it
            if (showNoteDegreesCheckbox.isChecked == true)
            sharedViewModel.ShowNoteDegreeChecked.value = true
            else
                sharedViewModel.ShowNoteDegreeChecked.value = false
        }

        return root
    }



    override fun onResume() {
        super.onResume()

        //var actionbar = activity?.actionBar

        //var a = (activity as AppCompatActivity).supportActionBar
        //a?.setDisplayHomeAsUpEnabled(true)

        //region practice Type spinner
        var practiceTypeArray = dashboardViewModel.model.PracticeType
        var practiceTypeAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,
            practiceTypeArray)
        val practiceTypeSpinner: Spinner = binding.practiceTypeSpinner
        if(sharedViewModel.PracticeType.value == ""){
            practiceTypeSpinner.adapter = practiceTypeAdapter
        }
        else{
            practiceTypeSpinner.adapter = practiceTypeAdapter
            var position = sharedViewModel.model.PracticeType.indexOf(sharedViewModel.PracticeType.value)
            practiceTypeSpinner.setSelection(position)
        }
        //endregion

        //region Notes Type Spinner
        var notesTypeArray = dashboardViewModel.model.NotesType
        var notesTypeAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item , notesTypeArray)
        val notesTypeSpinner : Spinner = binding.notesTypeSpinner
        if(sharedViewModel.NotesType.value == ""){
            notesTypeSpinner.adapter = notesTypeAdapter
        }
        else{
            notesTypeSpinner.adapter = notesTypeAdapter
            var position = sharedViewModel.model.NotesType.indexOf(sharedViewModel.NotesType.value)
            notesTypeSpinner.setSelection(position)
        }
        //endregion

        //region Scale Type Spinner
        var scaleTypeArray = dashboardViewModel.model.ScaleType
        var scaleTypeAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item , scaleTypeArray)
        val scaleTypeSpinner : Spinner = binding.scaleTypeSpinner
        if(sharedViewModel.ScaleType.value == ""){
            scaleTypeSpinner.adapter = scaleTypeAdapter
        }
        else{
            scaleTypeSpinner.adapter = scaleTypeAdapter
            var position = sharedViewModel.model.ScaleType.indexOf(sharedViewModel.ScaleType.value)
            scaleTypeSpinner.setSelection(position)
        }
        //endregion

        //region Root Note
        var rootNotesArray = dashboardViewModel.model.RootNotes
        var rootNotesAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item , rootNotesArray)
        val rootNotesSpinner : Spinner = binding.rootNoteSpinner
        if(sharedViewModel.RootNote.value == ""){
            rootNotesSpinner.adapter = rootNotesAdapter
        }
        else{
            rootNotesSpinner.adapter = rootNotesAdapter
            var position = sharedViewModel.model.RootNotes.indexOf(sharedViewModel.RootNote.value)
            rootNotesSpinner.setSelection(position)
        }
        //endregion

        //region Selected Chords
        //var stringbuilder : StringBuilder = java.lang.StringBuilder()
        var countOfInitiallySelectedChords = 0
        for (item in sharedViewModel.ChordType){
            if(!sharedViewModel.selectedChordsIndexList.contains(model.ChordTypes.indexOf(item)))
            sharedViewModel.selectedChordsIndexList.add(model.ChordTypes.indexOf(item) )
            sharedViewModel.selectedChordsIndexList.sort()
            //stringbuilder.append(item.toString())
            //if (sharedViewModel.ChordType.indexOf(item) < sharedViewModel.ChordType.count() - 1)
                //stringbuilder.append(",")
        }
        textSelectedChords.setText(sharedViewModel.txtSelectedChords)
        //endregion

        val showNoteDegreeCheckbox = binding.checkboxShowNoteDegree
        showNoteDegreeCheckbox.isChecked = sharedViewModel.ShowNoteDegreeChecked.value == true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}

