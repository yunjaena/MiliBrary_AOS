package dev.yunzai.milibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.data.model.Bookmark
import dev.yunzai.milibrary.databinding.ItemBookmarkBinding

class BookmarkAdapter(
    private val context: Context,
    private val clickListener: ((Bookmark) -> Unit)? = null
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    private val list = arrayListOf<Bookmark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBookmarkBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_bookmark, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            titleTextView.text = item.title ?: ""
            titleTextView.text = item.title ?: ""
            authorTextView.text = item.authors ?: ""
            publishTextView.text = item.createdAt ?: ""
            com.bumptech.glide.Glide.with(holder.itemView)
                .load(item.thumbnail)
                .thumbnail(0.5f)
                .into(bookImage)
            contentTextView.text = item.content ?: context.getString(R.string.write_book_mark)
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

    fun add(list: ArrayList<Bookmark>) {
        val currentSize = this.list.size
        this.list.addAll(list)
        notifyItemRangeChanged(currentSize, this.list.size)
    }

    class ViewHolder(val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root)
}

