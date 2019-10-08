CREATE TABLE `order_cp` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `order_status` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'INIT',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
