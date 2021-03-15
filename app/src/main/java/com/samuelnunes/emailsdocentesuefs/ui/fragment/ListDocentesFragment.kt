package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.repository.Resource
import com.samuelnunes.emailsdocentesuefs.ui.recyclerView.RecyclerViewDocentesAdapter
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.ListDocentesFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class ListDocentesFragment : Fragment() {

    private val viewModel: ListDocentesFragmentViewModel by viewModel()
    lateinit var adapter: RecyclerViewDocentesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_docentes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscaDocentes(view)
        createFab(view)
    }

    private fun createFab(view: View) {
        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            val controlador = findNavController()
            controlador.navigate(R.id.adicionaOuEditaDocenteFragment)
        }
    }

    private fun buscaDocentes(view: View) {

        viewModel.buscaTodos().observe(requireActivity(), Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    val docentesEncontrados = resource.data!!
                    adapter = RecyclerViewDocentesAdapter(docentesEncontrados.toList())
                    view.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
                }
                Resource.Status.ERROR -> Log.i(
                    "BuscaDocentes",
                    "erro: ${resource.getErrorMessage()}"
                )
                Resource.Status.LOADING -> Toast.makeText(
                    requireContext(),
                    "Loading!",
                    Toast.LENGTH_SHORT
                ).show()
                Resource.Status.CANCEL -> Toast.makeText(
                    requireContext(),
                    "Cancel!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}