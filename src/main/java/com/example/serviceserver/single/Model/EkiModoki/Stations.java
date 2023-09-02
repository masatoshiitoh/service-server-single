package com.example.serviceserver.single.Model.EkiModoki;

// 駅の一覧
public class Stations {

  public static Station[] stations = {new Station(1, "東京", 35.681382, 139.766084),
      new Station(2, "神田", 35.69169, 139.770883), new Station(3, "秋葉原", 35.698683, 139.774219),
      new Station(4, "御茶ノ水", 35.699619, 139.764961), new Station(5, "水道橋", 35.702159, 139.765964),
      new Station(6, "飯田橋", 35.701306, 139.753216), new Station(7, "市ケ谷", 35.694069, 139.735318),
      new Station(8, "四ツ谷", 35.686041, 139.730644), new Station(9, "新宿", 35.689592, 139.700413),
      new Station(10, "代々木", 35.683061, 139.702042), new Station(11, "原宿", 35.670399, 139.702242),
      new Station(12, "渋谷", 35.658517, 139.701334), new Station(13, "恵比寿", 35.646685, 139.710106),
      new Station(14, "目黒", 35.633903, 139.715976), new Station(15, "五反田", 35.625974, 139.72399),
      new Station(16, "大崎", 35.6197, 139.728553), new Station(17, "品川", 35.62876, 139.738926),
      new Station(18, "田町", 35.645736, 139.747575), new Station(19, "浜松町", 35.655646, 139.75761),
      new Station(20, "新橋", 35.666195, 139.758858)};

  public static Station findNearestStation(double latitude, double longitude) {
    Station nearestStation = null;
    double minDistance = Double.MAX_VALUE;
    for (Station station : stations) {
      double distance = calcDistance(latitude, longitude, station.latitude, station.longitude);
      if (distance < minDistance) {
        minDistance = distance;
        nearestStation = station;
      }
    }
    return nearestStation;
  }

  public static double calcDistance(double latitude1, double longitude1, double latitude2,
      double longitude2) {
    double distance = 0;
    double theta = longitude1 - longitude2;
    distance = Math.sin(deg2rad(latitude1)) * Math.sin(deg2rad(latitude2))
        + Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2)) * Math.cos(deg2rad(theta));
    distance = Math.acos(distance);
    distance = rad2deg(distance);
    distance = distance * 60 * 1.1515;
    distance = distance * 1.609344;
    return distance;
  }

  public static double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
  }

  public static double rad2deg(double rad) {
    return (rad * 180 / Math.PI);
  }
}
