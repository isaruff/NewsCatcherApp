package com.example.newscatcherapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newscatcherapp.R
import com.example.newscatcherapp.adapter.SaveWindowAdapter
import com.example.newscatcherapp.databinding.FragmentSaveWindowBinding
import com.example.newscatcherapp.model.ArticleData
import com.example.newscatcherapp.viewmodel.SaveWindowViewModel
import java.text.SimpleDateFormat
import java.util.*


class SaveWindowFragment : Fragment() {

    private var _binding: FragmentSaveWindowBinding? = null
    private val binding get() = _binding!!

    private lateinit var saveWindowNewsAdapter : SaveWindowAdapter
    private val saveWindowViewModel: SaveWindowViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSaveWindowBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSavedValues()
        setupSaveWindowRecyclerView()
        setDateTime()





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeSavedValues() {
        saveWindowViewModel.getSavedData()
        saveWindowViewModel.articleLiveData.observe(viewLifecycleOwner) {
            saveWindowNewsAdapter.submitList(it)
        }
    }
    private fun setupSaveWindowRecyclerView() = binding.saveRecyclerView.apply {
        saveWindowNewsAdapter = SaveWindowAdapter { savedArticle ->
            findNavController().navigate(
                NewsFragmentDirections.actionGlobalExpandedNewsFragment(savedArticle)
            )
        }
        adapter = saveWindowNewsAdapter
        layoutManager = LinearLayoutManager(activity)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateTime() {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy")
        val dateTime = simpleDateFormat.format(calendar.time)
        binding.dateTextView.text = dateTime
    }


}