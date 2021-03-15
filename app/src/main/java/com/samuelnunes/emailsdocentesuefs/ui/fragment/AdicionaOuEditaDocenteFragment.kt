package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.activity.CHAVE_DOCENTE_ID
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.AdicionaOuEditaDocenteViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AdicionaOuEditaDocenteFragment : Fragment() {

    private val docenteID: String? by lazy {
        arguments?.getString(CHAVE_DOCENTE_ID)
    }

    private val viewModel: AdicionaOuEditaDocenteViewModel by viewModel{ parametersOf(docenteID) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.adiciona_ou_edita_docente_fragment, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<Button>(R.id.btn_signup).setOnClickListener {
//            val nome = view.findViewById<TextView>(R.id.input_name).text.toString()
//            val email = view.findViewById<TextView>(R.id.input_email).text.toString()
//            val docente = Docente(nome = nome, email = email)
//            viewModel.addDocente(docente)
//        }
//    }
}