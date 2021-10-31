package ru.malygin.crusade.service

import ru.malygin.crusade.model.OrderOfBattle

sealed class StartCrusadeResult(
    val newOrderOfBattle: OrderOfBattle?
)

class AlreadyStartedCrusade(newOrderOfBattle: OrderOfBattle) : StartCrusadeResult(newOrderOfBattle)
class SuccessfullyStartedCrusade(newOrderOfBattle: OrderOfBattle) : StartCrusadeResult(newOrderOfBattle)
class FailedToStartCrusade : StartCrusadeResult(null)