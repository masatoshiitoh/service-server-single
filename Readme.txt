


---
DB関連

CREATE TABLE `stations` (
  `id` int NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `own_player` int DEFAULT NULL,
  `own_start_at` datetime DEFAULT NULL,
  `kafka_index` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin

INSERT INTO stations (id, name) VALUES
(1, '東京'),
(2, '神田'),
(3, '秋葉原'),
(4, '御茶ノ水'),
(5, '水道橋'),
(6, '飯田橋'),
(7, '市ケ谷'),
(8, '四ツ谷'),
(9, '新宿'),
(10, '代々木'),
(11, '原宿'),
(12, '渋谷'),
(13, '恵比寿'),
(14, '目黒'),
(15, '五反田'),
(16, '大崎'),
(17, '品川'),
(18, '田町'),
(19, '浜松町'),
(20, '新橋');


CREATE TABLE master_stations (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);


INSERT INTO master_stations (id, name, latitude, longitude) VALUES
(1, '東京', 35.681382, 139.766084),
(2, '神田', 35.69169, 139.770883),
(3, '秋葉原', 35.698683, 139.774219),
(4, '御茶ノ水', 35.699619, 139.764961),
(5, '水道橋', 35.702159, 139.765964),
(6, '飯田橋', 35.701306, 139.753216),
(7, '市ケ谷', 35.694069, 139.735318),
(8, '四ツ谷', 35.686041, 139.730644),
(9, '新宿', 35.689592, 139.700413),
(10, '代々木', 35.683061, 139.702042),
(11, '原宿', 35.670399, 139.702242),
(12, '渋谷', 35.658517, 139.701334),
(13, '恵比寿', 35.646685, 139.710106),
(14, '目黒', 35.633903, 139.715976),
(15, '五反田', 35.625974, 139.72399),
(16, '大崎', 35.6197, 139.728553),
(17, '品川', 35.62876, 139.738926),
(18, '田町', 35.645736, 139.747575),
(19, '浜松町', 35.655646, 139.75761),
(20, '新橋', 35.666195, 139.758858);
