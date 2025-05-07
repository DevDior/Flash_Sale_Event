#!/bin/bash

# 기존 dangling 이미지 삭제
docker image prune -f

# 기존 컨테이너 강제 종료 및 삭제
docker rm -f flash-sale-container redis-container

# logs 디렉터리가 없으면 생성
mkdir -p ./logs

# Docker 네트워크가 없으면 생성
docker network inspect flash-sale-net >/dev/null 2>&1 || \
  docker network create flash-sale-net

# Redis 컨테이너 실행 (같은 네트워크)
docker run -d \
  --name redis \
  --network flash-sale-net \
  -p 6379:6379 \
  redis:7.4.3

# 이미지 빌드
docker build -t flash-sale-event .

# 컨테이너 실행 (CPU & 메모리 제한 포함)
docker run -d \
  --name flash-sale-container \
  --network flash-sale-net \
  --cpus="2" \
  --memory="1024m" \
  -p 8080:8080 \
  -v $(pwd)/logs:/app/logs \
  flash-sale-event
