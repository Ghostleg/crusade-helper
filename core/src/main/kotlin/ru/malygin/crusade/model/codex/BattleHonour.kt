package ru.malygin.crusade.model.codex

data class BattleHonour(
    val name: String,
    val faction: CodexFaction,
    val battlefieldRole: BattlefieldRole,
    val description: String,
)