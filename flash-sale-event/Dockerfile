# 베이스 이미지: OpenJDK 21 slim 버전 사용 (필요에 따라 버전 조정)
FROM openjdk:21-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# 빌드한 jar 파일을 컨테이너로 복사
# 예를 들어, jar 파일 이름이 flash-sale-event.jar라면:
COPY build/libs/flash-sale-event.jar app.jar

# 컨테이너 실행 시 jar 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
