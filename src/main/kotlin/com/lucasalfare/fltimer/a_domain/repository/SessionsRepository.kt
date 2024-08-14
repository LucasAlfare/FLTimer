package com.lucasalfare.fltimer.a_domain.repository

import com.lucasalfare.fltimer.a_domain.models.Session
import com.lucasalfare.fltimer.a_domain.models.Solve

interface SessionsRepository {

  suspend fun create(name: String, solves: List<Solve> = listOf()): Int

  suspend fun get(id: Int): Session?

  suspend fun update(
    id: Int,
    nextName: String? = null,
    nextSolves: List<Solve>? = null
  ): Boolean

  suspend fun delete(id: Int): Boolean
}