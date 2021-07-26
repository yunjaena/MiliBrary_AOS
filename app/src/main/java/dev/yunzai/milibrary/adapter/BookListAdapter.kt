package dev.yunzai.milibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.data.model.Book
import dev.yunzai.milibrary.databinding.ItemBookBinding

class BookListAdapter(
    private val clickListener: ((Book) -> Unit)? = null
) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    private val list = arrayListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBookBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_book, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            bookTitle.text = item.title ?: ""
            Glide.with(holder.itemView)
                .load(item.thumbnail)
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

    fun add(list: ArrayList<Book>) {
        val currentSize = this.list.size
        this.list.addAll(list)
        notifyItemRangeChanged(currentSize, this.list.size)
    }

    class ViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)
}
