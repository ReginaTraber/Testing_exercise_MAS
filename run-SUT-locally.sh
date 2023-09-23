#!/bin/bash

VERSION=1.0.3

docker pull sadsamba/heatclinic:$VERSION
docker run -it --rm -p 8080:8080 -p 8443:8443 -p 9001:9001 sadsamba/heatclinic:$VERSION
