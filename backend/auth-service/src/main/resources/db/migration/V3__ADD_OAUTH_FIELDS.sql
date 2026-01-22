-- ============================================================================
-- V3__ADD_OAUTH_FIELDS.sql
-- Description: Add OAuth and authentication related columns
-- Database: PostgreSQL
-- ============================================================================

ALTER TABLE users
ADD COLUMN password VARCHAR(100),
ADD COLUMN google_id VARCHAR(255),
ADD COLUMN provider VARCHAR(10) NOT NULL DEFAULT 'LOCAL',
ADD COLUMN profile_picture VARCHAR(500),
ADD COLUMN enabled BOOLEAN NOT NULL DEFAULT TRUE;

-- Unique constraint for google_id
ALTER TABLE users
ADD CONSTRAINT uk_users_google_id UNIQUE (google_id);

-- Indexes for OAuth fields
CREATE INDEX idx_users_google_id ON users (google_id);
CREATE INDEX idx_users_provider ON users (provider);
