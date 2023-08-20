package com.example.serviceserver.single.DomainModel.EkiModoki;

import java.util.ArrayList;
import java.util.List;

public class Players {
  List<Player> players = new ArrayList<Player>();

  public Player findPlayerById(int id) {
    for (Player player : players) {
      if (player.id == id) {
        return player;
      }

    }
    Player p = new Player(id);
    players.add(p);
    return p;
  }

  public boolean addPlayer(Player player) {
    if (findPlayerById(player.id) != null) {
      return false;
    }
    players.add(player);
    return true;
  }
}
