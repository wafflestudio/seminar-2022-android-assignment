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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.WordListAdapter.WordViewHolder

class WordListAdapter(private val onItemClicked: (Word) -> Unit,private val onClickButton: (Word) -> Unit)
    : ListAdapter<Word, WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)

        if (current.flag == "undone") {
            holder.undoneButton.text = holder.itemView.context.getString(R.string.button_undone)
        } else {
            holder.undoneButton.text = holder.itemView.context.getString(R.string.button_done)
        }
        holder.undoneButton.setOnClickListener{
            onClickButton(current)
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleItemView: TextView = itemView.findViewById(R.id.textview_title)
        private val contentItemView: TextView = itemView.findViewById(R.id.textview_content)
        val undoneButton: Button =  itemView.findViewById(R.id.button_undone)

        fun bind(word: Word) {
            titleItemView.text = word.title
            contentItemView.text = word.content
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }
}
