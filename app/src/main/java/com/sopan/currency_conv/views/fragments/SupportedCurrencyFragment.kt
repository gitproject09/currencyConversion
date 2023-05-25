package com.sopan.currency_conv.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sopan.currency_conv.R
import com.sopan.currency_conv.databinding.FragmentHomeBinding
import com.sopan.currency_conv.model.CurrencyRateToDisplay
import com.sopan.currency_conv.util.TAG
import com.sopan.currency_conv.viewmodel.DataViewModel
import com.sopan.currency_conv.views.adapter.SupportedCurrencyAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SupportedCurrencyFragment : Fragment() {

    private val dataViewModel by viewModel<DataViewModel>()
    private lateinit var mViewDataBinding: FragmentHomeBinding
    private val countriesAdapter: SupportedCurrencyAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setView()
        removeBackButton()
        mViewDataBinding.viewModel = dataViewModel
        dataViewModel.getAllSupportedCurrencies()
        dataViewModel.countriesList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "@@currencyRateList ${it.size.toString()}")
            if (it.isNotEmpty() && it != null) {
                countriesAdapter.setSupportedCurrencies(it)
                dataViewModel.getAllCurrencyRates()

            }
        })
        dataViewModel.currencyRateList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "@@currencyRateList ${it.size.toString()}")
            if (it.isNotEmpty() && it != null) {
                countriesAdapter.setCurrencyRateList(it)
                setSpinner(it)
            }
        })

        dataViewModel.mCurrentCurrency.observe(viewLifecycleOwner, Observer {
            countriesAdapter.notifyDataSetChanged()
        })

    }

    private fun removeBackButton() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(false)
    }

    private fun setView() {
        //  countriesAdapter = SupportedCurrencyAdapter(context, this)
        countriesAdapter.mViewModel = dataViewModel
        mViewDataBinding.rvCountries.layoutManager = GridLayoutManager(
            activity, 3
        )
        mViewDataBinding.rvCountries.adapter = countriesAdapter
        mViewDataBinding.rvCountries.isNestedScrollingEnabled = false

        //setSpinner(null)
    }

    private fun setSpinner(list: List<CurrencyRateToDisplay>) {
        val adapter: ArrayAdapter<CurrencyRateToDisplay> = ArrayAdapter<CurrencyRateToDisplay>(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mViewDataBinding.spCurrencies.adapter = adapter
        mViewDataBinding.etMultiplier.placeCursorToEnd()
    }


    fun EditText.placeCursorToEnd() {
        this.setSelection(this.text.length)
    }


}
