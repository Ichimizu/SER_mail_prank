FROM openjdk:8-jre-alpine

COPY ./target/MockMock-1.4.0.one-jar.jar /opt/app/mockmock.jar

EXPOSE 8080
EXPOSE 2525

CMD ifconfig; java -jar /opt/app/mockmock.jar -p 2525 -h 8080