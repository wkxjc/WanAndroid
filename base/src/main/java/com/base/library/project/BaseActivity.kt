package com.base.library.project

import android.content.Context
import android.os.Bundle
import android.view.View
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.base.library.project.utils.LanguageUtils
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), IBase {
    protected lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initViewBinding() {
        val superClass = javaClass.genericSuperclass as ParameterizedType
        val actualClass = superClass.actualTypeArguments.first() as Class<*>
        val inflateMethod = actualClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        binding = inflateMethod.invoke(null, layoutInflater) as T
    }

    override fun attachBaseContext(newBase: Context?) {
        // set language
        super.attachBaseContext(LanguageUtils.getContextWithLanguage(newBase))
    }
}