package com.example.newscatcherapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newscatcherapp.R
import com.example.newscatcherapp.adapter.NewsAdapter
import com.example.newscatcherapp.adapter.TodaysNewsAdapter
import com.example.newscatcherapp.databinding.FragmentNewsBinding
import com.example.newscatcherapp.utils.hideKeyboard
import com.example.newscatcherapp.viewmodel.CardNewsViewModel
import com.example.newscatcherapp.viewmodel.NewsViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewsFragment : Fragment(R.layout.fragment_news) {


    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var todaysNewsAdapter: TodaysNewsAdapter

    private val newsViewModel: NewsViewModel by viewModels()
    private val cardViewModel: CardNewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setDateTime()
        setupNewsRecyclerView()
        setupCardRecyclerView()
        observeNewsValues()
        observeCardValues()
        setupLanguage()


        val searchText = binding.fragmentSearchEditText
        searchText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                newsViewModel.getNewsData(textView.text.toString(), newsViewModel.getLang()!!)
                searchText.hideKeyboard()

            }
            true
        }
    }

    private fun observeNewsValues() {
        newsViewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
    }

    private fun observeCardValues() {
        cardViewModel.getCardNews(lang = newsViewModel.getLang()!!)
        cardViewModel.cardNewsLiveData.observe(viewLifecycleOwner) {
            todaysNewsAdapter.submitList(it)
        }
    }

    //Avoiding Memory Leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupNewsRecyclerView() = binding.recyclerView.apply {
        newsAdapter = NewsAdapter { articleData ->
            findNavController().navigate(
                NewsFragmentDirections.actionGlobalExpandedNewsFragment(
                    articleData.toSaveArticle()
                )
            )
        }
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(activity)

    }

    private fun setupCardRecyclerView() = binding.cardRecyclerView.apply {
        todaysNewsAdapter = TodaysNewsAdapter { articleData ->
            findNavController().navigate(
                NewsFragmentDirections.actionGlobalExpandedNewsFragment(
                    articleData.toSaveArticle()
                )
            )
        }
        adapter = todaysNewsAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)




    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateTime() {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy")
        val dateTime = simpleDateFormat.format(calendar.time)
        binding.dateTextView.text = dateTime
    }

    private fun setupLanguage() {
        val langIcon = binding.languagePopUp
        langIcon.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.language_pop_up)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.en -> {
                        newsViewModel.setLang("en")
                        refresh()
                        true
                    }
                    R.id.ru -> {
                        newsViewModel.setLang("ru")
                        refresh()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()

        }
    }

    private fun refresh() {
        cardViewModel.cardNewsLiveData.value = emptyList()
        newsViewModel.newsLiveData.value = emptyList()
        activity?.recreate()
    }




}
