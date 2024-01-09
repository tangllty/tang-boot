FROM redis:7.2.3
# author
LABEL authors="Tang"

# create directory
RUN mkdir -p /opt/redis
# mount directory
VOLUME /opt/redis
# specify path
WORKDIR /opt/redis
# copy conf file to path
COPY ./conf/redis.conf /opt/redis/redis.conf