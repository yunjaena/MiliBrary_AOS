package dev.yunzai.milibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.yunzai.milibrary.R
import dev.yunzai.milibrary.data.model.Bookmark
import dev.yunzai.milibrary.databinding.ItemBookmarkBinding

class BookmarkAdapter(
    private val context: Context,
    private val clickListener: ((Bookmark) -> Unit)? = null
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    private val list = arrayListOf<Bookmark>()
    var deleteListener: ((Bookmark) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBookmarkBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_bookmark, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            titleTextView.text = item.book?.title ?: ""
            titleTextView.text = item.book?.title ?: ""
            authorTextView.text = item.book?.authors ?: ""
            publishTextView.text = item.createdAt ?: ""
            Glide.with(holder.itemView)
                .load(item.book?.thumbnail)
                .thumbnail(0.5f)
                .into(bookImage)
            contentTextView.text = item.content ?: context.getString(R.string.write_book_mark)

            moreButton.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                MenuInflater(context).inflate(R.menu.bookmark_more_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    return@setOnMenuItemClickListener when (menuItem.itemId) {
                        R.id.delete -> {
                            deleteListener?.invoke(item)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
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

    fun remove(bookmarkId: Int) {
        val deleteIndex = this.list.indexOfFirst { it.id == bookmarkId }
        if (deleteIndex == -1) return
        list.removeAt(deleteIndex)
        notifyItemRemoved(deleteIndex)
    }

    class ViewHolder(val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root)
}
