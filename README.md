# AWS JavaScript Lambda

> This is just a simple guide on how to create a basic SOA infrustructure using [Java API for XML Web-Service](https://en.wikipedia.org/wiki/Java_API_for_XML_Web_Services). This small tutorial is done as a part of a `Distributed Systems` project in the University of Applied Science Merseburg.

# Quick Dive-In

If you just want to have all the things here on your local machine running, try out instructions in this section and skip the other.

# Intro

The aim of this small basic tutorial is to learn the essential parts of the Web-Services in practice.

## What we are going to do

We will do the following things:

* Create a simple Web-Service that offers 3 functions.
* Create a deployment artefact.
* Install a Glassfish Server and make a basic setup.
* Deploy our deployment artefact locally.
* Create a client and bind it to a web-service consuming its functionality.
* Write a simple test in order to test the system as a whole.

## Prerequisites

### Knowledge

You have a solid understanding of object-oriented languages, e.g. [Java](<https://en.wikipedia.org/wiki/Java_(programming_language)>) and [XML](https://en.wikipedia.org/wiki/XML).
You have an idea what a [Service-oriented architecture](https://en.wikipedia.org/wiki/Service-oriented_architecture) is.
You understand the [Web-Services](https://en.wikipedia.org/wiki/Web_service), their purpose and essential components.

Personal recommendation: I really recommend to take a look at [this tutorial for Web-Services](https://www.tutorialspoint.com/webservices/index.htm) if you are not that fit in the theory.

### Tools

I will use [Unix Bash](<https://en.wikipedia.org/wiki/Bash_(Unix_shell)>) and [IntelliJ IDEA Ultimate Edition (Trial)](https://www.jetbrains.com/idea/) throughout this tutorial.

# Step by step guide

## Initial steps

Make sure you have JDK installed:

```sh
$ sudo apt-get install openjdk-8-jdk
$ java -version
```

If you experience any problems, try out [this](https://tecadmin.net/install-oracle-java-8-ubuntu-via-ppa/) tutorial.

## Create a simple Web-Service

### Create a project

1. Start the IntelliJ IDEA and open the create a new project.
2. Search for `JAVA EE`and check `WebServices`. Uncheck `Generate sample code`.
   ![New Project](/images/new-project.png).
3. Click `Next` and give a name to your project, say `MyWebService` and click `Finish`.
   You should have the following ![Project Structure Tree](/images/project-structure.png).

### Implement a Web Service

To start with, click on the `src` directory and create a new package named `ws`: go File -> New -> Package.

### 1. Create a Service Endpoint Interface (SEI).

This is a Java Interface that exposes the Service interface's operations. This SEI is used by the clients to communicate with a Web-Service.

Click on the `src` directory, go File -> New -> Java Class. In the modal window enter `MyWebService` and select `Interface` kind.

We are going to expose 3 functions. For the sake of understanding and implementing the SOA Infrustructe, we'll create simple functions:

* Validate credit card number using [Luhn algorithm](https://en.wikipedia.org/wiki/Luhn_algorithm).
* Check if a given word is a [Palindrome](https://en.wikipedia.org/wiki/Palindrome).
* [Quadratic equation solver](https://en.wikipedia.org/wiki/Quadratic_equation).

Therefore, our `SEI` looks like following:

```java
package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface MyWebService {
    @WebMethod
    boolean luhnChecksum(final long checksum);

    @WebMethod
    boolean isPalindrome(final String string);

    @WebMethod
    int[] quadraticEquationSolve(final int a, final int b, final int c);
}
```

> Note: the annotations `@WebService` and `@WebMethod` describe Web-Service Operations.

### 2. Implement the Service Implementation Bean (SIB)
Now its time to provide actual implementation of the exposed Web-Service operations.

Click on the `src` directory, go File -> New -> Java Class. In the modal window enter `MyWebServiceImpl`. This class is our SIB.

We should now connect the newly created SIB to the SEI. This is done by the following annotation. 
```java
@WebService(endpointInterface = "ws.MyWebService")
```

> The endpointInterface element connects this SIB to its SEI, and is necessary to avoid undefined port type errors when running the client application presented later [source](https://www.javaworld.com/article/3215966/java-language/web-services-in-java-se-part-2-creating-soap-web-services.html).

We have provided the stub implementation that looks like following:

```java
package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(endpointInterface = "ws.MyWebService")
public class MyWebServiceImpl implements MyWebService {
    @Override
    @WebMethod
    public boolean luhnChecksum(long checksum) {
        return false;
    }

    @Override
    @WebMethod
    public boolean isPalindrome(String string) {
        return false;
    }

    @Override
    @WebMethod
    public int[] quadraticEquationSolve(int a, int b, int c) {
        return new int[0];
    }
}
```

## Create a deployment artifact
We don't want to stick to any IDE specific local deployment logic, like IntelliJ IDEA, NetBeans or Eclipse. We want to have a single piece of data, say a container with our WebService, being ready to deploy anywhere. 

### What is an artifact? 
> An artifact is an assembly of your project assets that you put together to test, deploy or distribute your software solution or its part. Examples are a collection of compiled Java classes or a Java application packaged in a Java archive, a Web application as a directory structure or a Web application archive, etc. 
More information about artifacts you can find [here](https://www.jetbrains.com/help/idea/working-with-artifacts.html).

### Configure the artifact
We are going to create a Web Application Archive ([WAR](https://en.wikipedia.org/wiki/WAR_(file_format))) now, that is actually our artifact.

Go to File -> Project Structure and Select Artifacts on the left. 
Set name to `MyWebService`, select `Web Application Archive` Type.

This is how artifact configuration looks like:

![Artifact config](/images/artifact-config.png)

Make sure you have a `sun-jaxws.xml` configuration file under `/web/WEB-INF/`, that defines our service endpoint and looks like following: 
```xml
<?xml version="1.0" encoding="UTF-8"?>

<endpoints xmlns='http://java.sun.com/xml/ns/jax-ws/ri/runtime' version='2.0'>
    <endpoint
            name='MyWebService'
            implementation='ws.MyWebServiceImpl'
            url-pattern='/services/MyWebService'/>
</endpoints>
```
The `web.xml` in the same directory defines the url pattern for our service and looks like following: 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <listener>
        <listener-class>com.sun.xml.ws.transport.http.servlet.WSServletContextListener</listener-class>
    </listener>
    <servlet>
        <description>JAX-WS endpoint</description>
        <display-name>WSServlet</display-name>
        <servlet-name>WSServlet</servlet-name>
        <servlet-class>com.sun.xml.ws.transport.http.servlet.WSServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>WSServlet</servlet-name>
        <url-pattern>/services/*</url-pattern>
    </servlet-mapping>
</web-app>
```

We can also remove `index.jsp` because we actually don't need any home page to be displayed.
Our project tree looks like following now:

![Project Tree](/images/project-structure-release.png)

>Note: To get full description on what this or that configuration file means, read the official oracle docs on [Packaging and Deploying Web Services](https://docs.oracle.com/cd/E16439_01/doc.1013/e13982/wsdeployment.htm).

### Generate the artifact
Just navigate to Build -> Build Artifacts... -> app -> Build Artifact.

This will trigger the project build and generate an `out/` directory. Our deployment artifact can be found under `/out/artifacts/app/app.war`. 

## Local Deployment

## Test

## Outro