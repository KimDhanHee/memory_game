package com.example.memorygame.view.dest

import androidx.navigation.fragment.findNavController
import com.example.memorygame.R
import com.example.memorygame.base.BaseFragment
import com.example.memorygame.databinding.FragmentGameSettingBinding

class GameSettingFragment : BaseFragment<FragmentGameSettingBinding>(
  R.layout.fragment_game_setting
) {
  override fun FragmentGameSettingBinding.bindingViewData() {
  }

  override fun FragmentGameSettingBinding.setEventListener() {
    buttonGameStart.setOnClickListener {
      val round = numberpickerRound.value
      val row = numberpickerRow.value
      val col = numberpickerCol.value

      findNavController().navigate(
        GameSettingFragmentDirections.actionGameSettingFragmentToGameFragment(round, row, col)
      )
    }
  }
}