package com.example.memorygame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.memorygame.databinding.FragmentGameSettingBinding

class GameSettingFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<FragmentGameSettingBinding>(
    inflater,
    R.layout.fragment_game_setting,
    container,
    false
  ).run {
    root
  }
}