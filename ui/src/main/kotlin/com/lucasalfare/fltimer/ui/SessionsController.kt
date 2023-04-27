package com.lucasalfare.fltimer.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import com.lucasalfare.fltimer.core.FLTimerEvent
import com.lucasalfare.fltimer.core.model.FLTimerModel
import com.lucasalfare.fltimer.core.model.session.Session
import uiManager

@Composable
fun SessionController() {
    val sessions = remember { FLTimerModel.sessions }
    val currentActiveSessionName = remember { FLTimerModel.currentActiveSessionName }

    Row(verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = {
            uiManager.notifyListeners(
                FLTimerEvent.SessionSwitch,
                getSession(sessions.first {it.name == currentActiveSessionName.value }, sessions, false)
            )
        }) {
            Text("<<")
        }

        Text(currentActiveSessionName.value)

        TextButton(onClick = {
            uiManager.notifyListeners(
                FLTimerEvent.SessionSwitch,
                getSession(sessions.first {it.name == currentActiveSessionName.value }, sessions, true)
            )
        }) {
            Text(">>")
        }
    }
}

private fun getSession(
    currentSession: Session,
    sessions: SnapshotStateList<Session>,
    next: Boolean
): Session {
    val currentIndex = sessions.indexOf(currentSession)
    val factor = if (next) 1 else -1
    var targetIndex = (currentIndex + factor) % sessions.size
    if (targetIndex < 0) targetIndex = sessions.size - 1
    return sessions[targetIndex]
}