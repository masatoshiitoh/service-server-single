package com.example.serviceserver.single.Model.EkiModoki;

import java.time.LocalDateTime;
import java.util.HashMap;

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
    // 前回チェックイン時刻に5分を追加した値が、現時刻よりも後なら、待機時間が残っている
    if (checkInHistory.containsKey(station.id)
        && checkInHistory.get(station.id).plusMinutes(5).isAfter(LocalDateTime.now())) {
      // 待機時間がまだ残っているなら、その駅には何もできない
      System.out.println("プレイヤー" + this.id + ": 待機時間がまだ残ってる: " + station.name);
      return new NoCheckIn();
    }
    // 5分経過した、または、そのstation.idは存在しない、という場合の処理
    // チェックイン履歴に追加する
    System.out.println("プレイヤー" + this.id + ": チェックイン！ " + station.name);
    checkInHistory.put(station.id, LocalDateTime.now());



    return new AttackCheckIn(this.id, this.id, station.id, LocalDateTime.now()); // 第2引数は本来攻撃を受けた側（保持プレイヤー）のID
  }
}
