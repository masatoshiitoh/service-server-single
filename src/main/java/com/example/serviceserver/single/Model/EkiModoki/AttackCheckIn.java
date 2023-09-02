package com.example.serviceserver.single.Model.EkiModoki;

import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public class AttackCheckIn implements CheckIn {

  int playerId;
  int stationId;
  LocalDateTime checkInTime;

  public AttackCheckIn(int playerId, int stationId, LocalDateTime checkInTime) {
    this.playerId = playerId;
    this.stationId = stationId;
    this.checkInTime = checkInTime;
  }

  @Override
  public JsonObject makePersistentJson() {
    return new JsonObject().put("playerId", playerId).put("stationId", stationId).put("checkInTime",
        checkInTime.toString());
  }
}
