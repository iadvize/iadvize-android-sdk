package com.iadvize.conversation.sdk.demo.navigationitem.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iadvize.conversation.sdk.demo.navigationitem.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {
    private var binding: ProfileFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }
}
