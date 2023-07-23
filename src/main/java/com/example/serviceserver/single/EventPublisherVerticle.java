package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;

public class EventPublisherVerticle extends AbstractVerticle {

  // Kafkaから受信したイベントを適切な送信先にイベントバス経由で送るverticleです

  @Override
  public void start() throws Exception {
    System.out.println("EventPublisherVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("EventPublisherVerticle stopped!");
  }
}
