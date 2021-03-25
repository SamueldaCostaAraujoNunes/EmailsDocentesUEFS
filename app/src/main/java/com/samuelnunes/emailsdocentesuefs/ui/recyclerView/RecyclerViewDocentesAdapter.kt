package com.samuelnunes.emailsdocentesuefs.ui.recyclerView

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import com.samuelnunes.emailsdocentesuefs.databinding.ItemAdmobBinding
import com.samuelnunes.emailsdocentesuefs.databinding.ItemBtnNotFindDocenteBinding
import com.samuelnunes.emailsdocentesuefs.databinding.ItemDocenteBinding
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.ui.activity.CHAVE_DOCENTE_ID
import org.koin.java.KoinJavaComponent.inject

private const val ITEM_TYPE_DOCENTE = 0
private const val ITEM_TYPE_BANNER_AD = 1
private const val ITEM_TYPE_BTN_NOT_FIND_DOCENTE = 2
private const val ITEM_TYPE_OTHER = 3

abstract class RecyclerViewDocentesAdapter(private var docentes: List<Docente> = listOf()) :
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
            is Anuncio -> ITEM_TYPE_BANNER_AD
            is BtnNotFindDocente -> ITEM_TYPE_BTN_NOT_FIND_DOCENTE
            else -> ITEM_TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER_AD -> {
                val view = ItemAdmobBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                AdMobViewHolder(view)
            }
            ITEM_TYPE_DOCENTE -> {
                val view = ItemDocenteBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                DocenteViewHolder(view)
            }
            ITEM_TYPE_BTN_NOT_FIND_DOCENTE -> {
                val view = ItemBtnNotFindDocenteBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                BtnNaoEncontradoViewHolder(view)
            }
            else -> {
                val view = ItemDocenteBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                DocenteViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder) {
            is AdMobViewHolder -> {
                val adRequest: AdRequest = AdRequest.Builder().build()
                viewHolder.viewBinding.adView.loadAd(adRequest)
            }
            is DocenteViewHolder -> {
                val docente = this.reallyListDocentes[position] as Docente
                viewHolder.viewBinding.docente = docente
                viewHolder.itemView.setOnClickListener {
                    val clip = ClipData.newPlainText("Canal de ClipData", docente.email)
                    clipboardManager?.setPrimaryClip(clip)
                    Snackbar.make(
                        viewHolder.itemView,
                        "${docente.email} copiado!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                viewHolder.itemView.setOnLongClickListener {
                    val bundle = Bundle()
                    bundle.putString(CHAVE_DOCENTE_ID, docente.id)
                    notFoundDocente(bundle)
                    true
                }
            }
            is BtnNaoEncontradoViewHolder -> {
                val naoEncontrouODocente = "Não encontrou o docente?"
                viewHolder.viewBinding.itemTvNotFindDocente.text = naoEncontrouODocente
                viewHolder.itemView.setOnClickListener { notFoundDocente() }
            }
        }
    }

    override fun getItemCount() = this.reallyListDocentes.size

    abstract fun notFoundDocente(bundle: Bundle? = null)

    @SuppressLint("DefaultLocale")
    fun filter(searchTerm: String? = filterCache) {
        if (searchTerm != null || searchTerm != "") {
            val key = searchTerm!!.toLowerCase()
            reallyListDocentes = docentes.filter { docente ->
                val nome = docente.name?.toLowerCase()
                val comparacao = nome?.contains(key) ?: false
                comparacao
            }.toMutableList()
            verifyFindDocente()
            addAdMobBannerAds()
            notifyDataSetChanged()
        }
        filterCache = searchTerm
    }


    inner class Anuncio
    inner class BtnNotFindDocente

    private fun addAdMobBannerAds() {
        var count = 0
        while (count <= reallyListDocentes.size) {
            reallyListDocentes.add(count, Anuncio())
            count += (itemsPerAd + 1)
        }
    }

    private fun verifyFindDocente() {
        if (reallyListDocentes.size <= 5) {
            reallyListDocentes.add(BtnNotFindDocente())
        }
    }

    open inner class ViewHolder(open val viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    inner class DocenteViewHolder(override val viewBinding: ItemDocenteBinding) :
        ViewHolder(viewBinding)

    inner class AdMobViewHolder(override val viewBinding: ItemAdmobBinding) :
        ViewHolder(viewBinding)

    inner class BtnNaoEncontradoViewHolder(override val viewBinding: ItemBtnNotFindDocenteBinding) :
        ViewHolder(viewBinding)
}
