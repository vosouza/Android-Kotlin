package com.example.texteditor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast

class TextListActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_list)

        listView = findViewById(R.id.lista_textos)

        var lista: ArrayList<Arquive> = ArrayList()
        lista.add(Arquive("text1.txt",R.raw.seafretoceans))
        lista.add(Arquive("text2.txt",R.raw.seafretoceans))

        val adapter = FilesAdapter(this, lista)
        listView.adapter = adapter

        val context = this
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedRecipe = lista[position]
            val detailIntent = MainActivity.newIntent(context, selectedRecipe)
            startActivity(detailIntent)
        }

    }
}
