package dev.yunzai.milibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.databinding.ItemLongReviewBinding

class ReviewLongListAdapter(
    val itemClickListener: ((Review) -> Unit)? = null
) : RecyclerView.Adapter<ReviewLongListAdapter.ViewHolder>() {
    private val list = arrayListOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLongReviewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_long_review, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }
        with(holder.binding) {
            if (item.nickname != null) {
                nickname.visibility = View.VISIBLE
                nickname.text = item.nickname
            } else {
                nickname.visibility = View.GONE
            }
            if (item.score != null) {
                ratingBar.visibility = View.VISIBLE
                ratingBar.rating = item.score.toFloat()
            } else {
                ratingBar.visibility = View.GONE
            }

            if (item.comment != null) {
                reviewTextView.visibility = View.VISIBLE
                reviewTextView.text = item.comment
            } else {
                reviewTextView.visibility = View.GONE
            }

            if (item.createdAt != null) {
                createDate.visibility = View.VISIBLE
                createDate.text = item.createdAt
            } else {
                createDate.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun add(list: ArrayList<Review>) {
        val currentSize = this.list.size
        this.list.addAll(list)
        notifyItemRangeChanged(currentSize, this.list.size)
    }

    class ViewHolder(val binding: ItemLongReviewBinding) : RecyclerView.ViewHolder(binding.root)
}
