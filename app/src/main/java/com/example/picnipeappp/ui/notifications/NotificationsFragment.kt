package com.example.picnipeappp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.FragmentNotificationsBinding
import com.example.picnipeappp.ui.notifications.adapter.NotificationAdapter
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


//        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
            initRecyclerView()
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initRecyclerView() {
        val recyclerview = binding.recyclerviewNotifications
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = NotificationAdapter(NotificationProvider.notificationsList)
    }
}