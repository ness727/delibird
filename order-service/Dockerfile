FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/order-service-1.0.jar OrderService.jar
COPY sleep_order.sh /sleep_order.sh
RUN chmod +x /sleep_order.sh
ENTRYPOINT ["/sleep_order.sh"]