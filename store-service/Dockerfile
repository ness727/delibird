FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/store-service-1.0.jar StoreService.jar
COPY sleep_store.sh /sleep_store.sh
RUN chmod +x /sleep_store.sh
ENTRYPOINT ["/sleep_store.sh"]