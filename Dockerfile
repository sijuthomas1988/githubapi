# The base image
FROM ubuntu:latest

MAINTAINER SIJUMON KARYIL RAJU <sijuthomas1988@gmail.com>
LABEL Description="Github API Image"

COPY target/githubapi.jar /githubapi.jar

CMD echo Docker container started.
CMD exec java -jar /githubapi.jar
EXPOSE   8080
