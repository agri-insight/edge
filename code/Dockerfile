# Stage 1: Build stage
FROM sbtscala/scala-sbt:eclipse-temurin-focal-17.0.5_8_1.9.4_3.3.0 AS build
WORKDIR /opt/build

# Copy only the build files needed for resolving dependencies
COPY build.sbt ./
COPY project/ project/
RUN sbt update

# Copy the entire source code and build
COPY . ./
RUN sbt universal:packageZipTarball

ENV package=agent

WORKDIR target/universal

RUN tar -xf $package.tgz &&  \
    mv $package/* . &&  \
    mv bin/$package bin/start &&  \
    rm -r $package $package.tgz

# Stage 2: Final stage
FROM eclipse-temurin:17-jre AS final
WORKDIR /opt/app/bin

# Copy the assembled JAR from the build stage
COPY --from=build /opt/build/target/universal/ ./

# Define the entrypoint
ENTRYPOINT ["bin/start"]
