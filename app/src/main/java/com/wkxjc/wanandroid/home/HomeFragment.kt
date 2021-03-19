package com.wkxjc.wanandroid.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.library.project.BaseFragment
import com.base.library.project.myStartActivity
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.http.api.BaseApi
import com.base.library.rxRetrofit.http.httpList.HttpListListener
import com.base.library.rxRetrofit.http.listener.HttpListener
import com.wkxjc.wanandroid.R
import com.wkxjc.wanandroid.databinding.FragmentHomeBinding
import com.wkxjc.wanandroid.common.artical.LINK
import com.wkxjc.wanandroid.common.artical.WebActivity
import com.wkxjc.wanandroid.home.common.api.ArticleApi
import com.wkxjc.wanandroid.home.common.api.BannerApi
import com.wkxjc.wanandroid.home.common.api.CollectApi
import com.wkxjc.wanandroid.home.common.bean.ArticleBean


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun releaseBinding() {
        _binding = null
    }

    private val httpManager = HttpManager(this)
    private val bannerApi = BannerApi()
    private val articleApi = ArticleApi()
    private val collectApi = CollectApi()
    private val homeAdapter = HomeAdapter()
    private val collectListener = object : HttpListener() {
        override fun onNext(result: String) {
            Log.d("~~~", "result: $result")
        }

        override fun onError(error: Throwable) {
        }
    }
    private val homeListListener = object : HttpListListener() {
        override fun onNext(resultMap: HashMap<BaseApi, Any>) {
            homeAdapter.refresh(bannerApi.convert(resultMap), articleApi.convert(resultMap))
            binding.refreshHome.isRefreshing = false
        }

        override fun onError(error: Throwable) {
        }
    }

    private val loadMoreListener = object : HttpListener() {
        override fun onNext(result: String) {
            homeAdapter.addMore(articleApi.convert(result))
        }

        override fun onError(error: Throwable) {
        }

    }

    override fun initView() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
        }
        homeAdapter.onItemClickListener = object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, bean: ArticleBean) {
                when (view.id) {
                    R.id.tvCollect -> httpManager.request(collectApi.apply {
                        articleId = bean.id
                    }, collectListener)
                    else -> myStartActivity<WebActivity>(LINK to bean.link)
                }
            }
        }
        binding.refreshHome.setOnRefreshListener {
            loadData()
        }
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if ((rvHome.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == homeAdapter.itemCount - 1) {
                    // load more
                    loadMore()
                }
            }
        })
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        // data should be loaded in onResume, since collect/login state maybe changed
        loadData()
    }

    private fun loadData() {
        refreshHome.isRefreshing = true
        articleApi.resetPage()
        httpManager.request(arrayOf(bannerApi, articleApi), homeListListener)
    }

    private fun loadMore() {
        articleApi.nextPage()
        httpManager.request(articleApi, loadMoreListener)
    }
}