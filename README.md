# Java Spring Phone Catalog Tutorial Application

## Overview
This is an example application/tutorial learn Spring, REST and other Java technologies.

This application takes the developer through the process of building a web-application using
Spring. The application is based on the [**Angularjs Phone Catalog Tutorial**][angular-phonecat]. The idea is to create the back-end application for this tutorial.

Each tagged commit is a separate lesson teaching an aspect of generating a Restful Java web server with Spring.

## Prerequisites

### Git

- A good place to learn about setting up git is [here][git-github].
- Git [home][git-home] (download, documentation).

### Java and maven

- JDK 1.7.x
  [Ubuntu/Debian][java-debian]
  [Windows][java-windows]
- maven3
- mongodb

## Workings of the application

- Read the Development section at the end to familiarize yourself with running and developing
  an angular application.

## Commits / Tutorial Outline

You can check out any point of the tutorial using
    git checkout step-?

To see the changes which between any two lessons use the git diff command.
    git diff step-?..step-?

### step-0 
- Install Java
- Install maven
- Install git
- Instal mongodb
- Clone repository
- run: `mvn clean package` to import dependencies and check environment status

### step-1

- Add project encoding:

```xml
<properties>
 <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

- Add java compilation level in pom.xml

```xml
<plugin>
 <artifactId>maven-compiler-plugin</artifactId>
 <version>2.3.2</version>
 <configuration>
  <source>1.7</source>
  <target>1.7</target>
 </configuration>
</plugin>
```

- Change junit vesion to 4.12

- Add WAR plugin for webapp packaging

```xml
<packaging>war</packaging>
```

```xml
<plugin>
 <groupId>org.apache.maven.plugins</groupId>
 <artifactId>maven-war-plugin</artifactId>
 <version>2.6</version>
 <configuration>
  <failOnMissingWebXml>false</failOnMissingWebXml>
 </configuration>
</plugin>
```

- Make maven work with servlet 3 config adding to pom:

```xml
<dependency>
 <groupId>javax.servlet</groupId>
 <artifactId>javax.servlet-api</artifactId>
 <version>3.1.0</version>
 <scope>provided</scope>
</dependency>
```

- Add Spring MVC dependency:

```xml
<dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-webmvc</artifactId>
 <version>4.0.8.RELEASE</version>
</dependency>
```

- Add jetty server to deploy and run the webapp

```xml
<plugin>
 <groupId>org.eclipse.jetty</groupId>
 <artifactId>jetty-maven-plugin</artifactId>
 <version>9.3.0.M1</version>
 <configuration>
  <scanIntervalSeconds>3</scanIntervalSeconds>
  <httpConnector>
   <port>9000</port>
  </httpConnector>
 </configuration>
</plugin>
```

- Create classes:
  [MyWebAppInitializer][mvc-container-config]
  [MyWebConfig][mvc-config]
  Add [ViewResolver][mvc-viewresolver-chaining] bean to MyWebConfig
  [HelloWorldmentController][mvc-controller]
  Add home.jsp file to `src/main/WEB-INF/jsp/`

- Delete default generated App class and test

- Run the webapp `mvn jetty:run`

- Open the [webapp](http://localhost:9000/hello)


### step-2

- Add User Entity
- Store in DB
- Add Spring Data Mongo dependency to POM

```xml
<!-- Spring data mongodb -->
<dependency>
 <groupId>org.springframework.data</groupId>
 <artifactId>spring-data-mongodb</artifactId>
 <version>1.2.0.RELEASE</version>
</dependency>
```

- Add [MongoConfiguration][mongo-repositories]
- Add MongoConfiguration to root configurations in MyWebAppInitializer
- Add Anotations and Repository
- Return as REST web service
- Add json dependency in pom

```xml
<!-- Jackson Core -->
<dependency>
 <groupId>com.fasterxml.jackson.core</groupId>
 <artifactId>jackson-databind</artifactId>
 <version>2.3.4</version>
