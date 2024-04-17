FROM eclipse-temurin:17-jdk

WORKDIR /opt/app

ENV package=edge

COPY target/universal/$package.zip .

RUN unzip $package.zip && mv $package/* . && mv bin/$package bin/start && rm -r $package $package.zip

ENTRYPOINT ["bin/start"]
