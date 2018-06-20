# 附录 A Debian/Ubuntu18.04开发环境完美配置

## A.1 安装vim

```
sudo apt install vim
```

## A.2 更换系统源为清华大学源

1. 编辑ubuntu 18.04 源
```
sudo vim /etc/apt/sources.list
```
2. 替换为如下内容
```
# 默认注释了源码镜像以提高 apt update 速度，如有需要可自行取消注释
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse

# 预发布软件源，不建议启用
# deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
```

## A.3 更新软件
```
sudo apt update
sudo apt upgrade
```

## A.2 安装搜狗输入法

## A.3 安装编辑器Atom

## A.4 安装JDK开发环境

## A.5 安装Java开发利器IntelliJ IDEA

## A.6 安装world文档、PPT、excel工具WPS

## A.7 GIMP
