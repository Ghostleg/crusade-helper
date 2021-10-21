package ru.malygin.crusade.model.codex

data class UnitCodexParameters(
    val battlefieldRole: BattlefieldRole,
    val powerRating: Int,
    val unitType: UnitType,
    val equipment: List<Equipment>,
)
