package com.example.memorygame.view.dest

import android.util.Log
import androidx.navigation.fragment.navArgs
import com.example.memorygame.R
import com.example.memorygame.base.BaseFragment
import com.example.memorygame.databinding.FragmentGameBinding

class GameFragment : BaseFragment<FragmentGameBinding>(
  R.layout.fragment_game
) {
  private val args by lazy { navArgs<GameFragmentArgs>().value }
  private val round by lazy { args.round }
  private val row by lazy { args.row }
  private val col by lazy { args.col }

  override fun FragmentGameBinding.bindingViewData() {
    Log.d("GameFragment", "round : ${round}, ${row} X ${col}")
  }

  override fun FragmentGameBinding.setEventListener() {
  }
}