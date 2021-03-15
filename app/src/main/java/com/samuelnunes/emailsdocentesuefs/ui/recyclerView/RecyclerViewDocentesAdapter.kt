package com.samuelnunes.emailsdocentesuefs.ui.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.model.Docente


class RecyclerViewDocentesAdapter(private val docentes: List<Docente>) :
    RecyclerView.Adapter<RecyclerViewDocentesAdapter.ViewHolder>() {

    private var reallyListDocentes = docentes.toMutableList()

    @SuppressLint("DefaultLocale")
    fun filter(searchTerm: String) {
        val key = searchTerm.toLowerCase().capitalize()
        reallyListDocentes = docentes.filter { docente ->
            val nome = docente.name
            val comparacao = nome?.startsWith(key) ?: false
            comparacao
        }.toMutableList()
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
    }

    override fun getItemCount() = reallyListDocentes.size

}
