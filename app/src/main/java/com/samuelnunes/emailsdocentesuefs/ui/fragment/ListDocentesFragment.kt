package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.databinding.FragmentListDocentesBinding
import com.samuelnunes.emailsdocentesuefs.repository.REPOSITORY
import com.samuelnunes.emailsdocentesuefs.repository.REPOSITORY_PUBLIC
import com.samuelnunes.emailsdocentesuefs.ui.recyclerView.RecyclerViewDocentesAdapter
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.ListDocentesFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoroutinesApi
class ListDocentesFragment(private val typeList: String = REPOSITORY_PUBLIC) : Fragment() {
    private lateinit var binding: FragmentListDocentesBinding

    private val viewModel: ListDocentesFragmentViewModel by viewModel {
        parametersOf(
            selectRepository(typeList)
        )
    }
    lateinit var adapter: RecyclerViewDocentesAdapter

    fun filter(text: String?) {
        adapter.filter(text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListDocentesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscaDocentes()
    }

    private fun buscaDocentes() {
        adapter = object : RecyclerViewDocentesAdapter() {
            override fun notFoundDocente(bundle: Bundle?) {
                val controlador = findNavController()
                bundle?.putString(REPOSITORY, typeList)
                controlador.navigate(R.id.adicionaOuEditaDocenteFragment, bundle)
            }
        }
        binding.recyclerView.adapter = adapter
        viewModel.buscaTodos().observe(requireActivity(), Observer { data ->
            adapter.data = data
        })
    }
}