FROM eclipse-temurin:17-jdk

WORKDIR /opt/app

ENV package=edge

COPY target/universal/edge.zip .

RUN unzip $package.zip && mv $package/* . && mv bin/$package bin/start

ENTRYPOINT ["bin/start"]