package com.lucasalfare.fltimer.ui.screens.stats

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.lucasalfare.fltimer.core.model.FLTimerState
import com.lucasalfare.fltimer.core.statistics.*
import com.lucasalfare.fltimer.core.toTimestamp
import com.lucasalfare.fltimer.ui.theme.FLTimerTheme

class StatisticsTemplateProvider {
  companion object {

    const val STATISTIC_TAG_LABEL = "statistic"

    enum class TemplateVariable(val id: String) {
      CurrentDayDate("%current_day_date"),
      SolvesTotal("%solves_total"),
      CurrentSingle("%current_single"),
      BestSingle("%best_single"),
      WorstSingle("%worst_single"),

      GlobalMean("%mean"),
      GlobalAverage("%global_average"),

      CurrentAverageOf5("%current_average_of5"),
      BestAverageOf5("%best_average_of5"),
      WorstAverageOf5("%worst_average_of5"),

      CurrentAverageOf12("%current_average_of12"),
      BestAverageOf12("%best_average_of12"),
      WorstAverageOf12("%worst_average_of12"),

      CurrentAverageOf50("%current_average_of50"),
      BestAverageOf50("%best_average_of50"),
      WorstAverageOf50("%worst_average_of50"),

      CurrentAverageOf100("%current_average_of100"),
      BestAverageOf100("%best_average_of100"),
      WorstAverageOf100("%worst_average_of100")
    }

    val templateModel = arrayOf(
      "Generated by FLTimer in ", TemplateVariable.CurrentDayDate.id,
      "\n",
      "solves total: ", TemplateVariable.SolvesTotal.id,
      "\n",
      "\n",
      "single",
      "\n",
      "    current: ", TemplateVariable.CurrentSingle.id,
      "\n",
      "    best: ", TemplateVariable.BestSingle.id,
      "\n",
      "    worst: ", TemplateVariable.WorstSingle.id,
      "\n",
      "\n",
      "global mean: ", TemplateVariable.GlobalMean.id,
      "\n",
      "global average: ", TemplateVariable.GlobalAverage.id,
      "\n",
      "\n",
      "avg of 5",
      "\n",
      "    current: ", TemplateVariable.CurrentAverageOf5.id,
      "\n",
      "    best: ", TemplateVariable.BestAverageOf5.id,
      "\n",
      "    worst: ", TemplateVariable.WorstAverageOf5.id,
      "\n",

      "\n",
      "avg of 12",
      "\n",
      "    current: ", TemplateVariable.CurrentAverageOf12.id,
      "\n",
      "    best: ", TemplateVariable.BestAverageOf12.id,
      "\n",
      "    worst: ", TemplateVariable.WorstAverageOf12.id,
      "\n",

      "\n",
      "avg of 50",
      "\n",
      "    current: ", TemplateVariable.CurrentAverageOf50.id,
      "\n",
      "    best: ", TemplateVariable.BestAverageOf50.id,
      "\n",
      "    worst: ", TemplateVariable.WorstAverageOf50.id,
      "\n",

      "\n",
      "avg of 100",
      "\n",
      "    current: ", TemplateVariable.CurrentAverageOf100.id,
      "\n",
      "    best: ", TemplateVariable.BestAverageOf100.id,
      "\n",
      "    worst: ", TemplateVariable.WorstAverageOf100.id,
      "\n",
    )

    fun lookUpVariableValue(key: String): StatisticResult {
      val solves = FLTimerState.getCurrentActiveSession().solves

      if (solves.isNotEmpty()) {
        when (key) {
          TemplateVariable.BestSingle.id -> return solves.best()
          TemplateVariable.WorstSingle.id -> return solves.worst()

          TemplateVariable.GlobalMean.id -> return solves.mean()
          TemplateVariable.GlobalAverage.id -> return solves.globalAverage()

          TemplateVariable.BestAverageOf5.id -> return solves.bestAverageOf(5)
          TemplateVariable.WorstAverageOf5.id -> return solves.worstAverageOf(5)

          TemplateVariable.BestAverageOf12.id -> return solves.bestAverageOf(12)
          TemplateVariable.WorstAverageOf12.id -> return solves.worstAverageOf(12)

          TemplateVariable.BestAverageOf50.id -> return solves.bestAverageOf(50)
          TemplateVariable.WorstAverageOf50.id -> return solves.worstAverageOf(50)

          TemplateVariable.BestAverageOf100.id -> return solves.bestAverageOf(100)
          TemplateVariable.WorstAverageOf100.id -> return solves.worstAverageOf(100)

          TemplateVariable.CurrentAverageOf5.id -> return solves.rollingAverage(5)
          TemplateVariable.CurrentAverageOf12.id -> return solves.rollingAverage(12)
          TemplateVariable.CurrentAverageOf50.id -> return solves.rollingAverage(50)
          TemplateVariable.CurrentAverageOf100.id -> return solves.rollingAverage(100)

          TemplateVariable.CurrentSingle.id -> {
            val lastSolve = solves.last()
            return StatisticResult(
              name = "currentSingle",
              result = lastSolve.time,
              related = mutableListOf(lastSolve)
            )
          }
        }
      }

      return StatisticResult.notCalculatedResult
    }
  }
}

@Composable
fun getStatisticsAnnotatedString() = buildAnnotatedString {
  var currentCharacterCursorPosition = 0

  StatisticsTemplateProvider.templateModel.forEach {
    if (it == "\n") {
      appendLine()
      currentCharacterCursorPosition++
    } else {
      if (it.startsWith("%")) {
        val currStatResult = StatisticsTemplateProvider.lookUpVariableValue(it)

        if (currStatResult != StatisticResult.notCalculatedResult && currStatResult != StatisticResult.dnfResult) {
          append(currStatResult.result.toTimestamp())

          addStyle(
            style = SpanStyle(
              color = FLTimerTheme.colors.error,
              fontFamily = FLTimerTheme.typography.body.fontFamily,
              textDecoration = TextDecoration.Underline
            ),
            start = currentCharacterCursorPosition,
            end = currentCharacterCursorPosition + currStatResult
              .result
              .toTimestamp()
              .length
          )

          addStringAnnotation(
            tag = StatisticsTemplateProvider.STATISTIC_TAG_LABEL,
            annotation = it,
            start = currentCharacterCursorPosition,
            end = currentCharacterCursorPosition + currStatResult
              .result
              .toTimestamp()
              .length
          )

          currentCharacterCursorPosition += currStatResult
            .result
            .toTimestamp()
            .length
        } else {
          val txt = "- -"
          append(txt)
          currentCharacterCursorPosition += txt.length
        }
      } else {
        currentCharacterCursorPosition += it.length
        append(it)
      }
    }
  }

  addStyle(
    style = SpanStyle(
      fontFamily = FLTimerTheme.typography.body.fontFamily,
      color = FLTimerTheme.colors.textOnPrimary
    ),
    start = 0,
    end = currentCharacterCursorPosition
  )
}