package com.example.serviceserver.single;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.HashMap;
import java.util.Map;

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
  //
  // 今回の試し実装
  // チェックインイベントをもとに、Stationsテーブルを更新する

  KafkaConsumer<String, String> kafkaConsumer;


  MySQLConnectOptions connectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
      .setDatabase("ekimodoki").setUser("root").setPassword("rootpass");

  PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

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
      System.out.println("EventToDbTransferVerticle: Subscribed to checkin topic");
    }).onFailure(err -> {
      System.out.println("EventToDbTransferVerticle: Failed to subscribe to checkin topic");
    });

    kafkaConsumer.handler(record -> {
      JsonObject json = new JsonObject(record.value());
      String playerId = json.getString("playerId");
      String checkInTime = json.getString("checkInTime");
      String stationId = json.getString("stationId");
      // Kafka経由でpubsubされてきた情報をDBに書き込む
      SqlClient client = MySQLPool.pool(vertx, connectOptions, poolOptions);
      client
          .preparedQuery(
              "UPDATE stations SET kafka_index = ?, own_player = ?, own_start_at = ? WHERE id = ?")
          .execute(Tuple.of(record.offset(), Integer.parseInt(playerId), checkInTime,
              Integer.parseInt(stationId)))
          .onComplete(ar -> {
            if (ar.succeeded()) {
              System.out.println("EventToDbTransferVerticle: Got " + ar.result().size() + " rows ");
            } else {
              System.out.println("EventToDbTransferVerticle: Failure: " + ar.cause().getMessage());
            }
          });
      System.out.println(
          "EventToDbTransferVerticle: offset: " + record.offset() + ", message: " + record.value());
    });



    System.out.println("EventToDbTransferVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    kafkaConsumer.close();
    System.out.println("EventToDbTransferVerticle stopped!");
  }
}
