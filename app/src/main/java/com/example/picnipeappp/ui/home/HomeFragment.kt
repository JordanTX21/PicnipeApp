package com.example.picnipeappp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.FragmentHomeBinding
import io.getstream.avatarview.coil.loadImage


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var postItem = ArrayList<PostItem>()
    private  lateinit var rvPost: RecyclerView
    private lateinit var adapter: PostAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//          val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//          textView.text = it
        })

        rvPost = binding.rvPost
        rvPost.layoutManager = StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL )
        adapter = PostAdapter(requireContext(), postItem)

        rvPost.adapter = adapter

        /*val imagen1 = PostItem(R.drawable.des
        postItem.add(PostItem(imagen1)


        val avatarView1 = binding.userAvatarView
        avatarView1.loadImage("https://static.wikia.nocookie.net/chainsaw-man/images/9/9e/Monkima.jpg/revision/latest?cb=20210627024327&path-prefix=es")
        val avatarView2 = binding.userAvatarView2
        avatarView2.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-3.jpg")
        val avatarView3 = binding.userAvatarView3
        avatarView3.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-4.jpg")
        val avatarView4 = binding.userAvatarView4
        avatarView4.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-4.jpg")
        val avatarView5 = binding.userAvatarView5
        avatarView5.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-4.jpg")
        val avatarView6 = binding.userAvatarView6
        avatarView6.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-4.jpg")
        val avatarView7 = binding.userAvatarView7
        avatarView7.loadImage("https://demos.creative-tim.com/argon-dashboard-pro/assets/img/team-4.jpg")*/

        galeriaImagenes()

        return root;
    }

    private fun galeriaImagenes(){

        val imagen1 = PostItem(R.drawable.madrid)
        val imagen2 = PostItem(R.drawable.komi)
        val imagen3 = PostItem(R.drawable.makima)
        val imagen4 = PostItem(R.drawable.descarga)
        val imagen5 = PostItem(R.drawable.bbunny)
        val imagen6 = PostItem(R.drawable.chemso)
        val imagen7 = PostItem(R.drawable.comida)
        val imagen8 = PostItem(R.drawable.madrid)
        val imagen9 = PostItem(R.drawable.madrid)
        val imagen10 = PostItem(R.drawable.madrid)


        postItem.add(imagen1)
        postItem.add(imagen2)
        postItem.add(imagen3)
        postItem.add(imagen4)
        postItem.add(imagen5)
        postItem.add(imagen6)
        postItem.add(imagen7)
        postItem.add(imagen8)
        postItem.add(imagen9)
        postItem.add(imagen10)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}