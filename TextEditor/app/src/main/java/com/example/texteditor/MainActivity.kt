package com.example.texteditor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    //user interface components
    lateinit var textField: EditText
    lateinit var soundName: TextView
    lateinit var btnPLay: ImageButton
    lateinit var btnSave: ImageButton
    var fileName = ""
    //Media components
    private  lateinit var mp : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // fileName = intent.extras!!.getString(EXTRA_TITLE)
        super.setTitle(fileName)
        val soundID = intent.extras!!.getString(EXTRA_SOUND)

        btnPLay = findViewById(R.id.btnPlay)
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener(save)
        soundName = findViewById(R.id.txtSoundName)

        mp = MediaPlayer.create(this, R.raw.seafretoceans)
        var name = "SeaFreat - Oceans mp3 music play test"
        soundName.text = name
        soundName.isSelected = true

        fab.setOnClickListener { view ->
            val texto = textField.text
            texto.append(createTimeLabel(mp.currentPosition))
            textField.text =  Editable.Factory.getInstance().newEditable(texto)
        }
        
        textField =  findViewById(R.id.editText)
        try{
            var fileCont : File = File(baseContext.filesDir,fileName)
            textField.text = Editable.Factory.getInstance().newEditable(fileCont.readText())
        }catch(e : Exception){
            Log.d("FIle",e.toString())
        }

        barraTempo.max = mp.duration
        barraTempo.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        Thread(Runnable {
            while ( mp != null){
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e : InterruptedException){}
            }
        }).start()

    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var posicaoAtual = msg.what
            barraTempo.progress = posicaoAtual

            var txtTempo = createTimeLabel(posicaoAtual)
            tempoAtual.text = txtTempo

            var txtFalta = createTimeLabel(mp.duration -posicaoAtual)
            tempoRestante.text = txtFalta
        }
    }

    var save: (View) -> Unit =  { view :View->
        var fileContent = textField.text.toString()
        baseContext.openFileOutput(fileName,Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }


    fun play(view : View){
        if(mp.isPlaying){
            mp.pause()
            btnPLay.setBackgroundResource(R.drawable.ic_play_arrow_24px)

        }else{
            mp.start()
            btnPLay.setBackgroundResource(R.drawable.ic_pause_24px)
        }

    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_SOUND = "sound id"

        fun newIntent(context: Context, ar: Arquive): Intent {
            val detailIntent = Intent(context, MainActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, ar.fileName)
            detailIntent.putExtra(EXTRA_SOUND, ar.soundName)

            return detailIntent
        }
    }
}
