package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;

public class QueryApiVerticle extends AbstractVerticle {

  // イベントバスから受け取ったクエリを評価して結果を返すverticleです
  // 基本的にはクエリなので、冪等です（DB更新されたら返却値変わりますよ、当然）
  // バーティクル自体も更新せず冪等に動作します
  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("query", message -> {
      System.out.println("Received query: " + message.body());
      message.reply("Query received!");
    });
    System.out.println("QueryApiVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("QueryApiVerticle stopped!");
  }
}
