#!/usr/bin/env bash
set -euo pipefail
# Script to build docker container from directory

export IMAGE_REGISTRY=${IMAGE_REGISTRY:-denis256/}
export PUSH_IMAGE=${PUSH_IMAGE:-1}

cd "$(dirname "$0")"

function usage() {
  echo "Script to build docker image with IoT application"
  echo
  echo "Usage: "
  echo "$0 <directory-to-build>"
}

if [[ "$#" -eq 0 ]]; then
  usage
  exit 0
fi

dir="$1"

echo "Building ${dir}"
cd "${dir}"

tag=$(git rev-parse --short=5 HEAD)

export CONTAINER_IMAGE="${IMAGE_REGISTRY}${dir}:${tag}"
export CONTAINER_IMAGE_LATEST="${IMAGE_REGISTRY}${dir}:latest"

../gradlew clean build

docker build -t "${CONTAINER_IMAGE}" .
docker tag "${CONTAINER_IMAGE}" "${CONTAINER_IMAGE_LATEST}"

if [[ "${PUSH_IMAGE}" == "1" ]]; then
  docker push "${CONTAINER_IMAGE}"
  docker push "${CONTAINER_IMAGE_LATEST}"
fi

echo "Built image ${CONTAINER_IMAGE}"

