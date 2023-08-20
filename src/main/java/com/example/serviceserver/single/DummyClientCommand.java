package com.example.serviceserver.single;

import java.net.URLEncoder;

public class DummyClientCommand {
  public String Command;
  public double Latitude;
  public double Longitude;
  public int playerId;
  public String Comment;

  public DummyClientCommand(String command, double latitude, double longitude, int playerId,
      String comment) {
    this.Command = command;
    this.Latitude = latitude;
    this.Longitude = longitude;
    this.playerId = playerId;
    this.Comment = comment;
  }

  public String getPathParameters() {
    return String.format("command=%s&id=%d&latitude=%f&longitude=%f&comment=%s", this.Command,
        this.playerId, this.Latitude, this.Longitude,
        URLEncoder.encode(this.Comment, java.nio.charset.StandardCharsets.UTF_8));
  }
}
