FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/login-service-1.0.jar LoginService.jar
COPY sleep_login.sh /sleep_login.sh
RUN chmod +x /sleep_login.sh
ENTRYPOINT ["/sleep_login.sh"]