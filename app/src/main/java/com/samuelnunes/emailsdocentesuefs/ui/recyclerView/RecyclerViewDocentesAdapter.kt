package com.samuelnunes.emailsdocentesuefs.ui.recyclerView

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdSize
import com.google.android.material.snackbar.Snackbar
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.model.Docente
import org.koin.java.KoinJavaComponent.inject

private const val ITEM_TYPE_DOCENTE = 0
private const val ITEM_TYPE_BANNER_AD = 1
private const val ITEM_TYPE_OTHER = 2

class RecyclerViewDocentesAdapter(private var docentes: List<Docente> = listOf()) :
    RecyclerView.Adapter<RecyclerViewDocentesAdapter.ViewHolder>() {

    private val clipboardManager: ClipboardManager? by inject(ClipboardManager::class.java)
    private var reallyListDocentes: MutableList<Any> = docentes.toMutableList()
    private val itemsPerAd = 13
    private var filterCache = ""

    var data: List<Docente>
        get() = docentes
        set(value) {
            docentes = value
            filter()
        }

    init {
        addAdMobBannerAds()
    }

    override fun getItemViewType(position: Int): Int {
        return when (reallyListDocentes[position]) {
            is Docente -> ITEM_TYPE_DOCENTE
            is String -> ITEM_TYPE_BANNER_AD
            else -> ITEM_TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER_AD -> {
                AdMobViewHolder(ItemRecyclerAdMob(viewGroup.context))
            }
            ITEM_TYPE_DOCENTE -> {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_docente, viewGroup, false)
                DocenteViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_docente, viewGroup, false)
                ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder) {
            is AdMobViewHolder -> {
                (viewHolder.itemView as ItemRecyclerAdMob).loadBanner()
            }
            is DocenteViewHolder -> {
                val docente = this.reallyListDocentes[position] as Docente
                viewHolder.tvDocenteName.text = docente.name
                viewHolder.tvDocenteEmail.text = docente.email
                viewHolder.tvDocenteDepartment.text = docente.departmentCode
                viewHolder.itemView.setOnClickListener {
                    val clip = ClipData.newPlainText("Canal de ClipData", docente.email)
                    clipboardManager?.setPrimaryClip(clip)
                    Snackbar.make(
                        viewHolder.itemView,
                        "${docente.email} copiado!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun getItemCount() = this.reallyListDocentes.size

    private fun addAdMobBannerAds() {
        var count = 0
        while (count <= reallyListDocentes.size) {
            reallyListDocentes.add(
                count,
                "banner"
            )
            count += (itemsPerAd + 1)
        }
    }

    @SuppressLint("DefaultLocale")
    fun filter(searchTerm: String? = filterCache) {
        if (searchTerm != null || searchTerm != "") {
            val key = searchTerm!!.toLowerCase()
            reallyListDocentes = docentes.filter { docente ->
                val nome = docente.name?.toLowerCase()
                val comparacao = nome?.contains(key) ?: false
                comparacao
            }.toMutableList()
            addAdMobBannerAds()
            notifyDataSetChanged()
        }
        filterCache = searchTerm
    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class DocenteViewHolder(view: View) : ViewHolder(view) {
        var tvDocenteName: TextView = view.findViewById(R.id.item_docente_name)
        var tvDocenteEmail: TextView = view.findViewById(R.id.item_docente_email)
        var tvDocenteDepartment: TextView = view.findViewById(R.id.item_docente_depatarment_code)
    }

    inner class AdMobViewHolder(view: ItemRecyclerAdMob) : ViewHolder(view) {
        init {
            view.createBanner(R.string.admob_banner_id, AdSize.FULL_BANNER)
        }
    }
}
