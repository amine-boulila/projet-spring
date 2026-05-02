-- Create the classroomdb database if it doesn't exist
-- Run this script using: psql -U postgres -f setup-database.sql

-- Connect to postgres database first
\c postgres

-- Drop database if exists (optional - uncomment if you want to start fresh)
-- DROP DATABASE IF EXISTS classroomdb;

-- Create the database
CREATE DATABASE classroomdb;

-- Connect to the new database
\c classroomdb

-- Grant all privileges to postgres user
GRANT ALL PRIVILEGES ON DATABASE classroomdb TO postgres;

-- Display success message
\echo 'Database classroomdb created successfully!'
