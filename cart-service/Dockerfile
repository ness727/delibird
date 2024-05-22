FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/cart-service-1.0.jar CartService.jar
COPY sleep_cart.sh /sleep_cart.sh
RUN chmod +x /sleep_cart.sh
ENTRYPOINT ["/sleep_cart.sh"]