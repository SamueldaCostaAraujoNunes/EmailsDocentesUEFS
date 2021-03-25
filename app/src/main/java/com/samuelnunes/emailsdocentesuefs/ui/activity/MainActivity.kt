package com.samuelnunes.emailsdocentesuefs.ui.activity

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.databinding.ActivityMainBinding
import com.samuelnunes.emailsdocentesuefs.ui.fragment.ListDocentesFragment
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


private const val TITLE_NAME = "Emails Docentes"
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        title = TITLE_NAME
    }

    private fun selectLayoutMenu(menu: Menu) {
        when (val currentFragment = getForegroundFragment()) {
            is ListDocentesFragment -> {
                createSearchBar(menu, currentFragment)
            }
            else -> menuInflater.inflate(R.menu.menu, menu)
        }
    }

    private fun getForegroundFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        selectLayoutMenu(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun createSearchBar(menu: Menu?, fragment: ListDocentesFragment) {
        menuInflater.inflate(R.menu.search_nav_menu, menu)
        val search = menu?.findItem(R.id.nav_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Nome do Docente"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    fragment.adapter.filter(newText)
                }
                return true
            }
        })
    }

}