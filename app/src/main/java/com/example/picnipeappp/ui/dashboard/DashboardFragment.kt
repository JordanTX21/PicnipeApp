package com.example.picnipeappp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager.widget.ViewPager
import com.example.picnipeappp.R
import com.example.picnipeappp.data.adapters.ViewPagerAdapter
import com.example.picnipeappp.data.model.LoggedInUser
import com.example.picnipeappp.databinding.FragmentDashboardBinding
import com.example.picnipeappp.ui.components.SavedFragment
import com.example.picnipeappp.ui.components.UploadsFragment
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.login.usernameGlobal
import com.google.android.material.tabs.TabLayoutMediator
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

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
        avatarView.loadImage("https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg")

        var nombreUsuario = usernameGlobal()

        binding.userName.text = UserSingleton.username

        val viewPager = binding.viewpagerProfile
        val tabLayout = binding.tabLayoutProfile


        val adapter = ViewPagerAdapter((activity as FragmentActivity).supportFragmentManager)
        adapter.addFragment(UploadsFragment(),"Subidos")
        adapter.addFragment(SavedFragment(),"Guardados")
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}