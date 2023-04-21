package com.lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.lucasalfare.fltimer.ui.onFocusSelectAll

@Composable
fun StatisticDetails(textContent: String, onBackCallBack: () -> Unit) {
  val tfvState = remember { mutableStateOf(TextFieldValue(textContent)) }

  TextButton(
    modifier = Modifier.padding(bottom = 8.dp),
    onClick = {
      onBackCallBack()
    }
  ) {
    Text("<- Return")
  }

  TextField(
    value = tfvState.value,
    modifier = Modifier.fillMaxSize().onFocusSelectAll(tfvState),
    readOnly = true,
    onValueChange = {}
  )
}