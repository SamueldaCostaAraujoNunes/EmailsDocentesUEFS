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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.extensions.isValidEmail
import com.samuelnunes.emailsdocentesuefs.model.Departamento
import com.samuelnunes.emailsdocentesuefs.model.Docente
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
    private lateinit var docenteCurrent: Docente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.adiciona_ou_edita_docente_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instanciaTextInputs(view)
        docenteCurrent = viewModel.docente
        if (docenteCurrent.id.isNullOrEmpty()) {
            textInputLayoutEmail.visibility = GONE
            btnSave.text = resources.getString(R.string.salvar)
        } else {
            textInputLayoutEmail.visibility = VISIBLE
            btnSave.text = resources.getString(R.string.atualizar)
        }
        textInputLayoutName.editText!!.setText(docenteCurrent.name)
        textInputLayoutEmail.editText!!.setText(docenteCurrent.email)
        textInputLayoutDepartmento.editText!!.setText(docenteCurrent.departmentCode)
        createAutoCompleteDepartamentos()
        createClickListener()
    }

    private fun createClickListener() {
        btnSave.setOnClickListener { confirmInput() }
    }

    private fun confirmInput() {
        if (validateName() && validateDepartamento()) {
            val name = textInputLayoutName.editText!!.text.toString()
            val departmentCode = textInputLayoutDepartmento.editText!!.text.toString()
            val departmentName = Departamento.departamentoHashMap[departmentCode]
            val id = docenteID
            var email: String? = null
            if (textInputLayoutEmail.isVisible) {
                if (validateEmail()) {
                    email = textInputLayoutEmail.editText!!.text.toString()
                } else {
                    return
                }
            }
            val docente = Docente(name, email, departmentCode, departmentName, id)

            if (docente != docenteCurrent) {
                viewModel.createDocente(docente)
            }
            findNavController().popBackStack()
        } else {
            return
        }

    }

    private fun validateEmail(): Boolean {
        val emailInput: String = textInputLayoutEmail.editText!!.text.toString().trim()
        return when {
            emailInput.isEmpty() -> {
                textInputLayoutEmail.error = "Campo Vazio!!"
                false
            }
            !emailInput.isValidEmail() -> {
                textInputLayoutEmail.error = "Email Inválido!!"
                false
            }
            else -> {
                textInputLayoutEmail.error = null
                true
            }
        }
    }

    private fun validateName(): Boolean {
        val usernameInput: String = textInputLayoutName.editText!!.text.toString().trim()
        return if (usernameInput.isEmpty()) {
            textInputLayoutName.error = "Campo Vazio!!"
            false
        } else {
            textInputLayoutName.error = null
            true
        }
    }

    private fun validateDepartamento(): Boolean {
        val departamentoInput: String = textInputLayoutDepartmento.editText!!.text.toString().trim()
        return if (departamentoInput.isEmpty()) {
            textInputLayoutDepartmento.error = "Campo Vazio!!"
            false
        } else {
            textInputLayoutDepartmento.error = null
            true
        }
    }


    private fun instanciaTextInputs(view: View) {
        textInputLayoutName = view.findViewById(R.id.input_name_layout)
        textInputLayoutEmail = view.findViewById(R.id.input_email_layout)
        textInputLayoutDepartmento = view.findViewById(R.id.input_department_layout)
        btnSave = view.findViewById(R.id.btn_save_docente)
    }

    private fun createAutoCompleteDepartamentos() {
        val departamentos: List<String> = Departamento.departamentoHashMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_departament, departamentos)
        val departamentAutoComplete = textInputLayoutDepartmento.editText as AutoCompleteTextView
        departamentAutoComplete.setAdapter(adapter)
    }

}