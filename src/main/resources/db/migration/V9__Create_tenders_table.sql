
CREATE TABLE IF NOT EXISTS `tenders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tender_number` VARCHAR(255) NOT NULL UNIQUE,
  `title` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `publish_date` VARCHAR(50),
  `closing_date` VARCHAR(50),
  `status` VARCHAR(50) DEFAULT 'OPEN',
  `description` TEXT,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
