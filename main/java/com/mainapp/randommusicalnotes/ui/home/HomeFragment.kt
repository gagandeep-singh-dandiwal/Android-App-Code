package com.mainApp.randommusicalnotes.ui.home

import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mainApp.randommusicalnotes.R
import com.mainApp.randommusicalnotes.SharedViewModel
import com.mainApp.randommusicalnotes.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    public val sharedViewModel : SharedViewModel by activityViewModels()

    var flag = false
    lateinit var r : Runnable
    lateinit var txtNote: TextView
    lateinit var textViewTempo: TextView
    lateinit var textPracticeType: TextView
    lateinit var txtRootNote: TextView
    lateinit var txtScaleType: TextView
    lateinit var txtNotesType: TextView
    lateinit var btnStart : Button
    lateinit var  radioButton1 : RadioButton
    lateinit var  radioButton2 : RadioButton
    var btnStartClicked : Boolean = false
    lateinit var grpscaleType: LinearLayout
    lateinit var grpnotesType: LinearLayout


    final val handler : Handler = Handler()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.sharedViewModel = sharedViewModel

        grpscaleType = binding.grpScaleType!!

        grpnotesType = binding.grpNotesType!!

        txtScaleType = binding.txtScaleType!!
        sharedViewModel.ScaleType.observe(viewLifecycleOwner, Observer {
            txtScaleType.text = it
        })

        txtNotesType = binding.txtNotesType!!
        sharedViewModel.NotesType.observe(viewLifecycleOwner, Observer {
            txtNotesType.text = it
        })

        textPracticeType = binding.txtPracticeType!!
        sharedViewModel.PracticeType_homeScreen.observe(viewLifecycleOwner, Observer {
            textPracticeType.text = getString(it)
        })

        txtRootNote = binding.txtRootNote!!
        sharedViewModel.RootNote.observe(viewLifecycleOwner, Observer {
            txtRootNote.text = it
            txtNote.text = it
        })

        txtNote= binding.txtNote
        homeViewModel.TextNote.observe(viewLifecycleOwner, Observer {
            txtNote.text = it
        })

        textViewTempo = binding.txtTempo
//        homeViewModel.TextTempo.observe(viewLifecycleOwner, Observer {
//            textViewTempo.text = it
//        })

        sharedViewModel.Tempo.observe(viewLifecycleOwner, Observer {
            textViewTempo.text = it
        })

        val sharpOrflat: TextView = binding.sharpOrflat
        homeViewModel.sharpOrflatText.observe(viewLifecycleOwner, Observer {
            sharpOrflat.text = it
        })

        val txtChordType: TextView = binding.txtChordType
        homeViewModel.ChordType.observe(viewLifecycleOwner, Observer {
            txtChordType.text = it
        })



        btnStart = binding.btnStart
        btnStartClicked  = false
        btnStart.setOnClickListener {
            btnStartClicked = !btnStartClicked
            if(btnStartClicked) {
                btnStart.text = getString(R.string.btn_Stop)
                homeViewModel.btnStartStopText.value = R.string.btn_Stop
            }
            else {
                btnStart.text = getString(R.string.btn_Start)
                homeViewModel.btnStartStopText.value = R.string.btn_Start
            }
            context?.let { it1 -> homeViewModel.onStartClick(it1) }
        }
        homeViewModel.btnStartStopText.observe(viewLifecycleOwner,{
            btnStart.text = getString(it)
            if(btnStart.text == getString(R.string.btn_Start)){
                btnStartClicked = false
            }
            else if(btnStart.text == getString(R.string.btn_Stop)){
                btnStartClicked = true
            }
        })


        val btnMinusFive : Button = binding.btnMinusFive
        var btnMinusFiveClicked : Boolean = false
        btnMinusFive.setOnClickListener {
            homeViewModel.onMinusFiveClicked()
        }

        var mediaPlayer : MediaPlayer = MediaPlayer.create( context,R.raw.one)

        val btnMinusOne : Button = binding.btnMinusOne
        var btnMinusOneClicked : Boolean = false
        btnMinusOne.setOnClickListener {
            homeViewModel.onMinusOneClicked()
        }


        val btnPlusFive : Button = binding.btnPlusFive
        var btnPlusFiveClicked : Boolean = false
        btnPlusFive.setOnClickListener {
            homeViewModel.onPlusFiveClicked()

        }


        val btnPlusOne : Button = binding.btnPlusOne
        var btnPlusOneClicked : Boolean = false
        btnPlusOne.setOnClickListener {
            homeViewModel.onPlusOneClicked()
        }

        radioButton1 = binding.radioButton1
        homeViewModel.radioButton1.observe(viewLifecycleOwner,{
            radioButton1.isChecked = it
        })
        radioButton2 = binding.radioButton2
        homeViewModel.radioButton2.observe(viewLifecycleOwner,{
            radioButton2.isChecked = it
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
//        radioButton1.isChecked = false
//        radioButton2.isChecked = false
        homeViewModel.HomeFragmentPaused()
    }

    override fun onResume() {
        super.onResume()
        //if(sharedViewModel.PracticeType.value == homeViewModel.model.PracticeType[0]) {
        if(sharedViewModel.ShowNoteDegreeChecked.value == false) {
            var displayNote = sharedViewModel.RootNote.value
            var sharpnote = displayNote?.contains("#")
            if (sharedViewModel.RootNote.value?.contains("#") == true) {
                displayNote = displayNote?.replace("#", "", ignoreCase = true)
            }
            txtNote.text = displayNote

            if (sharpnote == true) {
                homeViewModel.sharpOrflatText.value = "#"
            } else {
                homeViewModel.sharpOrflatText.value = ""
            }
        }
        else
        {
            txtNote.text = homeViewModel.model.DegreeOfNotes[0]
        }
        textViewTempo.text = sharedViewModel.Tempo.value
        if(sharedViewModel.PracticeType.value == homeViewModel.model.PracticeType[0]){
            sharedViewModel.PracticeType_homeScreen.value = R.string.lbl_HomeScreen_PracticeType_Notes
            grpscaleType.visibility = View.INVISIBLE
            grpnotesType.visibility = View.VISIBLE
            txtNotesType.text = sharedViewModel.NotesType.value
            if((sharedViewModel.NotesType.value == sharedViewModel.model.NotesType[0]) ||
                (sharedViewModel.NotesType.value == sharedViewModel.model.NotesType[1])){
                txtRootNote.text = ""
                txtNote.text = "C"
                //sharedViewModel.RootNote.value = "C"
            }
        }
        else{
            sharedViewModel.PracticeType_homeScreen.value = R.string.lbl_Chords
            grpscaleType.visibility = View.VISIBLE
            grpnotesType.visibility = View.INVISIBLE
            txtScaleType.text = sharedViewModel.ScaleType.value
        }


        homeViewModel.btnStartStopText.value = R.string.btn_Start
        btnStart.text = getString(R.string.btn_Start)
        radioButton1.isChecked = false
        radioButton2.isChecked = false
        homeViewModel.radioButton1.value = false
        homeViewModel.radioButton2.value = false
        btnStartClicked = false
        homeViewModel.ChordType.value = ""
        //homeViewModel.metronomeLooperObject.run()
    //}
    }
}

