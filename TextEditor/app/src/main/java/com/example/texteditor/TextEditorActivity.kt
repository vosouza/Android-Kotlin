package com.example.texteditor

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class TextEditorActivity: AppCompatActivity(){

    val PICK_AUDIO_REQUEST = 1

    var textTile = ""
    var soundId: Int? = null
    var mp: MediaPlayer? = null

    val edtTextoPrincipal = findViewById<EditText>(R.id.editText)

    val btnSave = findViewById<ImageButton>(R.id.btnSave)
    val saveListener = View.OnClickListener {

    }

    val btnPlay = findViewById<ImageButton>(R.id.btnPlay)
    val playListener = View.OnClickListener {
        if(mp!!.isPlaying){
            btnPlay.setBackgroundResource(R.drawable.ic_play_arrow_24px)
        }else{
            btnPlay.setBackgroundResource(R.drawable.ic_pause_24px)
        }
    }

    val btnChooseSound = findViewById<ImageButton>(R.id.btnChooseSound)
    val chooseSoundListenner = View.OnClickListener {
        val soundIntent = Intent()
        soundIntent.setType("audio/*")
        soundIntent.setAction(Intent.ACTION_GET_CONTENT)
        soundIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(soundIntent,PICK_AUDIO_REQUEST)
    }

    fun createTimeText(time: Int):String{
        var finalText = ""

        var sec = time / 1000 % 60 //resto
        var min = time / 1000 / 60
        finalText = "[ $min: $sec ]"

        return finalText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSave.setOnClickListener(saveListener)
        btnPlay.setOnClickListener(playListener)
        btnChooseSound.setOnClickListener(chooseSoundListenner)

        fab.setOnClickListener { view ->
            val texto = edtTextoPrincipal.text
            texto.append(createTimeText(mp!!.currentPosition))
            edtTextoPrincipal.text =  Editable.Factory.getInstance().newEditable(texto)
            edtTextoPrincipal.setSelection(texto.length)
        }
    }
}