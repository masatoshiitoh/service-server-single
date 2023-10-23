package com.example.serviceserver.single.Model.EkiModoki;

import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public class AttackCheckIn implements CheckIn {

  int playerId; // 勝った方のプレイヤー
  int lastPlayerId; // スポットを奪われた方のプレイヤー
  int stationId;
  LocalDateTime checkInTime;

  public AttackCheckIn(int playerId, int lastPlayerId, int stationId, LocalDateTime checkInTime) {
    this.playerId = playerId;
    this.lastPlayerId = lastPlayerId;
    this.stationId = stationId;
    this.checkInTime = checkInTime;

  }

  @Override
  public JsonObject makePersistentJson() {
    return new JsonObject().put("playerId", playerId).put("stationId", stationId).put("checkInTime",
        checkInTime.toString());
  }
}
