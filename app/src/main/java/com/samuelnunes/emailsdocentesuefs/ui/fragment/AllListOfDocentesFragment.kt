package com.samuelnunes.emailsdocentesuefs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.samuelnunes.emailsdocentesuefs.databinding.AllListOfDocentesFragmentBinding
import com.samuelnunes.emailsdocentesuefs.repository.REPOSITORY_ADMIN_EDITAR
import com.samuelnunes.emailsdocentesuefs.repository.REPOSITORY_ADMIN_SALVAR
import com.samuelnunes.emailsdocentesuefs.repository.REPOSITORY_PUBLIC
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class AllListOfDocentesFragment : Fragment() {

    private lateinit var binding: AllListOfDocentesFragmentBinding
    val fragmentList: List<ListDocentesFragment> by lazy {
        listOf<ListDocentesFragment>(
            ListDocentesFragment(REPOSITORY_PUBLIC),
            ListDocentesFragment(REPOSITORY_ADMIN_SALVAR),
            ListDocentesFragment(REPOSITORY_ADMIN_EDITAR)
        )
    }
    private lateinit var adapter: ViewPagerFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllListOfDocentesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun currentFragment(): ListDocentesFragment {
        val currentItem = binding.pager.currentItem
        return fragmentList[currentItem]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ViewPagerFragmentAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )
        binding.pager.adapter = adapter
        val titleTab = listOf("Aprovados", "Criados", "Editados")
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = titleTab[position]
        }.attach()
    }
}

@ExperimentalCoroutinesApi
class ViewPagerFragmentAdapter(
    private val fragmentList: List<ListDocentesFragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
