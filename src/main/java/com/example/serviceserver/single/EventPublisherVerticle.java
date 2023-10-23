package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Map;

public class EventPublisherVerticle extends AbstractVerticle {

  // Kafkaからsubscribeで受信したイベントを適切な送信先にイベントバス経由で送るverticleです
  // 適切とは？

  KafkaConsumer<String, String> kafkaConsumer;

  @Override
  public void start() throws Exception {
    Map<String, String> config = new HashMap<>();
    config.put("bootstrap.servers", "localhost:9092");
    config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    config.put("group.id", "eki-modoki");
    config.put("auto.offset.reset", "earliest");
    config.put("enable.auto.commit", "false");

    kafkaConsumer = KafkaConsumer.create(vertx, config);

    kafkaConsumer.subscribe("checkin").onSuccess(ok -> {
      System.out.println("EventPublisherVerticle: Subscribed to checkin topic");
    }).onFailure(err -> {
      System.out.println("EventPublisherVerticle: Failed to subscribe to checkin topic");
    });

    kafkaConsumer.handler(record -> {
      JsonObject json = new JsonObject(record.value());
      String playerId = json.getString("playerId");
      String checkInTime = json.getString("checkInTime");
      String stationId = json.getString("stationId");
      // ここでイベントを必要な宛先に届ける
    });
    System.out.println("EventPublisherVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    kafkaConsumer.close();
    System.out.println("EventPublisherVerticle stopped!");
  }
}
