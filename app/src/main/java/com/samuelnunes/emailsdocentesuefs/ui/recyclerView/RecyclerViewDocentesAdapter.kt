package com.samuelnunes.emailsdocentesuefs.ui.recyclerView

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.model.Docente
import org.koin.java.KoinJavaComponent.inject


class RecyclerViewDocentesAdapter(private val docentes: List<Docente>) :
    RecyclerView.Adapter<RecyclerViewDocentesAdapter.ViewHolder>() {

    val clipboardManager: ClipboardManager? by inject(ClipboardManager::class.java)
    private var reallyListDocentes = docentes.toMutableList().sortedBy { it.name }

    @SuppressLint("DefaultLocale")
    fun filter(searchTerm: String) {
        val key = searchTerm.toLowerCase()
        reallyListDocentes = docentes.filter { docente ->
            val nome = docente.name?.toLowerCase()
            val comparacao = nome?.contains(key) ?: false
            comparacao
        }.toMutableList().sortedBy { it.name }
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDocenteName: TextView = view.findViewById(R.id.item_docente_name)
        val tvDocenteEmail: TextView = view.findViewById(R.id.item_docente_email)
        val tvDocenteDepartment: TextView = view.findViewById(R.id.item_docente_depatarment_code)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_docente, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val docente = reallyListDocentes[position]
        viewHolder.tvDocenteName.text = docente.name
        viewHolder.tvDocenteEmail.text = docente.email
        viewHolder.tvDocenteDepartment.text = docente.departmentCode
        viewHolder.itemView.setOnClickListener {
            val clip = ClipData.newPlainText("Canal de ClipData", docente.email)
            clipboardManager?.setPrimaryClip(clip)
            Snackbar.make(viewHolder.itemView, "${docente.email} copiado!", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount() = reallyListDocentes.size

}
