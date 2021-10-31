package ru.malygin.crusade.model.codex

data class BattleHonour constructor(
    val name: String,
    val faction: CodexFaction,
    val acceptableKeywords: List<UnitKeyword>,
    val excludingKeywords: List<UnitKeyword>? = emptyList(),
    val description: String,
)