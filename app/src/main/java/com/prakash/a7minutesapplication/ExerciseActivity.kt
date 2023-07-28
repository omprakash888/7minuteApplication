package com.prakash.a7minutesapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prakash.a7minutesapplication.databinding.ActivityExerciseBinding
import com.prakash.a7minutesapplication.databinding.DialogCustomBackButtonBinding
import java.lang.Exception
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private  var binding : ActivityExerciseBinding? = null

    private var textToSpeech : TextToSpeech? = null
    private var player : MediaPlayer? = null

    private var restTimer : CountDownTimer?  = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer?  = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var exerciseStatusAdapter : ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        textToSpeech = TextToSpeech(this, this)
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        setUpRestView()
        setUpExerciseStatusRecyclerView()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackButtonBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.yesButton.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.noButton.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //GridLayoutManager(this,3, RecyclerView.VERTICAL,false)

        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseStatusAdapter

    }

    private fun setUpRestView() {

        try {
            val soundUri = Uri.parse("android.resource://com.prakash.a7minutesapplication/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundUri)
            player?.isLooping = false
            player?.start()
        }
        catch (e : Exception) {
            e.printStackTrace()
        }

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.title?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpComingLabel?.visibility = View.VISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.VISIBLE


        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpComingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setUpExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.title?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpComingLabel?.visibility = View.INVISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.INVISIBLE

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExercise?.text = exerciseList!![currentExercisePosition].getName()

        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()
                if(currentExercisePosition < exerciseList!!.size - 1) {
                    setUpRestView()
                }else {
                    finish()
                    startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if(textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        if(player != null) {
            player!!.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result =  textToSpeech?.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "the specified language is not supported")
            }
        }
        else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    fun speakOut(text : String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}