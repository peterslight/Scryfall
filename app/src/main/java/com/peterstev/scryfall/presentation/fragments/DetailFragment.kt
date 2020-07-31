package com.peterstev.scryfall.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.peterstev.safebodachallengepeterslight.R
import com.peterstev.scryfall.presentation.viewmodels.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import org.json.JSONObject
import javax.inject.Inject

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: FragmentViewModel by activityViewModels()

    @Inject
    lateinit var glide: RequestManager
    private lateinit var cardImage: AppCompatImageView
    private lateinit var name: AppCompatTextView
    private lateinit var type: AppCompatTextView
    private lateinit var rarity: AppCompatTextView
    private lateinit var setName: AppCompatTextView
    private lateinit var date: AppCompatTextView
    private lateinit var manaCost: Chip
    private lateinit var cmc: Chip
    private lateinit var desc: AppCompatTextView
    private lateinit var legalities: ChipGroup


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews().also {
            mapViews()
        }
    }

    private fun setupViews() {
        cardImage = detail_card_image
        name = detail_card_name
        type = detail_card_type
        rarity = detail_card_rarity
        setName = detail_card_set_name
        date = detail_released_date
        manaCost = detail_chip_cost
        cmc = detail_chip_cmc
        desc = detail_tv_oracle_text
        legalities = detail_chip_group_legalities
    }

    private fun mapViews() {
        val data = viewModel.getSelectedCardItem()
        if (data.imageUrl != null) glide.load(data.imageUrl.artCrop)
            .placeholder(R.drawable.drawable_background).into(cardImage)
        else glide.load(data.cardFaces!![0].imageUris.artCrop)
            .placeholder(R.drawable.drawable_background).into(cardImage)

        name.text = data.name
        type.text = data.typeLine
        rarity.text = data.rarity
        setName.text = data.setName
        date.text = data.releasedAt
        manaCost.text = data.manaCost
        cmc.text = "${getString(R.string.cmc)} ${data.cmc}"
        desc.text =
            if (!data.oracleText.isNullOrEmpty()) data.oracleText else data.cardFaces!![0].oracleText

        Gson().toJson(data.legalities).let {
            JSONObject(it).also { json ->
                for (key in json.keys()) {
                    val chip = Chip(requireContext())
                    chip.text = "$key : ${json.getString(key)}"
                    legalities.addView(chip)
                }
            }
        }
    }
}