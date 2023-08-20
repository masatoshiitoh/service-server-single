package com.example.serviceserver.single;

import java.util.List;

public class DummyClientCommands {
  public static List<DummyClientCommand> commands =
      List.of(new DummyClientCommand("checkin", 35.681167, 139.767052, 1, "default 1"),
          new DummyClientCommand("checkin", 35.69169, 139.770883, 2, "神田 2"), // 神田 2
          new DummyClientCommand("checkin", 35.686041, 139.730644, 3, "四谷 3"), // 四谷 3
          new DummyClientCommand("checkin", 35.698683, 139.774219, 1, "秋葉原 1"), // 秋葉原 1
          new DummyClientCommand("checkin", 35.625974, 139.72399, 2, "五反田 2"), // 五反田 2
          new DummyClientCommand("checkin", 35.683061, 139.702042, 3, "代々木 3"), // 代々木 3
          new DummyClientCommand("checkin", 35.681382, 139.766084, 1, "東京 1"), // 東京 1
          new DummyClientCommand("checkin", 35.633903, 139.715976, 2, "目黒 2"), // 目黒 2
          new DummyClientCommand("checkin", 35.689592, 139.700413, 3, "新宿 3") // 新宿 3
      );



}
