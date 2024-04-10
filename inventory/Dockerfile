FROM amazoncorretto:21.0.2

ARG WORKING_DIR=/onlineshop
RUN mkdir ${WORKING_DIR}

COPY /build/libs/inventory.jar /onlineshop

WORKDIR ${WORKING_DIR}

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar inventory.jar"]