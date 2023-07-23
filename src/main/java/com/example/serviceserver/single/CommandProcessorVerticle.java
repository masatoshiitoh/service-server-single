package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;

// イベントバスとのやりとりをおこなうバーティクルです
public class CommandProcessorVerticle extends AbstractVerticle {

  // イベントバスから受け取ったコマンドをEvaluatorで評価して、
  // >> OKならKafkaに保管して、保管が完了したらリクエスト元に成功を返す
  // >> NGなら、リクエスト元には失敗を返す
  // という処理をおこなうバーティクルです
  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("command", message -> {
      System.out.println("Received command: " + message.body());
      message.reply("Command received!");
    });
    System.out.println("CommandProcessorVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("CommandProcessorVerticle stopped!");
  }
}
