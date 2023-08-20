package com.example.serviceserver.single.DomainModel.EkiModoki;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
  public int id;
  public String name;
  public HashMap<Integer, LocalDateTime> checkInHistory = new HashMap<Integer, LocalDateTime>();

  public Player(int id) {
    this.id = id;
    this.name = "";
  }

  public CheckIn doCheckIn(double checkInLatitude, double checkInLongitude) {
    // ここでチェックイン処理を実行する
    // 最寄り駅を判定する
    Station station = Stations.findNearestStation(checkInLatitude, checkInLongitude);
    if (station == null) {
      System.out.println("プレイヤー" + this.id + ": 駅が見つからない");
      return new NoCheckIn();
    }
    // 待機時間経過チェック
    if (checkInHistory.containsKey(station.id)
        && checkInHistory.get(station.id).plusMinutes(5).isAfter(LocalDateTime.now())) {
      // 待機時間がまだ残ってる
      System.out.println("プレイヤー" + this.id + ": 待機時間がまだ残ってる: " + station.name);
      return new NoCheckIn();
    }

    // チェックイン履歴に追加する
    System.out.println("プレイヤー" + this.id + ": チェックイン！ " + station.name);
    checkInHistory.put(station.id, LocalDateTime.now());
    return new AttackCheckIn();

  }

}
