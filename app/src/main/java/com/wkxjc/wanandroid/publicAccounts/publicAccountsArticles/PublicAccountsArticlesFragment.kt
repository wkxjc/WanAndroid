package com.wkxjc.wanandroid.publicAccounts.publicAccountsArticles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.library.project.BaseFragment
import com.base.library.project.myStartActivity
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.http.listener.HttpListener
import com.lewis.widget.ui.Status
import com.lewis.widget.ui.view.StatusView
import com.wkxjc.wanandroid.R
import com.wkxjc.wanandroid.common.artical.LINK
import com.wkxjc.wanandroid.common.artical.WebActivity
import com.wkxjc.wanandroid.databinding.FragmentPublicAccountsArticlesBinding
import com.wkxjc.wanandroid.home.common.api.CollectApi
import com.wkxjc.wanandroid.home.common.api.PublicAccountsArticlesApi
import com.wkxjc.wanandroid.home.common.bean.ArticleBean
import com.wkxjc.wanandroid.me.common.api.HomePageCancelCollectionApi
import com.wkxjc.wanandroid.me.user.User
import com.wkxjc.wanandroid.publicAccounts.PublicAccountsViewModel

class PublicAccountsArticlesFragment : BaseFragment<FragmentPublicAccountsArticlesBinding>() {
    // loadMore page++
    // PublicAccountsFragment observe page
    // observe api
    // MergedLiveData
    // articles refresh -> articles
    // scroll flag page++ no, id change, yes
    // id observe scroll
    private val viewModel by activityViewModels<PublicAccountsViewModel>()
    private val publicAccountArticlesAdapter by lazy { PublicAccountsArticlesAdapter() }
    private val statusView by lazy { StatusView.initInFragment(context, binding.root) }
    private val httpManager = HttpManager(this)
    private val publicAccountArticlesApi by lazy { PublicAccountsArticlesApi() }

    private val publicAccountsArticlesListener = object : HttpListener() {
        override fun onNext(result: String) {
            viewModel.publicAccountsArticles.value = publicAccountArticlesApi.convert(result)
            viewModel.publicAccountsArticlesStatus.value = Status.NORMAL
            viewModel.publicAccountsStatus.value = Status.NORMAL
            binding.rvPublicAccountArticles.scrollToPosition(0)
        }

        override fun onError(error: Throwable) {
            viewModel.publicAccountsArticlesStatus.value = Status.ERROR
            viewModel.publicAccountsStatus.value = Status.NORMAL
        }
    }
    private val publicAccountsArticlesLoadMoreListener = object : HttpListener() {
        override fun onNext(result: String) {
            publicAccountArticlesAdapter.loadMore(publicAccountArticlesApi.convert(result))
        }

        override fun onError(error: Throwable) {
            viewModel.publicAccountsArticlesStatus.value = Status.ERROR
        }
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPublicAccountArticles.layoutManager = LinearLayoutManager(context)
        binding.rvPublicAccountArticles.adapter = publicAccountArticlesAdapter
        statusView.setOnRetryBtnClickListener {
            // Let LiveData refresh
            viewModel.currentPublicAccountsAuthorId.value = viewModel.currentPublicAccountsAuthorId.value
        }
        publicAccountArticlesAdapter.onItemClickListener = ::onItemClick
        publicAccountArticlesAdapter.loadMore = {
            viewModel.currentPublicAccountsArticlesPage.value = viewModel.currentPublicAccountsArticlesPage.value!! + 1
        }
        viewModel.publicAccountsArticles.observe(this, {
            publicAccountArticlesAdapter.refresh(it)
        })
        viewModel.publicAccountsArticlesStatus.observe(this, {
            statusView.setStatus(it)
        })

        viewModel.currentPublicAccountsAuthorId.observe(this, {
            viewModel.publicAccountsArticlesStatus.value = Status.LOADING
            viewModel.currentPublicAccountsArticlesPage.value = 0
            httpManager.request(publicAccountArticlesApi.apply {
                id = it
                page = 0
            }, publicAccountsArticlesListener)
        })
        viewModel.currentPublicAccountsArticlesPage.observe(this, {
            if (it == 0) return@observe
            httpManager.request(publicAccountArticlesApi.apply {
                page = it
            }, publicAccountsArticlesLoadMoreListener)
        })
    }

    private fun onItemClick(view: View, bean: ArticleBean, position: Int) {
        when (view.id) {
            R.id.ivCollect -> {
                if (User.isNotLogon()) return
                httpManager.request(if (bean.collect) HomePageCancelCollectionApi(bean.id) else CollectApi(bean.id))
                bean.collect = !bean.collect
                publicAccountArticlesAdapter.notifyItemChanged(position)
            }
            else -> myStartActivity<WebActivity>(LINK to bean.link)
        }
    }
}