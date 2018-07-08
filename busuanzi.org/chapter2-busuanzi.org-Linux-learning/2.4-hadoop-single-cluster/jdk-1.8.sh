#!/bin/sh
# Author:wangxiaolei 王小雷
# Blog: http://blog.csdn.net/dream_an
# Github: https://github.com/wangxiaoleiai
# project: https://github.com/wangxiaoleiai/big-data
# Date: 2018.05
# Path: /etc/profile.d/

export JAVA_HOME=/opt/java/jdk1.8.0_172
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

