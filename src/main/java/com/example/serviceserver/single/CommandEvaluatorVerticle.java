package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;

public class CommandEvaluatorVerticle extends AbstractVerticle {

  // イベントバスから受け取ったコマンドを評価して結果を返す。保存はおこなわず、関数的に動作するverticleです
  @Override
  public void start() throws Exception {
    System.out.println("CommandEvaluatorVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("CommandEvaluatorVerticle stopped!");
  }
}
