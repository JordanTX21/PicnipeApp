package com.example.picnipeappp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.FragmentNotificationsBinding
import io.getstream.avatarview.coil.loadImage

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val avatarView = binding.userAvatarView
        avatarView.loadImage("@drawable/logo.png")
        val avatarView1 = binding.userAvatarView1
        avatarView1.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-2.jpg")
        val avatarView2 = binding.userAvatarView2
        avatarView2.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-3.jpg")
        val avatarView3 = binding.userAvatarView3
        avatarView3.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-4.jpg")

//        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}