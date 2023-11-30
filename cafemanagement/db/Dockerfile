# Use the official MySQL image as the base image
FROM mysql:latest

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=admin123
ENV MYSQL_DATABASE=cafemanagement
ENV MYSQL_USER=testuser
ENV MYSQL_PASSWORD=testuser@123



# Copy the SQL script to initialize the database
COPY db.sql /docker-entrypoint-initdb.d/db.sql
