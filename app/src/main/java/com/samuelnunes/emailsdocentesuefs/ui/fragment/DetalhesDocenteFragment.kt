package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.activity.CHAVE_DOCENTE_ID
import com.samuelnunes.emailsdocentesuefs.ui.activity.ID_DOCENTE_INVALIDO
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.DetalhesDocenteViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesDocenteFragment : Fragment() {

    private val docenteID by lazy {
        arguments?.getString(CHAVE_DOCENTE_ID)
            ?: throw IllegalArgumentException(ID_DOCENTE_INVALIDO)
    }
    private val viewModel: DetalhesDocenteViewModel by viewModel { parametersOf(docenteID) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detalhes_docente_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.docente.observe(requireActivity(), Observer { docenteEncontrado ->
            view.findViewById<TextView>(R.id.detalhes_docente_fragment_docente_nome).text =
                docenteEncontrado.dado?.nome ?: "Não foi possivel encontrar um nome"
            view.findViewById<TextView>(R.id.detalhes_docente_fragment_docente_email).text =
                docenteEncontrado.dado?.email ?: "Não foi possivel encontrar um email"
        })
    }

}