</dependency>
```

- Add User RestController
- Add POST method to store User
- Add GET method to retrieve User
- Install Advanced rest client chrome
- Add log4j dependencies in pom

```xml
<!-- Log4J2 -->
<dependency>
 <groupId>org.apache.logging.log4j</groupId>
 <artifactId>log4j-slf4j-impl</artifactId>
 <version>2.1</version>
</dependency>
<dependency>
 <groupId>org.apache.logging.log4j</groupId>
 <artifactId>log4j-core</artifactId>
 <version>2.1</version>
</dependency>
```

- Add log4j file

### step-3

- Add Spring security dependencies to pom

```xml
<!-- Spring security -->
<dependency>
 <groupId>org.springframework.security</groupId>
 <artifactId>spring-security-config</artifactId>
 <version>3.2.3.RELEASE</version>
</dependency>
<dependency>
 <groupId>org.springframework.security</groupId>
 <artifactId>spring-security-web</artifactId>
 <version>3.2.3.RELEASE</version>
</dependency>
```

- Create classes:
  [SecurityApplicationInitializer][webapplicationinitializer]
  [AppUserDetailsService][userdetails-service]
  [SecurityConfiguration][jc-form]
  [CustomAuthenticationSucessHandler]
- Add [SecurityConfiguration] to [MyWebAppInitializer]
- Make Controllers and entities aware of the logged user
- Make the user not return the password
- Create [MethodSecurityConfiguration]
- Make get User method secure

### step-4

- Add phone.json to resources.
- Add jsonschema2pojo plugin to pom
```xml
<plugin>
 <groupId>org.jsonschema2pojo</groupId>
 <artifactId>jsonschema2pojo-maven-plugin</artifactId>
 <version>0.4.15</version>
 <configuration>
  <sourceDirectory>${basedir}/src/main/resources/schema/phone.json</sourceDirectory>
  <targetPackage>com.examplecorp.phonecat.model</targetPackage>
  <includeHashcodeAndEquals>false</includeHashcodeAndEquals>
  <includeToString>false</includeToString>
  <sourceType>json</sourceType>
 </configuration>
 <executions>
  <execution>
   <goals>
    <goal>generate</goal>
   </goals>
  </execution>
 </executions>
</plugin>
```
- Create mongo repository and controller to use Phone class

## Development with spring-phonecat

The following docs describe how you can test and develop further this application.


### Installing dependencies

The application relies upon maven.  You can
install these by running:

```
mvn clean install
```

### Running the app during development

- Run `mvn jetty:run`
- Use Chrome extension Advanced rest client with the url `http://localhost:9000` to see the app running in your browser.

[angular-phonecat]: https://github.com/angular/angular-phonecat
[java-debian]: https://wiki.debian.org/JavaPackage
[java-windows]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[mvc-container-config]: http://docs.spring.io/spring-framework/docs/4.0.8.RELEASE/spring-framework-reference/html/mvc.html#mvc-container-config
[mvc-config]: http://docs.spring.io/spring-framework/docs/4.0.8.RELEASE/spring-framework-reference/html/mvc.html#mvc-config
[git-home]: http://git-scm.com
[git-github]: http://help.github.com/set-up-git-redirect
[mvc-viewresolver-chaining]: http://docs.spring.io/spring-framework/docs/4.0.8.RELEASE/spring-framework-reference/html/mvc.html#mvc-viewresolver-chaining
[mvc-controller]: http://docs.spring.io/spring-framework/docs/4.0.8.RELEASE/spring-framework-reference/html/mvc.html#mvc-controller
[mongo-repositories]: http://docs.spring.io/spring-data/data-mongo/docs/1.7.1.RELEASE/reference/html/#mongo.repositories
[webapplicationinitializer]: http://docs.spring.io/spring-security/site/docs/4.0.1.RELEASE/reference/htmlsingle/#abstractsecuritywebapplicationinitializer-with-spring-mvc
[userdetails-service]: http://docs.spring.io/spring-security/site/docs/4.0.1.RELEASE/reference/htmlsingle/#tech-userdetailsservice
[jc-form]: http://docs.spring.io/spring-security/site/docs/4.0.1.RELEASE/reference/htmlsingle/#jc-form