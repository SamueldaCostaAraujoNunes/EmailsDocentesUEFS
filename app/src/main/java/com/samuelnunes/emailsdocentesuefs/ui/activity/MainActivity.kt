package com.samuelnunes.emailsdocentesuefs.ui.activity

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.fragment.ListDocentesFragment
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


private const val TITLE_NAME = "Docentes"

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()
//    private var adapter: RecyclerViewDocentesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = TITLE_NAME
    }

    fun getForegroundFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_nav_menu, menu)
        val search = menu?.findItem(R.id.nav_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Nome do Docente"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val currentFragment = getForegroundFragment()
                    if (currentFragment is ListDocentesFragment) {
                        currentFragment.adapter.filter(newText)
                    }
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}