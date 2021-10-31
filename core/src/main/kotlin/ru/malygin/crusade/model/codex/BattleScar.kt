package ru.malygin.crusade.model.codex

data class BattleScar constructor(
    val name: String,
    val faction: CodexFaction,
    val battlefieldRole: BattlefieldRole,
    val description: String
)
