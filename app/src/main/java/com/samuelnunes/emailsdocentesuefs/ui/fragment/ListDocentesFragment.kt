package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abed.myapplication.recyclerView.withSimpleAdapter
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.activity.CHAVE_DOCENTE_ID
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.ListDocentesFragmentViewModel
import kotlinx.android.synthetic.main.item_docente.*
import org.koin.android.viewmodel.ext.android.viewModel


class ListDocentesFragment : Fragment() {

    private val viewModel: ListDocentesFragmentViewModel by viewModel()

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
            controlador.navigate(R.id.adicionaDocenteFragment)
        }
    }

    private fun buscaDocentes(view: View) {

        viewModel.buscaTodos().observe(requireActivity(), Observer { resource ->
            val docentesEncontrados = resource.dado
            if (docentesEncontrados != null) {
                view.findViewById<RecyclerView>(R.id.recyclerView)
                    .withSimpleAdapter(
                        docentesEncontrados.toList(),
                        R.layout.item_docente,
                        { docente ->
                            val controlador = findNavController()
                            val dados = Bundle()
                            dados.putString(CHAVE_DOCENTE_ID, docente.id)
                            controlador.navigate(R.id.detalhesDocenteFragment, dados)
                        },
                        { docente ->
                            item_docente_name.text = docente.nome
                            item_docente_email.text = docente.email
                            docente_id.text = docente.id
                        }
                    )
            }
            if(resource.erro != null){
                Log.i("BuscaDocentes", "erro: ${resource.erro}")
            }
        })
    }
}