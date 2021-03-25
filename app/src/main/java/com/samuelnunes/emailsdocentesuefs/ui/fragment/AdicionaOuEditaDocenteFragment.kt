package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.databinding.AdicionaOuEditaDocenteFragmentBinding
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
    private lateinit var binding: AdicionaOuEditaDocenteFragmentBinding
    private val viewModel: AdicionaOuEditaDocenteViewModel by viewModel { parametersOf(docenteID) }

    private lateinit var docenteCurrent: Docente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AdicionaOuEditaDocenteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textFieldFiller()
        createAutoCompleteDepartamentos()
        createClickListener()
    }

    private fun textFieldFiller() {
        docenteCurrent = viewModel.docente
        binding.docente = docenteCurrent
        if (docenteCurrent.id.isNullOrEmpty()) {
            binding.inputEmailLayout.visibility = GONE
            binding.btnSaveDocente.text = resources.getString(R.string.salvar)
        } else {
            binding.inputEmailLayout.visibility = VISIBLE
            binding.btnSaveDocente.text = resources.getString(R.string.atualizar)
        }
    }

    private fun createClickListener() {
        binding.btnSaveDocente.setOnClickListener { confirmInput() }
    }

    private fun confirmInput() {
        if (validateName() && validateDepartamento()) {
            binding.docente!!.departmentName =
                Departamento.departamentoHashMap[binding.docente!!.departmentCode]
            if (binding.inputEmailLayout.isVisible) {
                if (validateEmail()) {
                    viewModel.createDocente(binding.docente!!)
                } else {
                    return
                }
            }
            viewModel.createDocente(binding.docente!!)
            findNavController().popBackStack()
        } else {
            return
        }

    }

    private fun validateEmail(): Boolean {
        val emailInput: String = binding.inputEmail.text.toString().trim()
        return when {
            emailInput.isEmpty() -> {
                binding.inputEmail.error = "Campo Vazio!!"
                false
            }
            !emailInput.isValidEmail() -> {
                binding.inputEmail.error = "Email Inválido!!"
                false
            }
            else -> {
                binding.inputEmail.error = null
                true
            }
        }
    }

    private fun validateName(): Boolean {
        val usernameInput: String = binding.inputName.text.toString().trim()
        return if (usernameInput.isEmpty()) {
            binding.inputNameLayout.error = "Campo Vazio!!"
            false
        } else {
            binding.inputNameLayout.error = null
            true
        }
    }

    private fun validateDepartamento(): Boolean {
        val departamentoInput: String = binding.inputDepartment.text.toString().trim()
        return if (departamentoInput.isEmpty()) {
            binding.inputDepartmentLayout.error = "Campo Vazio!!"
            false
        } else {
            binding.inputDepartmentLayout.error = null
            true
        }
    }

    private fun createAutoCompleteDepartamentos() {
        val departamentos: List<String> = Departamento.departamentoHashMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_departament, departamentos)
        binding.inputDepartment.setAdapter(adapter)
    }

}