package ru.malygin.crusade.model.codex

data class Equipment(
    val name: String,
    val profile: List<WeaponProfile>?,
    val specialRules: List<String>,
)

data class WeaponProfile(
    val strength: Int,
    val armourPiercing: Int,
    val damage: Int,
)
