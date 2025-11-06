package com.ganlink.pe.core.resources

import com.ganlink.pe.features.auth.data.local.models.UserEntity
import com.ganlink.pe.features.farmmanagement.domain.models.UserResource


     fun resourceToEntity(userResource: UserResource) : UserEntity{
        return UserEntity(
            id = userResource.id
        )
    }
