hermes-jsonrpc [![Build Status](https://travis-ci.org/knightliao/hermes-jsonrpc.svg?branch=master)](https://travis-ci.org/knightliao/hermes-jsonrpc) [![Coverage Status](https://coveralls.io/repos/knightliao/hermes-jsonrpc/badge.png)](https://coveralls.io/r/knightliao/hermes-jsonrpc)
==============

A Java-Version light-weight Json RPC framework based on GSON/Protostuff.

基于GSON/Protostuff的轻量级Java Json Rpc框架

hermes-jsonrpc当前最新版本：

- 1.0.1

在Maven Central Repository里查看 [com.github.knightliao.hermesjsonrpc](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.knightliao.hermesjsonrpc )


## 项目信息 ##

- Java项目(1.6+)
- Maven管理(3.0.5+)

## 它是什么? ##

- 命名为hermes-jsonrpc
- 专注于高效、高速、简单的 Java Json Rpc 编程

## Features ##

- HTTP协议，JSON格式，具体协议规范可参见: [https://github.com/knightliao/docs/blob/master/baidu/rpc/rpcspec.md](https://github.com/knightliao/docs/blob/master/baidu/rpc/rpcspec.md)
- 压缩协议: GSON 或 Protostuff
- Client编程方式：
	- 支持Spring AOP代理方式请求方式
- Server编程方式：
	- 支持Spring Web Servlet接受请求方式
	- 支持安全的 用户名/密码验证(无法反算)
	- 支持白名单
	- 支持接受GET请求，它显示接口信息。GET请求支持白名单
- 同步调用，不支持异步调用

## 协议规范示例 ##

client request: 

	{"id":"8754","method":"getRandom","params":[6],"jsonrpc":"2.0"}

server response:
	
	{"jsonrpc":"2.0","result":358255,"id":"38197"}

## 使用 ##

在您的 Maven POM 文件里加入：

client:

    <dependency>
        <groupId>com.github.knightliao.hermesjsonrpc</groupId>
        <artifactId>hermes-jsonrpc-client</artifactId>
        <version>1.0.1</version>
    </dependency>

server:

    <dependency>
        <groupId>com.github.knightliao.hermesjsonrpc</groupId>
        <artifactId>hermes-jsonrpc-server</artifactId>
        <version>1.0.1</version>
    </dependency>

主要依赖为：

- apollo(1.0.1)

### Tutorials ###

- [Tutorial 1 Json RPC Server撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial1)
- [Tutorial 2 Json RPC Client 撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial2)
- [Tutorial 3 带有权限验证和IP白名单的 Json RPC Server撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial3)
- [Tutorial 4 请求带有权限验证 Json RPC Client 撰写方法（最佳实践）](https://github.com/knightliao/hermes-jsonrpc/wiki/Tutorial4)
	
## 局限性 ##

- 服务和客户端均只能是Java语言编程
- 不支持异步调用 
