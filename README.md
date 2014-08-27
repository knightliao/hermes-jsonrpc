hermes-jsonrpc [![Build Status](https://travis-ci.org/knightliao/hermes-jsonrpc.svg?branch=master)](https://travis-ci.org/knightliao/hermes-jsonrpc)
==============

A Java-Version light-weight Json RPC framework based on GSON/Protostuff.

基于GSON/Protostuff的轻量级Java Json Rpc框架

hermes-jsonrpc当前最新版本：

- 1.0.0

在Maven Central Repository里查看[com.github.knightliao.hermesjsonrpc](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.knightliao.hermesjsonrpc )


## 项目信息 ##

- Java项目(1.6+)
- Maven管理(3.0.5+)

## 它是什么? ##

- 命名为hermes-jsonrpc
- 专注于高效、高速、简单的 Java Json Rpc 编程

## Features ##

- Use GSON.
- Use simple http connection to send/receive packages.
- 压缩协议: 无 或 Protostuff, 目前还不支持 Protostuff
- Client编程方式：
	- 支持Spring AOP代理方式
- Server编程方式：
	- 支持Spring Web Servlet方式
- 同步调用 

## 使用 ##

在您的 Maven POM 文件里加入：

client:

    <dependency>
        <groupId>com.github.knightliao.hermesjsonrpc</groupId>
        <artifactId>hermes-jsonrpc-client</artifactId>
        <version>1.0.0</version>
    </dependency>

server:

    <dependency>
        <groupId>com.github.knightliao.hermesjsonrpc</groupId>
        <artifactId>hermes-jsonrpc-server</artifactId>
        <version>1.0.0</version>
    </dependency>

主要依赖为：

- apollo(1.0.0)

### Tutorials ###

- [Tutorial 1 Json RPC Server撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial1)
- [Tutorial 2 Json RPC Client 撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial2)
	
## 局限性 ##

- 服务和客户端均只能是Java语言编程
- 不支持异步调用 

## Authors ##

- [Qiqi Liao](https://github.com/knightliao): knightliao AT gmail.com