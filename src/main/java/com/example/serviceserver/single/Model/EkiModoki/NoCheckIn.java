package com.example.serviceserver.single.Model.EkiModoki;

import io.vertx.core.json.JsonObject;

public class NoCheckIn implements CheckIn {
  @Override
  public JsonObject makePersistentJson() {
    return null;
  }
}
