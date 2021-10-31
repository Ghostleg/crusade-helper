package ru.malygin.crusade.model.codex

import org.springframework.data.mongodb.core.mapping.DBRef


data class UnitCodexParameters constructor(
    val battlefieldRole: BattlefieldRole,
    val powerRating: Int,
    /*val unitType: UnitType,*/
    @DBRef
    val equipment: List<Equipment> = emptyList(),
)
