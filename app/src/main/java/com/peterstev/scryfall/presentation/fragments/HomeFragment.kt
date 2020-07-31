package com.peterstev.scryfall.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.peterstev.safebodachallengepeterslight.R
import com.peterstev.scryfall.presentation.adapters.HomeAdapter
import com.peterstev.scryfall.data.models.Data
import com.peterstev.scryfall.data.response.Status
import com.peterstev.scryfall.presentation.viewmodels.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.OnCardClickListener {

    private lateinit var navController: NavController
    private val fragmentViewModel: FragmentViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter
    private lateinit var progress: ProgressBar

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        adapter = HomeAdapter(mutableListOf(), glide, this)
        recyclerView.adapter = adapter
        progress = home_progress

        val etSearch = home_et_search
        val btnSearch = home_img_search_btn
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isNotEmpty() && query.isNotBlank())
                fragmentViewModel.fetchData(query)
            else Toast.makeText(requireContext(), "enter a text to search", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupObserver() {
        fragmentViewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    refreshList(it.data!!)
                    Timber.i(it.message)
                    progress.visibility = View.GONE
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                        .show()
                    Timber.i(it.message)
                }
            }
        })
    }

    private fun refreshList(data: List<Data>) {
        adapter.updateList(data)
    }

    override fun onCardClick(cardItem: Data) {
        fragmentViewModel.setSelectedCardItem(cardItem)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragment4ToDetailFragment4())
    }
}