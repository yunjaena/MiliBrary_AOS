package dev.yunzai.milibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.data.model.Review
import dev.yunzai.milibrary.databinding.ItemReviewBinding

class ReviewListAdapter(
    val clickListener: ((Review) -> Unit)? = null
) : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {
    private val list = arrayListOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReviewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_review, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            if (item.score != null) {
                ratingBar.visibility = View.VISIBLE
                ratingBar.rating = item.score.toFloat()
            } else {
                ratingBar.visibility = View.GONE
            }

            if (item.book?.title != null) {
                bookTitle.visibility = View.VISIBLE
                bookTitle.text = item.book.title
            } else {
                bookTitle.visibility = View.GONE
            }

            if (item.comment != null) {
                reviewTextView.visibility = View.VISIBLE
                reviewTextView.text = item.comment
            } else {
                reviewTextView.visibility = View.GONE
            }

            Glide.with(root)
                .load(item.book?.thumbnail)
                .thumbnail(0.5f)
                .into(bookImage)
        }

        holder.itemView.setOnClickListener {
            clickListener?.invoke(item)
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

    class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)
}
