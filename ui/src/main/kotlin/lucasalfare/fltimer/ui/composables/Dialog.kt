package lucasalfare.fltimer.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Dialog(
  modifier: Modifier = Modifier,
  title: String = "",
  bodyText: String = "",
  dismiss: () -> Unit,
  confirmationCallback: () -> Unit
) {
  AlertDialog(
    modifier = modifier,
    onDismissRequest = dismiss,
    title = {
      Text(text = title)
    },
    text = {
      Text(bodyText)
    },
    buttons = {
      Row(
        horizontalArrangement = Arrangement.Center
      ) {
        Button(
          modifier = Modifier.padding(8.dp),
          onClick = dismiss
        ) {
          Text("Cancel")
        }

        Button(
          modifier = Modifier.padding(8.dp),
          onClick = {
            confirmationCallback()
            dismiss()
          }
        ) {
          Text("Confirm")
        }
      }
    }
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Dialog(
  modifier: Modifier = Modifier,
  title: String = "",
  bodyContent: @Composable () -> Unit,
  dismiss: () -> Unit,
  confirmationCallback: () -> Unit
) {
  AlertDialog(
    modifier = modifier,
    onDismissRequest = dismiss,
    title = {
      Text(text = title)
    },
    text = {
      bodyContent()
    },
    buttons = {
      Row(
        horizontalArrangement = Arrangement.Center
      ) {
        Button(
          modifier = Modifier.padding(8.dp),
          onClick = dismiss
        ) {
          Text("Cancel")
        }

        Button(
          modifier = Modifier.padding(8.dp),
          onClick = {
            confirmationCallback()
            dismiss()
          }
        ) {
          Text("Confirm")
        }
      }
    }
  )
}
