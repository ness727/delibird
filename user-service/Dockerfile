FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/user-service-1.0.jar UserService.jar
COPY sleep_user.sh /sleep_user.sh
RUN chmod +x /sleep_user.sh
ENTRYPOINT ["/sleep_user.sh"]