package com.example.serviceserver.single.DomainModel.EkiModoki;

public class Station {
  public int id;
  public String name;
  public double latitude;
  public double longitude;

  public Station(int id, String name, double latitude, double longitude) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

}
