package com.example.serviceserver.single;

import com.example.serviceserver.single.DomainModel.EkiModoki.CheckIn;
import com.example.serviceserver.single.DomainModel.EkiModoki.Player;
import com.example.serviceserver.single.DomainModel.EkiModoki.Players;
import com.example.serviceserver.single.DomainModel.EkiModoki.Stations;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

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
    vertx.eventBus().consumer("command", message -> {

      JsonObject msg = (JsonObject) message.body();
      System.out.println("EkiModokiCommandProcessorVerticle Received command: " + msg.toString());


      Player p = players.findPlayerById(Integer.parseInt(msg.getString("id")));
      CheckIn ci = p.doCheckIn(Double.parseDouble(msg.getString("latitude")),
          Double.parseDouble(msg.getString("longitude")));

      message.reply("Command processed! return value is " + ci.getClass().getName());
    });
    System.out.println("CommandProcessorVerticle started!");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("CommandProcessorVerticle stopped!");
  }
}
