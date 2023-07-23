package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;

public class EventToDbTransferVerticle extends AbstractVerticle {

  // Kafkaからのイベントを受信して、クエリ環境DBに転送するバーティクルです
  // ああ、ここが一番忙しいな

  @Override
  public void start() throws Exception {
    System.out.println("EventToDbTransferVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("EventToDbTransferVerticle stopped!");
  }
}
