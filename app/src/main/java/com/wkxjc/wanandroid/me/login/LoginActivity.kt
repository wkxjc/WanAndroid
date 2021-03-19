package com.wkxjc.wanandroid.me.login

import android.util.Log
import com.base.library.project.BaseActivity
import com.base.library.project.myStartActivity
import com.base.library.project.toast
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.http.listener.HttpListener
import com.wkxjc.wanandroid.databinding.ActivityLoginBinding
import com.wkxjc.wanandroid.me.common.api.LoginApi
import com.wkxjc.wanandroid.me.login.register.RegisterActivity


class LoginActivity : BaseActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun createBinding() = binding.root
    private val httpManager = HttpManager(this)
    private val loginApi = LoginApi()
    private val listener = object : HttpListener() {
        override fun onNext(result: String) {
            Log.d("~~~", "result: $result")
            toast("login success")
            finish()
        }

        override fun onError(error: Throwable) {
            Log.d("~~~", "error:$error")
            binding.btnLogin.isEnabled = true
        }
    }

    override fun initView() {
        binding.btnLogin.setOnClickListener {
            if (inputNotValid()) return@setOnClickListener
            it.isEnabled = false
            loginApi.username = binding.etLoginUserName.text.toString()
            loginApi.password = binding.etLoginPassword.text.toString()
            httpManager.request(loginApi, listener)
        }
        binding.btnGoToRegister.setOnClickListener {
            myStartActivity<RegisterActivity>()
        }
    }

    private fun inputNotValid(): Boolean {
        if (binding.etLoginUserName.text.isEmpty()) {
            toast("User name cannot be empty!")
            return true
        }
        if (binding.etLoginPassword.text.isEmpty()) {
            toast("Password cannot be empty!")
            return true
        }
        return false
    }

    override fun initData() {
    }

}
