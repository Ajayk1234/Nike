package com.example.testapplication.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.dictionary.ResultAdapter.ViewHolder
import com.example.testapplication.dictionary.model.ResultView
import kotlinx.android.synthetic.main.cell_suggestions.view.*

/**
 * Recycler view adapter for Suggestions
 */
class ResultAdapter :
    ListAdapter<ResultView, ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_suggestions,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            val item = getItem(position)
            tvWord.text = item.word
            tvDefinition.text = item.definition
            tvThumbsUp.text = item.thumbsUp.toString()
            tvThumbsDown.text = item.thumbsDown.toString()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResultView>() {
            override fun areItemsTheSame(oldItem: ResultView, newItem: ResultView): Boolean {
                return oldItem.defid == newItem.defid
            }

            override fun areContentsTheSame(oldItem: ResultView, newItem: ResultView): Boolean {
                return oldItem == newItem
            }
        }
    }
}


