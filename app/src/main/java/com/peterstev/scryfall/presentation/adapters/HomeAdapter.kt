package com.peterstev.scryfall.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.peterstev.safebodachallengepeterslight.R
import com.peterstev.scryfall.data.models.Data

@Suppress("SENSELESS_COMPARISON")
class HomeAdapter(
    private val itemList: MutableList<Data>,
    private val glide: RequestManager,
    private val listener: OnCardClickListener
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HomeViewHolder = HomeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
    )

    override fun getItemCount(): Int = itemList.size
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) =
        holder.bindViews(itemList[position], glide, listener)

    fun updateList(list: List<Data>?) {
        itemList.clear()
        itemList.addAll(list ?: emptyList())
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardImage: AppCompatImageView = itemView.findViewById(R.id.home_card_image)
        private val name: AppCompatTextView = itemView.findViewById(R.id.home_card_name)
        private val type: AppCompatTextView = itemView.findViewById(R.id.home_card_type)
        private val rarity: AppCompatTextView = itemView.findViewById(R.id.home_card_rarity)
        private val setName: AppCompatTextView = itemView.findViewById(R.id.home_card_set_name)

        fun bindViews(cardItem: Data, glide: RequestManager, listener: OnCardClickListener) {
            if (cardItem.imageUrl != null) {
                glide.load(cardItem.imageUrl.artCrop)
                    .placeholder(R.drawable.drawable_background).into(cardImage)
            } else glide.load(cardItem.cardFaces!![0].imageUris.artCrop)
                .placeholder(R.drawable.drawable_background).into(cardImage)

            name.text = cardItem.name.trim()
            type.text = cardItem.typeLine.trim()
            rarity.text = cardItem.rarity.trim()
            setName.text = cardItem.setName.trim()

            itemView.setOnClickListener {
                listener.onCardClick(cardItem)
            }
        }
    }

    interface OnCardClickListener {
        fun onCardClick(cardItem: Data)
    }

}