package com.example.picnipeappp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.picnipeappp.data.adapters.ViewPagerAdapter
import com.example.picnipeappp.data.model.LoggedInUser
import com.example.picnipeappp.databinding.FragmentDashboardBinding
import com.example.picnipeappp.ui.components.SavedFragment
import com.example.picnipeappp.ui.components.UploadsFragment
import com.example.picnipeappp.ui.login.UserSingleton
import com.google.android.material.tabs.TabLayoutMediator
import io.getstream.avatarview.coil.loadImage
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var viewPager: ViewPager2

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var loggedInUser:  LoggedInUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val avatarView = binding.userAvatarView


        avatarView.loadImage(UserSingleton.photoPerfil)


        binding.userName.text = UserSingleton.name
        binding.descriptionUser.text = UserSingleton.descripcion


        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPageViewer()
    }
    fun initPageViewer(){
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(UploadsFragment(),"Mis publicaciones")
        adapter.addFragment(SavedFragment(),"Favoritos")
        viewpager_profile.adapter = adapter
        TabLayoutMediator(tab_layout_profile, viewpager_profile) { tab, position ->
            tab.text = adapter.getFragmentTitle(position)
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}