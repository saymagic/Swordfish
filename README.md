> 此文我将带大家用Docker来搭建一个微信公众号的后台，主要涉及Docker里运行JavaWeb的技术，一起来做这个有意思的事情吧！
(如果你对Docker了解不是很多的话，欢迎查看我的上一篇教程: [Docker简明教程](http://blog.saymagic.cn/2015/06/01/learning-docker.html))

## 项目结构介绍
首先看一下整个项目的结构：

![](http://cdn.saymagic.cn/o_19uc583oo1oqj16ek15gf19i4659.png)

`\etc\nginx-conf`是nginx的配置文件，用来做端口的转发。里面的代码如下：

    server {
    listen 80;
    server_name *.daoapp.io;
    location / {
        proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://localhost:8080/;
    }
    }

我们将所有访问*.daoapp.io下80端口的请求全部转移到8080端口，也就是提交给Tomcat进行处理。

`\etc\scripts`是Docker启动运行的脚本，里面会启动Tomcat与Nginx服务。

     #!/bin/sh
  
    # Start Tomcat
    $TOMCAT_HOME/bin/startup.sh

    # Start nginx
    nginx

`soft`文件夹里是maven与tomcat的安装文件，这里没有采用在Dockerfile里直接从网络下载安装包的方式，是防止网络下载地址在某些国家不能访问或者链接地址发生更改。

`webapp`目录是一个标准的maven项目，里面是我们微信公众号后台的主要源码。源码的目录结构如下：

![](http://cdn.saymagic.cn/o_19uc63k641h0t1b7j1u2t16vn674e.png)

`WxApiServlet`是处理请求的实现类，它会将微信服务器发来的Post请求封装成 `MsgRequest`对象，然后根据`MsgRequest`类型的不同(语音、文字、图片)来生成不同类型的handler，这里我只做了Event与Text类型的处理类，添加其它类型的处理需要继承`BaseHandler`，实现里面的`doHandleMsg`方法。这里需要注意两点，第一点是我将普通的文字与语音请求通过图灵机器人进行回复，因此你需要去[图灵机器人](http://www.tuling123.com/openapi/record.do?channel=14791)申请apikey：

![](http://cdn.saymagic.cn/o_19uc6geo1pgco0n1tj22no467o.png)

然后再Config.java中配置`APPKEY`属性。第二点是微信公众平台会对首次添加的后台地址做token验证，因此也需要在Config.java文件里配置`TOKEN`字段。这个字段的值需要和微信公众平台后台里设置的token相等。

`daocloud.yml`Daoloud的CI脚本，用来对项目做持续集成。

`Dockerfile`是组织整个项目的心脏，包含Docker镜像的构建，然后将项目编译并部署在容器的Tomcat上。 

## Dockerfile编写

来看一下整个Dockerfile：

    FROM ubuntu
    # 签名
    MAINTAINER saymagic "saymagic@163.com"

    # 安装JDK与nginx
    RUN apt-get update
    RUN apt-get install openjdk-7-jre -y
    RUN apt-get install openjdk-7-jdk -y
    RUN apt-get install nginx -y

    #拷贝nginx配置文件
    ADD ./etc/nginx-conf /etc/nginx/conf.d

    #拷贝启动脚本
    ADD ./etc/scripts /usr/local
    RUN chmod a+x /usr/local/start.sh

    #拷贝Tomcat与maven安装包
    ADD ./soft /tmp

    # 安装Tomcat 7
    RUN cd /usr/local && tar xzf /tmp/apache-tomcat-7.0.64.tar.gz
    RUN ln -s /usr/local/apache-tomcat-7.0.64 /usr/local/tomcat
    RUN rm /tmp/apache-tomcat-7.0.64.tar.gz

    # 安装maven
    RUN cd /usr/local && tar xzf /tmp/apache-maven-3.1.1-bin.tar.gz
    RUN ln -s /usr/local/apache-maven-3.1.1 /usr/local/maven
    RUN rm /tmp/apache-maven-3.1.1-bin.tar.gz

    RUN mkdir -p /webapp
    ADD ./webapp /webapp

    # 定义环境变量
    ENV TOMCAT_HOME /usr/local/tomcat
    ENV MAVEN_HOME /usr/local/maven
    ENV APP_HOME /webapp

    #编译源代码与部署
    RUN cd /webapp && /usr/local/maven/bin/mvn package 
    RUN rm -rf $TOMCAT_HOME/webapps/*
    RUN cd /webapp && cp target/wx_server.war $TOMCAT_HOME/webapps/ROOT.war

    #启动Tomcat与Nginx
    CMD /usr/local/start.sh && tail -F /usr/local/tomcat/logs/catalina.out

    EXPOSE 80 8080

里面我对每个主要步骤都做了注释，整体思路是这样：首先安装JDK、Maven与Tomcat，然后使用Maven编译webapp下面的java代码，并将打好的war包重命名为ROOT.war并拷进Tomcat的webapp目录来部署。最后运行start.sh来启动Tomcat与Nginx。

## Daocloud部署

到这里，我们相当于有了可以部署的镜像，但作为微信公众号的后台，我们必须提供一个可供外网访问的域名，为了一个实验去租用一台VPS有些不值并且会带来很多额外工作。这里我们可以使用[Daocloud]( https://account.daocloud.io/signup?invite_code=tqnulhtt2zsoh3p8npcu)平台来发布我们的镜像。它有免费的额度保证我们发布镜像来完成这个项目，并且还有很多直击痛点的优秀功能。

如果你是第一次使用[Daocloud]( https://account.daocloud.io/signup?invite_code=tqnulhtt2zsoh3p8npcu),首先我们去[Daocloud]( https://account.daocloud.io/signup?invite_code=tqnulhtt2zsoh3p8npcu)注册新账号，DaoCloud会将[Github](https://github.com/)、[GitCafe](http://gitcafe.com/signup?invited_by=saymagic)等git服务商作为代码源，所以你首先需要在[用户中心]->[第三方账户]里绑定[Github](https://github.com/)、[GitCafe](http://gitcafe.com/signup?invited_by=saymagic)账号:

![](http://cdn.saymagic.cn/o_19uc93ke11bpurl91smjrc91idbe.png)

为了你下面的操作更加方便，你可以直接Fork我的项目，项目地址:

Github:[https://github.com/saymagic/wxserver](https://github.com/saymagic/wxserver)

Gitcafe：[https://gitcafe.com/saymagic/wxserver](https://gitcafe.com/saymagic/wxserver)

Bitbucket:[https://bitbucket.org/saymagic/wxserver](https://bitbucket.org/saymagic/wxserver)

Coding：[https://coding.net/u/saymagic/p/wxserver/git]
(https://coding.net/u/saymagic/p/wxserver/git)

（注意的是：在Fork之后，记得修改token与图灵的appkey）

接下来选择[代码构建]->[创建新项目]

![](http://cdn.saymagic.cn/o_19uc8da8p8nh6kn1g3utv2110h9.png)

我们可以给我们的项目起个名字叫做`weixinserver`，

然后在[设置代码源]里选择我们Fork的项目。选择持续集成。

![](http://cdn.saymagic.cn/o_19uc9b72n1nmdf8q16jog2v1jagj.png)

最后，点击开始创建按钮。Daoloud就会默认将master分支进行构建：

![](http://cdn.saymagic.cn/o_19ucdb7omrbimq8jbp1fs81m1d9.png)

最后，点击[查看镜像]->[部署最新版本],Daoclod就会愉快的运行起来：

![](http://cdn.saymagic.cn/o_19ucebg3g1egd1vg95rr1vlts0pj.png)

上图标红的URL链接就是Daocloud为我们生成的微信公众后台链接。

### 持续集成

Daocloud提供了持续集成的功能，注意，持续集成不是持续构建，Daocloud的持续集成是保证我们每次push的版本都可以通过测试。然后再手动进行构建，然后运行镜像。相关文档可以看这里：[http://help.daocloud.io/features/continuous-integration/index.html](http://help.daocloud.io/features/continuous-integration/index.html).

## 微信公众平台注册

这个就无需多说，我们去[微信公众平台](https://mp.weixin.qq.com/)注册一个新的公众号，然后进入开发者模式，添加我们刚刚生成的URL与我们自己定义的token。

![](http://cdn.saymagic.cn/o_19ucfeqqchu6li98q5441sndj.png)

点击确定按钮，如果提示修改成功则表示我们token验证成功了，可以测试一下了！


## 测试

现在，关注我们自己的公众号，测试一下吧：

![](http://cdn.saymagic.cn/o_19ucf458d1vonmh314pn1ams1eo7e.png)

如果你成功收到了服务器返回的信息，恭喜你成功的用Docker来搭建了公众号的后台。

## 总结

此文就是我在将微信公众号后台部署到Docker上的详细笔记了。希望对看到最后的人有帮助。当然，我还是Docker新手，全文如果不正确地方，还请高手指正。

## 原文地址
[http://blog.saymagic.cn/2015/09/04/docker-weixin.html](http://blog.saymagic.cn/2015/09/04/docker-weixin.html)


