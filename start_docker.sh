#!/bin/bash

# 기존 dangling 이미지 삭제
docker image prune -f

# 기존 컨테이너 강제 종료 및 삭제
docker rm -f flash-sale-container

# 이미지 빌드
docker build -t flash-sale-event .

# logs 디렉터리가 없으면 생성
mkdir -p ./logs

# 컨테이너 실행 (CPU & 메모리 제한 포함)
docker run -d \
  --name flash-sale-container \
  --cpus="2" \
  --memory="1024m" \
  -p 8080:8080 \
  -v $(pwd)/logs:/app/logs \
  flash-sale-event
