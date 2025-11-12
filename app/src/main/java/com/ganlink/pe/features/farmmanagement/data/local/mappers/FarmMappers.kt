package com.ganlink.pe.features.farmmanagement.data.local.mappers

import com.ganlink.pe.features.farmmanagement.data.local.models.CreatedFarmIdEntity
import com.ganlink.pe.features.farmmanagement.data.local.models.FarmEntity
import com.ganlink.pe.features.farmmanagement.domain.models.CreateFarmWrapperDto
import com.ganlink.pe.features.farmmanagement.domain.models.FarmDto

fun FarmDto.toEntity(): FarmEntity = FarmEntity(
    id = id,
    alias = alias,
    description = description,
    mainActivity = mainActivity,
    ownerDni = ownerDni,
    userId = userId
)

fun FarmEntity.toDomain(): FarmDto = FarmDto(
    alias = alias,
    description = description,
    id = id,
    mainActivity = mainActivity,
    ownerDni = ownerDni,
    userId = userId
)

fun CreateFarmWrapperDto.toCreatedFarmIdEntity(): CreatedFarmIdEntity =
    CreatedFarmIdEntity(id = id)
