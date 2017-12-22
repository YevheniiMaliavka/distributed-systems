# Web-Service with JAX-WS. First blood

> This is just a simple guide on how to create a basic SOAP Web-Services infrustructure (Web Service and Web Service Client) using [Java API for XML Web-Service](https://en.wikipedia.org/wiki/Java_API_for_XML_Web_Services). This small tutorial is done as a part of a `Distributed Systems` project in the University of Applied Science Merseburg, is only for educational and version control purposes here. It does not implement best-practices and may contain mistakes.

## Quick Dive-In

If you just want to have all the things here on your local machine running, try out instructions in this section and skip the other.

## Intro

The aim of this small basic tutorial is to learn the essential parts of the Web-Services in practice.

## What we are going to do

We will do the following things:

1. Create a simple Web-Service that offers 3 functions
2. Create a deployment artefact.
3. Install a Glassfish Server and make a basic setup.
4. Deploy our deployment artefact locally.
5. Create a client and bind it to a web-service consuming its functionality.
6. Write a simple test in order to test the system as a whole.

## Prerequisites

### Knowledge

You have a solid understanding of object-oriented languages, e.g. [Java](<https://en.wikipedia.org/wiki/Java_(programming_language)>) and [XML](https://en.wikipedia.org/wiki/XML).
You have an idea what a [Service-oriented architecture](https://en.wikipedia.org/wiki/Service-oriented_architecture) is.
You understand the [Web-Services](https://en.wikipedia.org/wiki/Web_service), their purpose and essential components.

Personal recommendation: I really recommend to take a look at [this tutorial for Web-Services](https://www.tutorialspoint.com/webservices/index.htm) if you are not that fit in the theory.

### Tools

I will use [Unix Bash](<https://en.wikipedia.org/wiki/Bash_(Unix_shell)>) and [IntelliJ IDEA Ultimate Edition (Trial)](https://www.jetbrains.com/idea/) throughout this tutorial.

## Step by step guide

## Initial steps

Make sure you have JDK installed:

```sh
$sudo apt-get install openjdk-8-jdk
$java -version
```

If you experience any problems, try out [this](https://tecadmin.net/install-oracle-java-8-ubuntu-via-ppa/) tutorial.

## Create a simple Web-Service

### Create a project

1. Start the IntelliJ IDEA and open the create a new project.
1. Search for `JAVA EE`and check `WebServices`. Uncheck `Generate sample code`.
   ![New Project](/images/new-project.png)
1. Click `Next` and give a name to your project, say `MyWebService` and click `Finish`.
   You should have the following ![Project Structure Tree](/images/project-structure.png)

### Implement a Web Service

To start with, click on the `src` directory and create a new package named `ws`: go `File -> New -> Package`.

### 1. Create a Service Endpoint Interface (SEI)

This is a Java Interface that exposes the Service interface's operations. This SEI is used by the clients to communicate with a Web-Service.

Click on the `src` directory, go `File -> New -> Java Class`. In the modal window enter `MyWebService` and select `Interface` kind.

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

Click on the `src` directory, go `File -> New -> Java Class`. In the modal window enter `MyWebServiceImpl`. This class is our SIB.

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

### What is an artifact

> An artifact is an assembly of your project assets that you put together to test, deploy or distribute your software solution or its part. Examples are a collection of compiled Java classes or a Java application packaged in a Java archive, a Web application as a directory structure or a Web application archive, etc.
> More information about artifacts you can find [here](https://www.jetbrains.com/help/idea/working-with-artifacts.html).

### Configure the artifact

We are going to create a Web Application Archive ([WAR](<https://en.wikipedia.org/wiki/WAR_(file_format)>)) now, that is actually our artifact.

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

> Note: To get full description on what this or that configuration file means, read the official oracle docs on [Packaging and Deploying Web Services](https://docs.oracle.com/cd/E16439_01/doc.1013/e13982/wsdeployment.htm) and [Creating a Web Service](https://docs.oracle.com/cd/E17802_01/webservices/webservices/reference/tutorials/wsit/doc/Examples_glassfish4.html#wp104810).

### Generate the artifact

Just navigate to `Build -> Build Artifacts... -> app -> Build Artifact`.

This will trigger the project build and generate an `out/` directory. Our deployment artifact can be found under `/out/artifacts/app/app.war`.

## Local Deployment

Our deployment package will be running on a [GlassFish Application Server](https://en.wikipedia.org/wiki/GlassFish) for the Java EE platform.

### Server Setup

1. Download the GlassFish 5 for Linux [here](https://javaee.github.io/glassfish/download). There are two possibilities, namely Web-Profile and Full-Platform. The first one should be sufficient for this tutorial, however I have used the Full-Platform.
2. Unzip the archive.
3. Navigate in terminal to the unzipped dir, e.g.`/glassfish5/bin`, where you can find the `asadmin` shell script. This one is used to start and control the server.

### Deployment

4. Start the default domain `domain1`

```sh
./asadmin start-domain domain1
```

5. Run deployment command

```sh
./asadmin deploy ~/src/distributed-systems/MyWebService/out/artifacts/app/app.war
```

#### This should look like following

![Deployment](/images/deployment.png)

#### Try out deployed app

You can access it at `http://localhost:8080/app/services/MyWebService`.
![Deployed](/images/deployed.png)

#### GUI Deployment

You can also perform the deployment using the GUI in browser by accessing the GlassFish Admin Console at `http://localhost:4848/`
![GUI](/images/gui-deployment.png)

You can read more about GlassFish Deployment [using terminal](https://dzone.com/articles/how-deploy-war-file-using) or [using GUI](https://blog.idrsolutions.com/2013/08/creating-and-deploying-a-java-web-service/).

## Consume Web-Service

We have our Web-Service running and want to have a client that consumes its functionality. Still, we don't have any functionality our Web-Service could offer.
Let's implement our stub methods of the Web-Service:

## Implement Stub-Methods of the Web-Service

### luhnChecksum

```java
    @Override
    @WebMethod
    public boolean luhnChecksum(final String checksum) {
        // convert string to integer array
        if (checksum.length() == 0) {
            return false;
        }
        int[] digits = new int[checksum.length()];
        char[] symbols = checksum.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            digits[i] = Character.getNumericValue(symbols[i]);
        }
        // luhn algorithm
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {
            int digit = digits[length - i - 1];
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }
```

### isPalindrome

```java
    @Override
    @WebMethod
    public boolean isPalindrome(String string) {
        // exclude all symbols that are not letters
        String lowerCased = string.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if(lowerCased.length() == 0) {
            return false;
        }
        int a = 0, b = lowerCased.length() - 1;
        while(a < b){
            if(lowerCased.charAt(a) != lowerCased.charAt(b)){
                return false;
            }
            a++;
            b--;
        }
        return true;
    }
```

### Quadratic Equation Solver

```java
    @Override
    @WebMethod
    public double[] quadraticEquationSolve(final int a,final int b, final int c) {
        int discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new double[0];
        }
        if (discriminant == 0) {
            return new double[]{-b / (2 * a)};
        }
        double discriminantSqrt = Math.sqrt(discriminant);
        return new double[]{(-b + discriminantSqrt) / (2 * a), (-b - discriminantSqrt) / (2 * a)};
    }
```

## Create Client

I have implemented a basic Java Client that does not provide any GUI interaction and only shows console output for several reasons:

* to test the Web-Service with predefined values.
* to make the code more self-descriptive.
* to simplify the program execution.
* to simplify the project structure and make program behaviour obvious.
* many ways of developing a MVC Web-App in Java confuse me as a non-Java developer. The opportunity to hardcode something doesn't make me happy.

### Instantiate a project

1. Make sure your GlassFish server is running and executing our Web-Service App.
2. Create a new Web-Services Client Project.
   ![ws-client-project](/images/web-services-client.png)
3. IntelliJ IDEA will offer you to generate Java code from the WSDL. Fill in the fields correctly as shown and click `Ok`:
   ![WSDL](/images/wsdl-gen.png)

> Note: we will also try out another way of generating the Java code from the WSDL that is not IDE specific and uses the default JDK `wsimport` tool.

4. The previous actions would trigger IntelliJ IDEA to generate some code for us:

```java
package example;

import ws.MyWebServiceImplService;

public class HelloWorldClient {
  public static void main(String[] argv) {
    ws.MyWebService service = new MyWebServiceImplService().getPort();
    //invoke business method
    service.luhnChecksum();
  }
}
```

### Consume and Test Web-Service functions

We should adjust our project structure to look like folllowing:
![client-project-structure](/images/client-project-structure.png)

Let's implement the consuming functions.

#### luhnChecksum Implementation

```java
    static void checksumTest(ws.MyWebService service) throws Exception {
    Exception ex = new Exception("Luhn Checksum service is working incorrectly ¯\\_(ツ)_/¯");
    //American Express, Diners Club, MasterCard
    String[] creditCards = {"371449635398431", "30569309025904", "5555555555554444"};
    for (String creditCard :
            creditCards) {
        if (service.luhnChecksum(creditCard)) {
            System.out.println("Credit card " + creditCard + " is valid!");
        } else {
            throw ex;
        }
    }
    // check fake card
    String fakeCreditCard = creditCards[0].concat("999");
    if (service.luhnChecksum(fakeCreditCard)) {
        throw ex;
    } else {
        System.out.println("Credit card " + fakeCreditCard + " is fake!");
    }
}
```

#### isPalindrome Implementation

```java
    static void palindromeTest(ws.MyWebService service) throws Exception {
    String[] palindroms = {"A man, a plan, a canal, Panama!", "Was it a car or a cat I saw?", "No 'x' in Nixon"};
    Exception ex = new Exception("Palendrome service is working incorrectly ¯\\_(ツ)_/¯");
    for (String palindrom :
            palindroms) {
        if (service.isPalindrome(palindrom)) {
            System.out.println(palindrom + " ----> is a palindrome!");
        } else {
            throw ex;
        }
    }
    String notAPalindrome = "FooBar";
    if (service.isPalindrome(notAPalindrome)) {
        throw ex;
    } else {
        System.out.println(notAPalindrome + " ----> is not a palindrome!");
    }
}
```

#### Quadratic Equation Solver Implementation

```java
    static <T> boolean equal(List<T> a, List<T> b){
        if(a.size() != b.size()){
            return false;
        }
        for (T member :
                a) {
            if(!b.contains(member)){
                return false;
            }
        }
        return true;
    }

    static void quadraticEquationTest(ws.MyWebService service) throws Exception{
        // ax^2+bx+c=0
        int a = 1, b = -3, c = -4;
        List<Double> solution = Arrays.asList(-1.0, 4.0);
        List<Double> result = service.quadraticEquationSolve(a, b, c);
        System.out.println(result);
        if(equal(solution, result)){
            System.out.println("x^2 - 3x - 4 = 0. Roots: -1, 4");
        }else{
            throw new Exception("Quadratic Equation Solver is working incorrectly ¯\\_(ツ)_/¯");
        }
    }
```

### Main method

Our main method will trigger each function and catch any exceptions.

```java
    public static void main(String[] argv) {
        ws.MyWebService service = new ws.MyWebServiceImplService().getPort(MyWebService.class);
        try {
            System.out.println("<----------- [Palindrome Test] --------->");
            palindromeTest(service);
            System.out.println("(｡♥‿♥｡) done!\n");
            System.out.println("<----------- [Luhn Checksum] ----------->");
            checksumTest(service);
            System.out.println("(｡♥‿♥｡) done!\n");
            System.out.println("<----------- [Quadratic Equation Solver] ----->");
            quadraticEquationTest(service);
            System.out.println("(｡♥‿♥｡) done!\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }
```

## Test

Having everything set-up, we just run our client and expect to see the following output:
![Test](/images/final-test.png)

## Outro

We are done here. We've got our simple Web-Service consumed by a Client using the SOAP with a Service Provider and Service Consumer.

This guide was intended for my better understanding and is not to supposed to be the `to follow` one. 

If you've found some mistakes, got some ideas or suggestions on how to improve this simple guide, you are very welcome to make a PR or create an issue.