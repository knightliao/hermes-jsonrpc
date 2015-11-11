hermes-jsonrpc [![Build Status](https://travis-ci.org/knightliao/hermes-jsonrpc.svg?branch=master)](https://travis-ci.org/knightliao/hermes-jsonrpc) [![Coverage Status](https://coveralls.io/repos/knightliao/hermes-jsonrpc/badge.png)](https://coveralls.io/r/knightliao/hermes-jsonrpc)
==============

A Java-Version light-weight HTTP-JSON RPC framework using GSON/Protostuff.

使用 GSON/Protostuff 的轻量级HTTP协议的Java Json Rpc框架

hermes-jsonrpc branches and Maven version:

- dev(develop branch): 1.0.3-SNAPSHOT
- master(stable branch)：1.0.2

在Maven Central Repository里查看 [com.github.knightliao.hermesjsonrpc](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.knightliao.hermesjsonrpc )


## 项目信息 ##

- Java项目(1.6+)
- Maven管理(3.0.5+)

## 它是什么? ##

- 命名为hermes-jsonrpc
- 专注于高效、高速、简单的 Java Json Rpc 编程

## Features ##

- 协议：HTTP
- 传输格式：JSON
- codec协议: GSON 或 Protostuff
- Client编程方式：
    - 支持Spring AOP代理方式请求方式
- Server编程方式：
    - 支持Spring Web Servlet接受请求方式
    - 支持安全的 用户名/密码验证(无法反算)
    - 支持白名单
    - 支持接受GET请求，它显示接口信息。GET请求支持白名单
- 同步调用，不支持异步调用

注：本框架遵循的规范可参见: [https://github.com/knightliao/docs/blob/master/baidu/rpc/rpcspec.md](https://github.com/knightliao/docs/blob/master/baidu/rpc/rpcspec.md)

## 协议规范示例 ##

###Java接口:####

    package com.github.knightliao.hermesjsonrpc.demo;
    
    /**
     * 
     * @author liaoqiqi
     * @version 2014-8-21
     */
    public interface DemoServiceDriver {
    
        public class Request {
    
            public Request(int maxValue, int minValue) {
                super();
                this.maxValue = maxValue;
                this.minValue = minValue;
            }
    
            private int maxValue;
    
            public int getMaxValue() {
                return maxValue;
            }
    
            public void setMaxValue(int maxValue) {
                this.maxValue = maxValue;
            }
    
            public int getMinValue() {
                return minValue;
            }
    
            public void setMinValue(int minValue) {
                this.minValue = minValue;
            }
    
            private int minValue;
        }
    
        public class Response {
    
            public Response(int value) {
                super();
                this.value = value;
            }
    
            private int value;
    
            public int getValue() {
                return value;
            }
    
            public void setValue(int value) {
                this.value = value;
            }
        }
    
        Response getRandom(Request seed);
    }

###client request示例（在没有权限控制的情况下）: ###

    curl -d '{"id":"99999","version":"2.0","method":"getRandom","params":[{"maxValue":0,"minValue":50}]}' http://127.0.0.1:8085/export/DemoServiceDriver -H 'Content-Type: application/json-hermes-gson'

###server response示例（在没有权限控制的情况下）:###
    
    {"result":{"value":32},"version":"2.0","id":99999}

## 使用 ##

在您的 Maven POM 文件里加入：

client:

    <dependency>
        <groupId>com.github.knightliao.hermesjsonrpc</groupId>
        <artifactId>hermes-jsonrpc-client</artifactId>
        <version>1.0.2</version>
    </dependency>

server:

    <dependency>
        <groupId>com.github.knightliao.hermesjsonrpc</groupId>
        <artifactId>hermes-jsonrpc-server</artifactId>
        <version>1.0.2</version>
    </dependency>

### Tutorials ###

- [Tutorial 1 Json GSON RPC Server撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial1)
- [Tutorial 2 Json GSON RPC Client 撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial2)
- [Tutorial 3 带有权限验证和IP白名单的 Json GSON RPC Server撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial3)
- [Tutorial 4 请求带有权限验证 Json GSON RPC Client 撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial4)
- [Tutorial 5 Json Protostuff RPC 撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial5)
    
### dependency

#### client:

    +- com.github.knightliao.hermesjsonrpc:hermes-jsonrpc-core:jar:1.0.3:compile
    [INFO] |  +- commons-codec:commons-codec:jar:1.9:compile
    [INFO] |  +- commons-httpclient:commons-httpclient:jar:3.1:compile
    [INFO] |  |  \- commons-logging:commons-logging:jar:1.0.4:compile
    [INFO] |  +- com.dyuproject.protostuff:protostuff-api:jar:1.0.8:compile
    [INFO] |  +- com.dyuproject.protostuff:protostuff-json:jar:1.0.8:compile
    [INFO] |  |  \- org.codehaus.jackson:jackson-core-asl:jar:1.9.8:compile
    [INFO] |  \- com.dyuproject.protostuff:protostuff-runtime:jar:1.0.8:compile
    [INFO] |     \- com.dyuproject.protostuff:protostuff-collectionschema:jar:1.0.8:compile
    [INFO] +- commons-collections:commons-collections:jar:3.2:compile
    [INFO] +- org.springframework:spring-beans:jar:4.1.7.RELEASE:compile
    [INFO] |  \- org.springframework:spring-core:jar:4.1.7.RELEASE:compile
    [INFO] +- com.google.code.gson:gson:jar:2.3:compile
    [INFO] +- org.slf4j:slf4j-api:jar:1.7.6:compile

#### server:

    +- com.github.knightliao.hermesjsonrpc:hermes-jsonrpc-core:jar:1.0.3:compile
    [INFO] |  +- commons-codec:commons-codec:jar:1.9:compile
    [INFO] |  +- com.dyuproject.protostuff:protostuff-api:jar:1.0.8:compile
    [INFO] |  +- com.dyuproject.protostuff:protostuff-json:jar:1.0.8:compile
    [INFO] |  |  \- org.codehaus.jackson:jackson-core-asl:jar:1.9.8:compile
    [INFO] |  \- com.dyuproject.protostuff:protostuff-runtime:jar:1.0.8:compile
    [INFO] |     \- com.dyuproject.protostuff:protostuff-collectionschema:jar:1.0.8:compile
    [INFO] +- org.springframework:spring-beans:jar:4.1.7.RELEASE:compile
    [INFO] |  \- org.springframework:spring-core:jar:4.1.7.RELEASE:compile
    [INFO] +- org.springframework:spring-context:jar:4.1.7.RELEASE:compile
    [INFO] |  +- org.springframework:spring-aop:jar:4.1.7.RELEASE:compile
    [INFO] |  |  \- aopalliance:aopalliance:jar:1.0:compile
    [INFO] |  \- org.springframework:spring-expression:jar:4.1.7.RELEASE:compile
    [INFO] +- org.springframework:spring-web:jar:4.1.7.RELEASE:compile
    [INFO] +- javax.servlet:servlet-api:jar:2.4:provided
    [INFO] +- com.google.code.gson:gson:jar:2.3:compile
    [INFO] +- commons-lang:commons-lang:jar:2.4:compile
    [INFO] +- org.slf4j:slf4j-api:jar:1.7.6:compile

## 局限性 ##

- 服务和客户端均只能是Java语言编程
- 不支持异步调用 
