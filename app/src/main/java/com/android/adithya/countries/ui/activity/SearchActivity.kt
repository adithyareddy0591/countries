package com.android.adithya.countries.ui.activity

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.adithya.countries.response.CountryResponse
import com.android.adithya.countries.ui.adapter.CountryAdapter
import com.android.adithya.countries.viewmodel.SearchViewModel
import com.android.adithya.countries.viewmodel.SearchViewModelFactory
import com.android.countries.R
import com.rxjava2.android.samples.utils.getQueryTextChangeObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = this.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }

        searchCountry.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchCountry.setIconifiedByDefault(false)

        val searchEditText: EditText = searchCountry.findViewById(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        searchEditText.setHintTextColor(ContextCompat.getColor(this, android.R.color.white))
        val myCustomFont: Typeface? =
            ResourcesCompat.getFont(applicationContext, R.font.noto_sans_bold)
        searchEditText.typeface = myCustomFont

        searchView = findViewById(R.id.searchCountry)
        searchViewModel = ViewModelProviders.of(this,
            SearchViewModelFactory()
        ).get(
            SearchViewModel::class.java
        )

        setUpSearchObservable()

        val adapter = CountryAdapter()
        searchRecyclerview.adapter = adapter

        searchViewModel.apiCountryData().observe(this, Observer {
            adapter.countries = it as ArrayList<CountryResponse>
        })
    }

    @SuppressLint("CheckResult")
    fun setUpSearchObservable() {
        searchView.getQueryTextChangeObservable()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.length > 1 }
            .distinctUntilChanged()
            .switchMap { query ->
                Observable.just(query)
                    .doOnError {
                        // handle error
                    }
                    .onErrorReturn { "" }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                searchViewModel.fetchCountrysList(result)
            }
    }


}
