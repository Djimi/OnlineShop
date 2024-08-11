FROM amazoncorretto:21.0.4

RUN yum -y update && yum -y install coreutils net-tools telnet procps

ARG WORKING_DIR=/onlineshop
RUN mkdir /${WORKING_DIR}

COPY /build/libs/inventory.jar /${WORKING_DIR}

WORKDIR ${WORKING_DIR}

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar inventory.jar"]