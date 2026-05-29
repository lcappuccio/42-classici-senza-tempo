FROM gradle:jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle teavm:buildDist --no-daemon

FROM nginx:alpine
COPY --from=build /app/teavm/build/dist /usr/share/nginx/html
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
