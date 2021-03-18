package com.samuelnunes.emailsdocentesuefs.ui.recyclerView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdSize
import com.samuelnunes.emailsdocentesuefs.R
import com.samuelnunes.emailsdocentesuefs.ui.advertisement.AdmobAdvertisement

class ItemRecyclerAdMob(context: Context, attr: AttributeSet?, defStyleAttr: Int) : CardView(
    context,
    attr,
    defStyleAttr
) {
    private var admobAdvertisement: AdmobAdvertisement = AdmobAdvertisement(context)

    constructor(context: Context) : this(
        context,
        null
    )

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.style.Widget_MaterialComponents_CardView
    )

    private var advertisementGroup: RelativeLayout

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.banner_ad_row, this, true)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        advertisementGroup = view.findViewById(R.id.advertisementParent)
    }

    fun createBanner(idAdMob: Int, size: AdSize = AdmobAdvertisement.SMART_BANNER) {
        admobAdvertisement.createBanner(advertisementGroup, idAdMob, size)
    }

    fun loadBanner() {
        admobAdvertisement.loadBanner()
    }
}