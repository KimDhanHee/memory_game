package com.example.memorygame.viewmodel

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
  // 문제 블록
  private lateinit var blocks: Array<Array<Boolean>>
  // 정답 블록
  private lateinit var answer: Array<Array<Boolean>>

  /**
   * 블록 선택에 대한 로직
   * @param row : 선택 블록 행 index
   * @param col : 선택 블록 열 index
   * @param wrong : 선택한 블록이 틀렸을 때 blocks 를 초기화 하여 호출 시 파라미터로 전달
   * @param correct : 선택한 블록이 맞았을 때 호출되며 음 라운드 진행 여부 파라미터로 전달
   */
  fun selectBlock(
    row: Int,
    col: Int,
    wrong: (Array<Array<Boolean>>) -> Unit,
    correct: (Boolean) -> Unit
  ) {
    // 정답 블록이 아니면
    if (!blocks[row][col]) {
      // blocks 초기화 후
      blocks = answer.map { child -> child.clone() }.toTypedArray()
      // wrong 함수 파라미터로 초기화 된 blocks 반환
      wrong(blocks)
      return
    }

    // 정답 블록 체크
    blocks[row][col] = false

    // 다음 라운드 여부 반환
    correct(checkBlocks())
  }

  // 모든 블록을 맞췄는지 여부
  fun checkBlocks(): Boolean = !blocks.flatten().reduce { acc, b -> acc || b }

  // 새로운 블록 생성
  fun makeBlocks(rows: Int, cols: Int): Array<Array<Boolean>> =
    Array(rows) {
      Array(cols) { Random.nextInt(cols) == 0 }
    }.also {
      blocks = it
      answer = it.map { child -> child.clone() }.toTypedArray()
    }
}