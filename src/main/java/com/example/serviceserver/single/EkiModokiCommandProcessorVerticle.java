package com.example.serviceserver.single;

import com.example.serviceserver.single.Model.EkiModoki.CheckIn;
import com.example.serviceserver.single.Model.EkiModoki.Player;
import com.example.serviceserver.single.Model.EkiModoki.Players;
import com.example.serviceserver.single.Model.EkiModoki.Stations;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

import java.util.HashMap;
import java.util.Map;


// イベントバスとのやりとりをおこなうバーティクルです
public class EkiModokiCommandProcessorVerticle extends AbstractVerticle {

  // イベントバスから受け取ったコマンドをEvaluatorで評価して、
  // >> OKならKafkaに保管して、保管が完了したらリクエスト元に成功を返す
  // >> NGなら、リクエスト元には失敗を返す
  // という処理をおこなうバーティクルです

  Players players = new Players();
  Stations stations = new Stations();

  @Override
  public void start() throws Exception {
    Map<String, String> config = new HashMap<>();
    config.put("bootstrap.servers", "localhost:9092");
    config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    config.put("acks", "1");
    KafkaProducer<String, String> producer = KafkaProducer.create(vertx, config);


    vertx.eventBus().consumer("command", message -> {
      JsonObject msg = (JsonObject) message.body();
      System.out.println("EkiModokiCommandProcessorVerticle Received command: " + msg.toString());


      Player p = players.findPlayerById(Integer.parseInt(msg.getString("id")));
      CheckIn ci = p.doCheckIn(Double.parseDouble(msg.getString("latitude")),
          Double.parseDouble(msg.getString("longitude")));
      // TODO:このCheckIn結果を保存する（NoCheckInは何もしない、AttackCheckInは攻撃を記録する）
      System.out.println("returned CheckIn class name: " + ci.getClass().getName());
      switch (ci.getClass().getSimpleName())// CheckIn.makePersistentJson()でJSONに変換できる。NoCheckInはnullを返す
      {
        case "NoCheckIn":
          System.out.println("NoCheckIn");
          break;
        case "AttackCheckIn":
          System.out.println("AttackCheckIn");
          JsonObject json = ci.makePersistentJson();
          KafkaProducerRecord<String, String> record =
              KafkaProducerRecord.create("checkin", json.toString());
          producer.send(record).onSuccess(metadata -> {
            System.out.println("Kafkaに書き込みました");
          }).onFailure(err -> {
            System.out.println("Kafkaへの書き込みに失敗しました");
          });
          break;
        default:
          throw new RuntimeException("CheckInのサブクラスが増えたらここも修正する必要がある");
      }

      message.reply("Command processed! return value is " + ci.getClass().getName());
    });
    System.out.println("CommandProcessorVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("CommandProcessorVerticle stopped!");
  }
}
