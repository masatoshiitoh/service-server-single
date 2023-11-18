package com.example.serviceserver.single.Model.EkiModoki;

/**
 * 駅のアクター？ イベントはチェックイン操作になる、ユーザーの状態ではなく、駅の状態が「アクター」として保持される必要がある？
 * 駅メモでは、チェックイン時の編成が必要で、、、って駅の状態に「そこにチェックインしてる編成とオーナー」の情報をもたせればいいのか
 * 実際の駅メモは、チェックインした編成で、どのでんこでチェックインしたかをもってる（HP情報は「でんこ」ごとに保持され、複数駅での攻撃は一体に対して行われる）
 * 構成的には、プレイヤー操作→駅への攻撃→駅を持ってるでんこに対する攻撃成否判定→駅のオーナー変更→駅のオーナー変更通知→駅のオーナー変更通知を受け取ったプレイヤーの駅リスト更新
 * 攻撃を受けたときの評価は、「駅にどんな編成で待ち受けているか」の情報次第。駅のオーナーが変わったときに、その駅にいるでんこの情報を更新する必要がある
 */
public class Station {
  public int id;
  public String name;
  public double latitude;
  public double longitude;
  public int currentOwnerPlayerId;

  public Station(int id, String name, double latitude, double longitude) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

}
