package com.example.testapplication.dictionary

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.R
import com.example.testapplication.core.exception.AppException
import com.example.testapplication.core.extensions.context.gone
import com.example.testapplication.core.extensions.context.observe
import com.example.testapplication.core.extensions.context.visible
import com.example.testapplication.core.platform.BaseActivity
import com.example.testapplication.dictionary.model.UiState
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_dictionary.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class DictionaryActivity : BaseActivity<DictionaryViewModel>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val dictionaryViewModel: DictionaryViewModel by viewModel()

    override fun getViewModel(): DictionaryViewModel {
        return dictionaryViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        setViews()
        attachObservers()
    }

    private fun attachObservers() {
        rvSuggestions.layoutManager = LinearLayoutManager(this@DictionaryActivity)
        observe(dictionaryViewModel.resultLiveData) { listItems ->
            when (listItems) {
                is UiState.Success -> {
                    rvSuggestions.visible()
                    groupError.gone()
                    progress.gone()
                    rvSuggestions.adapter = ResultAdapter().apply {
                        this.submitList(listItems.result)
                    }
                }
                is UiState.Error -> {
                    rvSuggestions.gone()
                    groupError.visible()
                    progress.gone()
                    when (listItems.error) {
                        is AppException.HttpError -> {
                            tvError.text = getString(R.string.general_error)
                        }
                        is AppException.NetworkError -> {
                            tvError.text = getString(R.string.general_network_error)
                        }
                    }
                }
                UiState.Loading -> {
                    rvSuggestions.gone()
                    groupError.gone()
                    progress.visible()
                    tvError.setText(R.string.empty_result)
                }
            }
        }
    }

    private fun setViews() {
        etSearch.afterTextChangeEvents()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                dictionaryViewModel.getSuggestions(etSearch.text.toString())
            }.apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSortUp -> {
                dictionaryViewModel.orderResult(ascending = false)
                true
            }
            R.id.actionSortDown -> {
                dictionaryViewModel.orderResult(ascending = true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onPause() {
        compositeDisposable.clear()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
