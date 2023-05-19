package com.example.pawpal.screens.home.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.pawpal.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.webViewContacts) {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://www.mypetvet.com.ua/contact/")
        }
    }
}