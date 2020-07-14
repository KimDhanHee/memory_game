package com.example.memorygame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.memorygame.databinding.FragmentRecordByDifficultyBinding

class RecordByDifficultyFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = DataBindingUtil.inflate<FragmentRecordByDifficultyBinding>(
    inflater,
    R.layout.fragment_record_by_difficulty,
    container,
    false
  ).run {
    root
  }
}