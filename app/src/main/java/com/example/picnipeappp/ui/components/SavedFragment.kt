package com.example.picnipeappp.ui.components

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.picnipeappp.databinding.FragmentSavedBinding
import com.example.picnipeappp.ui.dashboard.components.saveds.SavedAdapter
import com.example.picnipeappp.ui.post.Post
import com.example.picnipeappp.ui.post.PostActivity

class SavedFragment : Fragment() {
    private var _binding:FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val root:View = binding.root
        initRecycleView()
        return root
    }

    fun initRecycleView(){
        val postList = ArrayList<Post>()
        postList.add(
            Post(
                "id2",
                "youyouyopi",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "una imagen",
                "otra cosa"
            )
        )
        postList.add(
            Post(
                "id2",
                "youyouyopi",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "una imagen",
                "otra cosa"
            )
        )
        postList.add(
            Post(
                "id2",
                "youyouyopi",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "una imagen",
                "otra cosa"
            )
        )
        postList.add(
            Post(
                "id2",
                "youyouyopi",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "una imagen",
                "otra cosa"
            )
        )
        postList.add(
            Post(
                "id2",
                "youyouyopi",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "una imagen",
                "otra cosa"
            )
        )
        val recycleView = binding.recyclerviewSaves
        recycleView.layoutManager = GridLayoutManager(context,3)
        recycleView.adapter = SavedAdapter(postList){post->onItemSelected(post)}
    }

    fun onItemSelected(post: Post){
        val intent = Intent(getActivity(), PostActivity::class.java)
        intent.putExtra("post_id", post.id)
        intent.putExtra("post_photo", post.photo)
        intent.putExtra("post_title", post.title)
        intent.putExtra("post_content", post.content)
        intent.putExtra("post_creator", post.iduser)
        getActivity()?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}