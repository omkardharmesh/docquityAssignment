package com.omkar.docquityassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.omkar.docquityassignment.adapter.PostAdapter
import com.omkar.docquityassignment.databinding.ActivityMainBinding
import com.omkar.docquityassignment.model.Post
import com.omkar.docquityassignment.repository.Repository
import com.omkar.docquityassignment.util.Resource

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: PostAdapter
    lateinit var postList: List<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()


        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        binding.searchView.requestFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                query?.let {
                    viewModel.getPostDetails(it.toInt())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.postItem.observe(this, Observer { item ->
            when (item) {
                is Resource.Success -> {
                    item.data?.let {
                        postList = listOf(it)
                        setUpRecyclerView()
                    }
                }
                is Resource.Error -> {
                    item.message?.let { message ->
                        Toast.makeText(this, "An Error Occurred $message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                else -> {
                    Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }


    private fun setUpRecyclerView() {
        postAdapter = PostAdapter(postList)
        binding.rvPost.apply {
            binding.rvPost.adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}