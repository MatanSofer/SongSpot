package com.example.myapplication.Spotify.touch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ListRecyclerTouchCallback (private val listItemTouchHelperAdapter: ListItemTouchHelperCallback):
ItemTouchHelper.Callback(){

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags,swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        listItemTouchHelperAdapter.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }
    // to swipe list item to left or right
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       listItemTouchHelperAdapter.onDismissed(viewHolder.adapterPosition)
    }

}