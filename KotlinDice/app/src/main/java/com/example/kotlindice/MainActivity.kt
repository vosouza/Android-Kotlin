package com.example.kotlindice

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    lateinit var image : ImageView;
    lateinit var image2 : ImageView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image =  findViewById(R.id.imageView)
        image2 = findViewById(R.id.imageView2)

        val rollBtn: Button = findViewById(R.id.button)
        rollBtn.setOnClickListener{rollDice()};

    }

    fun rollDice(){
        image.setImageResource(getRandImage())
        image2.setImageResource(getRandImage())
    }

    private fun getRandImage() : Int {
        val sortNumber: Int = (1..6).random()

        when(sortNumber){
            1 -> return R.drawable.dice_1
            2 -> return R.drawable.dice_2
            3 -> return R.drawable.dice_3
            4 -> return R.drawable.dice_4
            5 -> return R.drawable.dice_5
            6 -> return R.drawable.dice_6
            else -> return R.drawable.empty_dice
        }
    }
}
