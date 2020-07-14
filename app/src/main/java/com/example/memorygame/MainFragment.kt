package com.example.memorygame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.memorygame.databinding.FragmentMainBinding

class MainFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<FragmentMainBinding>(
    inflater,
    R.layout.fragment_main,
    container,
    false
  ).run {

    setEventListener()

    root
  }

  private fun FragmentMainBinding.setEventListener() {
    buttonGameStart.setOnClickListener {
      findNavController().navigate(
        MainFragmentDirections.actionMainFragmentToGameSettingFragment()
      )
    }

    buttonRecord.setOnClickListener {
      findNavController().navigate(
        MainFragmentDirections.actionMainFragmentToRecordFragment()
      )
    }
  }
}