FROM amazoncorretto:21.0.2

ARG WORKING_DIR=/onlineshop
RUN mkdir ${WORKING_DIR}

COPY /build/libs/purchases.jar /onlineshop

WORKDIR ${WORKING_DIR}

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar purchases.jar"]