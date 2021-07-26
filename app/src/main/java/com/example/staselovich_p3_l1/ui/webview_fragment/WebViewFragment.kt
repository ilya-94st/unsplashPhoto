package com.example.staselovich_p3_l1.ui.webview_fragment

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.navigation.fragment.navArgs
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.base.BaseFragment
import com.example.staselovich_p3_l1.databinding.FragmentWebViewBinding
import kotlinx.android.synthetic.main.fragment_web_view.*


class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {
    override fun getBinding() = R.layout.fragment_web_view
    private val args by navArgs<WebViewFragmentArgs>()
    companion object{
        const val PROGRES =100
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
        setWebClient()
args.link.let {
    loadUrl(it)
}
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }
    private fun setWebClient() {
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
                if (newProgress < PROGRES && binding.progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == PROGRES) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }
    private fun loadUrl(pageUrl: String) {
        binding.webView.loadUrl(pageUrl)
    }
}