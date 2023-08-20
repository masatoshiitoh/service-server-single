package com.example.serviceserver.single;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.http.impl.headers.HeadersMultiMap;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public class HeadersMultiMapToJsonCodec implements MessageCodec<HeadersMultiMap, JsonObject> {
  @Override
  public void encodeToWire(Buffer buffer, HeadersMultiMap entries) {
    JsonObject json = transform(entries);
    buffer.appendBuffer(Json.encodeToBuffer(json));
  }

  @Override
  public JsonObject decodeFromWire(int i, Buffer buffer) {
    buffer.getBuffer(i, buffer.length());
    return buffer.toJsonObject();
  }

  @Override
  public JsonObject transform(HeadersMultiMap entries) {
    JsonObject json = new JsonObject();
    for (Map.Entry<String, String> entry : entries.entries()) {
      json.put(entry.getKey(), entry.getValue());
    }
    return json;
  }

  @Override
  public String name() {
    return "HeadersMultiMapToJsonCodec";
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
