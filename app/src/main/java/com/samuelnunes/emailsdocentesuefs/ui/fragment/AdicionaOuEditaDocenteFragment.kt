package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.activity.CHAVE_DOCENTE_ID
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.AdicionaOuEditaDocenteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


@ExperimentalCoroutinesApi
class AdicionaOuEditaDocenteFragment : Fragment() {

    private val docenteID: String? by lazy {
        arguments?.getString(CHAVE_DOCENTE_ID)
    }
    private val viewModel: AdicionaOuEditaDocenteViewModel by viewModel { parametersOf(docenteID) }

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutDepartmento: TextInputLayout
    private lateinit var btnSave: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.adiciona_ou_edita_docente_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instanciaTextInputs(view)
        if (docenteID.isNullOrEmpty()) {
            textInputLayoutEmail.visibility = GONE
            btnSave.text = resources.getString(R.string.salvar)
        } else {
            textInputLayoutEmail.visibility = VISIBLE
            btnSave.text = resources.getString(R.string.atualizar)
            val docente = viewModel.docente
            if (docente != null) {
                textInputLayoutName.editText!!.setText(docente.name)
                textInputLayoutEmail.editText!!.setText(docente.email)
                textInputLayoutDepartmento.editText!!.setText(docente.departmentCode)
            }
        }
        createAutoCompleteDepartamentos()
    }

    private fun instanciaTextInputs(view: View) {
        textInputLayoutName = view.findViewById(R.id.input_name_layout)
        textInputLayoutEmail = view.findViewById(R.id.input_email_layout)
        textInputLayoutDepartmento = view.findViewById(R.id.input_department_layout)
        btnSave = view.findViewById(R.id.btn_save_docente)
    }

    private fun createAutoCompleteDepartamentos() {
        val departamentos = resources.getStringArray(R.array.departmentsCode)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_departament, departamentos)
        val departamentAutoComplete = textInputLayoutDepartmento.editText as AutoCompleteTextView
        departamentAutoComplete.setAdapter(adapter)
    }

}