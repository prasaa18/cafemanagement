# Build Stage
FROM node:14 AS ANGULAR_BUILD
WORKDIR /app
COPY . /app
RUN npm install
RUN npm run build

# Deployment Stage
FROM nginx:alpine
COPY --from=ANGULAR_BUILD /app/dist/Frontend /usr/share/nginx/html
COPY src/nginx/etc/conf.d/default.conf  /etc/nginx/conf/default.conf

# # Expose the port on which Nginx will listen


 # Start Nginx in the foreground
 CMD ["nginx", "-g", "daemon off;"]
