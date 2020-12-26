package com.ramitsuri.swiftly.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ramitsuri.swiftly.App
import com.ramitsuri.swiftly.data.Result
import com.ramitsuri.swiftly.databinding.FragmentSpecialsBinding
import com.ramitsuri.swiftly.entities.ManagerSpecialsResponse
import com.ramitsuri.swiftly.entities.SpecialsInfo
import com.ramitsuri.swiftly.ui.adapter.SpecialsAdapter
import com.ramitsuri.swiftly.viewmodel.SpecialsViewModel
import timber.log.Timber

class SpecialsFragment : BaseFragment() {
    private val defaultGridSpanCount = 16

    private var _binding: FragmentSpecialsBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: SpecialsViewModel
    private lateinit var adapter: SpecialsAdapter
    private lateinit var layoutManager: GridLayoutManager

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
        adapter = SpecialsAdapter(
            mutableListOf(),
            App.instance.injector.getCurrencyFormatter(),
            requireActivity()
        )
        adapter.onItemClick = {
            onManagerSpecialSelected(it)
        }
        layoutManager = GridLayoutManager(activity, defaultGridSpanCount)
        binding.listView.layoutManager = layoutManager
        binding.listView.adapter = adapter

        viewModel.getManagerSpecials().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    showProgress(true)
                }
                is Result.Success -> {
                    showProgress(false)
                    setupSpecialsList(result.data)
                }
                is Result.Error -> {
                    showProgress(false)
                    showError(result.message)
                }

            }
        })
    }

    private fun setupSpecialsList(managerSpecialsResponse: ManagerSpecialsResponse) {
        layoutManager.spanCount = managerSpecialsResponse.canvasUnit
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getItemWidthSpan(position)
            }
        }
        adapter.update(managerSpecialsResponse.specials, managerSpecialsResponse.canvasUnit)
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

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
    }
}