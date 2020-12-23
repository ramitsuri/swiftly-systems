package com.ramitsuri.swiftly.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramitsuri.swiftly.App
import com.ramitsuri.swiftly.data.Result
import com.ramitsuri.swiftly.databinding.FragmentSpecialsBinding
import com.ramitsuri.swiftly.entities.SpecialsInfo
import com.ramitsuri.swiftly.ui.adapter.SpecialsAdapter
import com.ramitsuri.swiftly.viewmodel.SpecialsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SpecialsFragment : BaseFragment() {
    private var _binding: FragmentSpecialsBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: SpecialsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("Create")
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        val factory = App.instance.injector.getSpecialsViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SpecialsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("CreateView")
        _binding = FragmentSpecialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val adapter = SpecialsAdapter(
            mutableListOf()
        )
        adapter.onItemClick = {
            onManagerSpecialSelected(it)
        }
        binding.listView.layoutManager = LinearLayoutManager(activity)
        binding.listView.adapter = adapter


        lifecycleScope.launch {
            viewModel.getManagerSpecials().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        showProgress(true)
                    }
                    is Result.Success -> {
                        showProgress(false)
                        adapter.update(result.data.specials)
                    }
                    is Result.Error -> {
                        showProgress(false)
                        showError(result.message)
                    }

                }
            }
        }
    }

    private fun onManagerSpecialSelected(info: SpecialsInfo) {
        Toast.makeText(requireActivity(), info.displayName, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        val fragment = ErrorFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(ErrorFragment.MESSAGE, message)
        fragment.arguments = bundle
        activity?.supportFragmentManager?.let { supportFragmentManager ->
            fragment.show(supportFragmentManager, ErrorFragment.TAG)
        }
    }
}