/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(
            onItemClicked = {this},
            onClickButton = {word -> changeUndoneButton(word, word.id)}
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val checkbox = findViewById<AppCompatCheckBox>(R.id.checkbox)

        fun showList(){
            if (checkbox.isChecked){
                wordViewModel.undoneWords.observe(owner = this) {words ->
                    words.let {adapter.submitList(it)}
                }
            } else {
                wordViewModel.allWords.observe(owner = this) {words ->
                    words.let {adapter.submitList(it)}
                }
            }
        }
        showList()

        checkbox.setOnClickListener {
            showList()
        }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {


            val title = intentData?.getStringExtra("title").toString()
            val content = intentData?.getStringExtra("content").toString()
            val flag =intentData?.getStringExtra("flag").toString()

            val word = Word(null,title, content, flag)
            wordViewModel.insert(word)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun changeUndoneButton(word: Word, id:Long?){
        if (word.flag == "undone"){
            wordViewModel.undoneToDone(id)
        } else {
            wordViewModel.doneToUndone(id)
        }
    }

}
