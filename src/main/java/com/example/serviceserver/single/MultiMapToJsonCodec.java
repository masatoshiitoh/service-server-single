package com.example.serviceserver.single;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public class MultiMapToJsonCodec implements MessageCodec<MultiMap, JsonObject> {
  @Override
  public void encodeToWire(Buffer buffer, MultiMap entries) {
    JsonObject json = transform(entries);
    buffer.appendBuffer(Json.encodeToBuffer(json));
  }

  @Override
  public JsonObject decodeFromWire(int i, Buffer buffer) {
    buffer.getBuffer(i, buffer.length());
    return buffer.toJsonObject();
  }

  @Override
  public JsonObject transform(MultiMap entries) {
    JsonObject json = new JsonObject();
    for (Map.Entry<String, String> entry : entries.entries()) {
      json.put(entry.getKey(), entry.getValue());
    }
    return json;
  }

  @Override
  public String name() {
    return "MultiMapToJsonCodec";
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
