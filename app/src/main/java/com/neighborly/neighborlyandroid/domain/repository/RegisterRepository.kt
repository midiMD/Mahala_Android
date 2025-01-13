package com.neighborly.neighborlyandroid.domain.repository

import com.neighborly.neighborlyandroid.common.RegisterResponseState
import com.neighborly.neighborlyandroid.domain.model.RegistrationDetails

interface RegisterRepository {
    suspend fun register(details: RegistrationDetails): RegisterResponseState
}