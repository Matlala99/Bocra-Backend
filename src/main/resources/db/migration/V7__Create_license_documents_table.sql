
CREATE TABLE IF NOT EXISTS `license_documents` (
  `license_id` BIGINT NOT NULL,
  `document_url` VARCHAR(255) NOT NULL,
  CONSTRAINT `fk_license_documents_license_id` FOREIGN KEY (`license_id`) REFERENCES `license_applications` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
