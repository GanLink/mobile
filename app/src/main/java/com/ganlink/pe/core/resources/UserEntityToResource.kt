package com.ganlink.pe.core.resources

import com.ganlink.pe.features.auth.data.local.models.UserEntity
import com.ganlink.pe.features.farmmanagement.domain.models.UserResource

class UserEntityToResource {
    fun entityToResource(userEntity: UserEntity) : UserResource{
        return UserResource(
            id = userEntity.id
        )
    }
}