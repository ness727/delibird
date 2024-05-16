FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/gateway-service-1.0.jar GatewayService.jar
COPY sleep_gateway.sh /sleep_gateway.sh
RUN chmod +x /sleep_gateway.sh
ENTRYPOINT ["/sleep_gateway.sh"]