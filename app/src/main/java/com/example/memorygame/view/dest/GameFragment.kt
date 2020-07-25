package com.example.memorygame.view.dest

import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.setMargins
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.memorygame.R
import com.example.memorygame.base.BaseFragment
import com.example.memorygame.databinding.FragmentGameBinding
import com.example.memorygame.viewmodel.GameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : BaseFragment<FragmentGameBinding>(
  R.layout.fragment_game
) {
  private val args by lazy { navArgs<GameFragmentArgs>().value }
  private val rounds by lazy { args.round }
  private val rows by lazy { args.row }
  private val cols by lazy { args.col }

  private lateinit var blocks: Array<Array<Boolean>>
  private val blockButtons: Array<Array<Button>> by lazy {
    Array(rows) { Array(cols) { Button(context) } }
  }
  private var blockButtonEnabled = false

  private var round: Int = 0

  private val dp by lazy { resources.displayMetrics.density }

  private val viewModel by viewModels<GameViewModel>()

  override fun FragmentGameBinding.bindingViewData() {
    startGame()
  }

  override fun FragmentGameBinding.setEventListener() {}

  private fun FragmentGameBinding.startGame() {
    blocks = viewModel.makeBlocks(rows, cols)
    currentRound = round + 1
    totalRound = rounds
    progressTextId = R.string.fragment_game_progress_ready

    initBlocks()
  }

  private fun FragmentGameBinding.initBlocks() = blocksContainer.post {
    val margin = (3 * dp).toInt()
    val size = blocksContainer.width / rows - margin * (cols - 1)

    for (row in 0 until rows) {
      val blockRowContainer = LinearLayoutCompat(context).apply {
        orientation = LinearLayoutCompat.HORIZONTAL
        layoutParams = LinearLayoutCompat.LayoutParams(
          LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
          LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
      }
      for (col in 0 until cols) {
        val block = Button(context).apply {
          layoutParams = LinearLayoutCompat.LayoutParams(size, size, 1f).apply {
            setMargins(margin)
          }
          background = ContextCompat.getDrawable(context, R.drawable.block)
          setOnClickListener { onBlockClick(row, col) }
        }
        blockRowContainer.addView(block)
        blockButtons[row][col] = block
      }
      blocksContainer.addView(blockRowContainer)
    }

    blockSettingAnim(true) {
      blockSettingAnim {
        progressTextId = R.string.fragment_game_progress_start
      }
    }
  }

  private fun FragmentGameBinding.onBlockClick(row: Int, col: Int) {
    if (!blockButtonEnabled) return

    viewModel.selectBlock(row, col,
      wrong = {
        blocks = it
        wrong()
      },
      correct = { nextRound ->
        when {
          // 다음 라운드 진행
          nextRound -> {
            currentRound = ++round + 1

            if (round != rounds) {
              nextRound()
            } else {
              finishGame()
            }
          }
          // 맞췄지만 다음 라운드 진행 X
          else -> blockButtons[row][col].isActivated = true
        }
      })
  }

  private fun FragmentGameBinding.wrong() {
    progressTextId = R.string.fragment_game_progress_wrong
    blockSettingAnim(true) {
      blockSettingAnim {
        progressTextId = R.string.fragment_game_progress_start
      }
    }
  }

  private fun FragmentGameBinding.nextRound() {
    blocks = viewModel.makeBlocks(rows, cols)

    progressTextId = R.string.fragment_game_progress_ready
    blockSettingAnim(true) {
      blockSettingAnim {
        progressTextId = R.string.fragment_game_progress_start
      }
    }
  }

  private fun finishGame() = requireActivity().onBackPressed()

  private fun FragmentGameBinding.blockSettingAnim(
    active: Boolean = false,
    finish: () -> Unit = {}
  ) {
    blockButtonEnabled = false

    for ((row, rowContainer) in blocksContainer.children.withIndex()) {
      for ((col, block) in (rowContainer as ViewGroup).children.withIndex()) {
        block.alpha = 0f
        block.isActivated = blocks[row][col] && active
      }
    }

    CoroutineScope(Dispatchers.Main).launch {
      for (rowContainer in blocksContainer.children) {
        for (block in (rowContainer as ViewGroup).children) {
          block.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(300L).start()
          delay(100L)
        }
      }

      delay(300L)

      blockButtonEnabled = true
      finish()
    }
  }
}