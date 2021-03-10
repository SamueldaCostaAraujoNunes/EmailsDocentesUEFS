package com.samuelnunes.emailsdocentesuefs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.abed.myapplication.recyclerView.withSimpleAdapter
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_docente.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Docentes"
        buscaDocentes()
    }

    private fun buscaDocentes() {
        viewModel.buscaTodos().observe(this) { resource ->
            val docentesEncontrados = resource.dado
            if(docentesEncontrados != null) {
                findViewById<RecyclerView>(R.id.recyclerView)
                    .withSimpleAdapter(
                        docentesEncontrados.toList(),
                        R.layout.item_docente
                    ) { docente ->
                        docente_name.text = docente.nome
                        docente_email.text = docente.email
                        docente_id.text = docente.id
                    }

            }else{
                Log.i("BuscaDocentes", "erro: ${resource.erro}")
            }
        }
    }
}