package ru.malygin.crusade.model.codex

data class BattleScar(
    val name: String,
    val faction: CodexFaction,
    val battlefieldRole: BattlefieldRole,
    val description: String
)
