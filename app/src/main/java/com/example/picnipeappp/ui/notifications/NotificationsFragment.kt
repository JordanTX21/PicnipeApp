package com.example.picnipeappp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picnipeappp.databinding.FragmentNotificationsBinding
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.notifications.adapter.NotificationAdapter
import com.google.firebase.firestore.FirebaseFirestore

class NotificationsFragment : Fragment() {
    private var firestore = FirebaseFirestore.getInstance()
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

            firestore.collection("notifications").whereEqualTo("toUserId", UserSingleton.iduser)
                .get()
                .addOnSuccessListener { documents ->
                    val provider = NotificationProvider.notificationsList
                    provider.clear()
                    for (document in documents) {
                        provider.add(
                            Notification(
                                document.get("titulo").toString(),
                                document.get("contenido").toString(),
                                document.get("fromUserPhoto").toString(),
                                document.get("fromUseriD").toString(),
                                document.get("toUserId").toString(),
                                document.get("fromUserName").toString()
                            )
                        )
                    }
                    binding.progressBarNotifications.visibility = View.GONE
                    initRecyclerView()
                }
        })
        return root

    }

    fun initRecyclerView() {
        val recyclerview = binding.recyclerviewNotifications
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = NotificationAdapter(NotificationProvider.notificationsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}