package com.example.newscatcherapp.fragments

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.newscatcherapp.R
import com.example.newscatcherapp.databinding.FragmentExpandedNewsBinding
import com.example.newscatcherapp.viewmodel.ExpandedNewsViewModel
import timber.log.Timber
import java.lang.Exception


class ExpandedNewsFragment : Fragment(R.layout.fragment_expanded_news) {

    private var _binding: FragmentExpandedNewsBinding?= null
    private val binding get() = _binding!!

    private val args: ExpandedNewsFragmentArgs by navArgs()
    private val expandedNewsViewModel: ExpandedNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpandedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initializing Functions
        setupBackButton()
        setUpNews()
        savePopUpMenu()
    }

    private fun setupBackButton() {
        val backButton = binding.backButton
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpNews() {
        binding.apply {
            newsImageExtended.load(args.model.media)
            args.model.run {
                topicFragmentText.text = topic
                titleFragmentText.text = title
                fragmentSummaryText.text = summary
                fragmentExcerptText.text = excerpt
                authorFragmentText.text = author
            }
            fragmentReadMoreLink.setOnClickListener {
                try {
                    val openLink = Intent(Intent.ACTION_VIEW, Uri.parse(args.model.link))
                    startActivity(openLink)
                } catch (_: ActivityNotFoundException) {
                    Toast.makeText(context, "No Browser Found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveOrDelete() {
        args.model.link?.let { expandedNewsViewModel.checkAlreadyExist(it) }
        expandedNewsViewModel.stateSaved.observe(viewLifecycleOwner) {state ->
            if (!state){
                expandedNewsViewModel.insertIntoDatabase(args.model)
                Toast.makeText(context, "Article Saved", Toast.LENGTH_SHORT).show()
            } else {
                args.model.link?.let { expandedNewsViewModel.deleteSavedArticle(it) }
                Toast.makeText(context, "Article Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun savePopUpMenu() {
        val popUpImage = binding.popUpMenu
        popUpImage.setOnClickListener { it ->
            val popUpMenu = PopupMenu(context, it)
            popUpMenu.inflate(R.menu.pop_up_save)
            popUpMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.share -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Link of the news: ${args.model.link}")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                        true
                    }
                    R.id.save -> {
                        saveOrDelete()
                        true
                    }
                    else -> false
                }
            }


            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible =true
                val mPopup = fieldMPopup.get(popUpMenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e:Exception) {
                Timber.tag("PopUpMenu Error").e(e, "Error Showing Menu Icons")
            } finally {
                popUpMenu.show()
            }

        }

    }


}