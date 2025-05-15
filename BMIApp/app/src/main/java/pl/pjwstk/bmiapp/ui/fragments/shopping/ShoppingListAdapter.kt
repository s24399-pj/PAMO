package pl.pjwstk.bmiapp.ui.fragments.shopping

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.data.models.ShoppingItem

class ShoppingListAdapter(
    private var items: List<ShoppingItem>,
    private val onItemCheckedListener: (ShoppingItem, Boolean) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private val TAG = "ShoppingListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Log.d(
            TAG,
            "Binding item at position $position: ${item.name}, purchased: ${item.isPurchased}"
        )

        with(holder) {
            nameTextView.text = item.name
            quantityTextView.text = item.quantity
            purchasedCheckBox.isChecked = item.isPurchased

            updateItemAppearance(holder, item.isPurchased)

            purchasedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                Log.d(TAG, "Item ${item.name} checked state changed to: $isChecked")
                onItemCheckedListener(item, isChecked)
                updateItemAppearance(holder, isChecked)
            }
        }
    }

    private fun updateItemAppearance(holder: ViewHolder, isPurchased: Boolean) {
        with(holder) {
            if (isPurchased) {
                nameTextView.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    alpha = 0.6f
                }
                quantityTextView.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    alpha = 0.6f
                }
                (itemView as CardView).cardElevation = 1f
            } else {
                nameTextView.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    alpha = 1.0f
                }
                quantityTextView.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    alpha = 1.0f
                }
                (itemView as CardView).cardElevation = 3f
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ShoppingItem>) {
        Log.d(TAG, "Updating items, count: ${newItems.size}")
        items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val quantityTextView: TextView = itemView.findViewById(R.id.itemQuantityTextView)
        val purchasedCheckBox: CheckBox = itemView.findViewById(R.id.itemPurchasedCheckBox)
    }
}