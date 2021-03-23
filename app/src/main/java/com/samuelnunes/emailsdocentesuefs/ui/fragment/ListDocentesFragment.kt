package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.recyclerView.RecyclerViewDocentesAdapter
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.ListDocentesFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ListDocentesFragment : Fragment() {

    private val viewModel: ListDocentesFragmentViewModel by viewModel()
    lateinit var adapter: RecyclerViewDocentesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_docentes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscaDocentes(view)
    }

    private fun buscaDocentes(view: View) {
        adapter = object : RecyclerViewDocentesAdapter() {
            override fun notFoundDocente(bundle: Bundle?) {
                val controlador = findNavController()
                controlador.navigate(R.id.adicionaOuEditaDocenteFragment, bundle)
            }
        }
        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        viewModel.buscaTodos().observe(requireActivity(), Observer { data ->
            adapter.data = data
        })
    }
}