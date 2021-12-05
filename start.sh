#!/bin/sh -x
dir=$(cd `dirname $0`;pwd)
echo $dir
mvn clean package -Dmaven.test.skip=true  && \
java -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=y \
-jar $dir/target/arthas-demo.jar


# 参考 http://kail.xyz/Troubleshooting/docs/Arthas/Case/Slow-Start/