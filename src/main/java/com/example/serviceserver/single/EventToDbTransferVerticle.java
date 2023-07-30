package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;

public class EventToDbTransferVerticle extends AbstractVerticle {

  // Kafkaからのイベントを受信して、クエリ環境DBに転送するバーティクルです
  // ああ、ここが一番忙しいな
  // ただ、ゲームで言えばここは他人の情報とか、統計情報とかで、インゲームでプレイヤー操作に起因するいろいろな
  // データではないはず（というかインゲームでプレイヤーデータとして随時更新されるデータは参照用とは異なる）
  // うぬぬ？
  // MMOを想定してしまうと重い。いったんは駅メモ想定がいいとおもう
  // このあたり、ドメインモデルがキーワードになってたと思うので、かなり個別実装になるはず。
  // Akkaは個別実装というよりもクラスの処理として隠蔽できる仕掛けという認識。
  // なので、とりあえずトライアル実装としては、個別実装でいいかな

  @Override
  public void start() throws Exception {
    System.out.println("EventToDbTransferVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("EventToDbTransferVerticle stopped!");
  }
}
