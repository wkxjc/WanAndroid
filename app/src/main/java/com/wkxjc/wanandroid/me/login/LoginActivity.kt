package com.wkxjc.wanandroid.me.login

import com.base.library.project.BaseActivity
import com.base.library.project.myStartActivity
import com.base.library.project.showToast
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.http.listener.HttpListener
import com.wkxjc.wanandroid.MyApplication
import com.wkxjc.wanandroid.databinding.ActivityLoginBinding
import com.wkxjc.wanandroid.me.common.api.LoginApi
import com.wkxjc.wanandroid.me.login.register.RegisterActivity
import com.wkxjc.wanandroid.me.NonTouristUser


class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val httpManager = HttpManager(this)
    private val loginApi = LoginApi()
    private val listener = object : HttpListener() {
        override fun onNext(result: String) {
            val loginBean = loginApi.convert(result)
            showToast("login success")
            MyApplication.user.loginOn(loginBean.publicName)
            finish()
        }

        override fun onError(error: Throwable) {
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
            showToast("User name cannot be empty!")
            return true
        }
        if (binding.etLoginPassword.text.isEmpty()) {
            showToast("Password cannot be empty!")
            return true
        }
        return false
    }

    override fun initData() {
    }

}
