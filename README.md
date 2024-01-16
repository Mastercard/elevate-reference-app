# Elevate Accelerator Reference App

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Mastercard_elevate-reference-app&metric=alert_status)](https://sonarcloud.io/dashboard?id=Mastercard_elevate-reference-app)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Mastercard_elevate-reference-app&metric=coverage)](https://sonarcloud.io/dashboard?id=Mastercard_elevate-reference-app)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Mastercard_elevate-reference-app&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Mastercard_elevate-reference-app)
[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/Mastercard/elevate-reference-app/blob/master/LICENSE)

## Table of Contents
- [Overview](#overview)
- [Requirements](#requirements)
- [Integrating with OpenAPI Generator](#OpenAPI_Generator)
- [Configuration](#configuration)
- [Use-Cases](#use-cases)
- [Execute the Use-Cases](#execute-the-use-cases)
- [Service Documentation](#documentation)
- [API Reference](#api-reference)
- [Support](#support)
- [License](#license)

## Overview  <a name="overview"></a>
This is a reference application to demonstrate how Elevate Accelerator Proxy APIs can be used.
To call these APIs, a consumer key and .p12 file are required from your project on Mastercard Developers.

## Requirements  <a name="requirements"></a>

- [Mastercard Developers Account](https://developer.mastercard.com/dashboard) with access to "Elevate" API
- [Java 11](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Apache maven 3.3+](https://maven.apache.org/download.cgi)
- A text Editor or any IDE

## Frameworks/Libraries <a name="frameworks"></a>
- Apache Maven
- OpenAPI Generator

##Pre-requisites

To start using the Elevate services you will first have to be onboarded on our sandbox environment. Refer to the [Sandbox Access](https://developer.mastercard.com/elevate/documentation/tutorials-and-guides/request-sandbox-access) documentation.

## Integrating with OpenAPI Generator <a name="OpenAPI_Generator"></a>

OpenAPI Generator generates API client libraries from OpenAPI Specs. It provides generators and library templates for supporting multiple languages and frameworks.
Check [Generating and Configuring a Mastercard API Client](https://developer.mastercard.com/platform/documentation/security-and-authentication/generating-and-configuring-a-mastercard-api-client/) to know more about how to generate a simple API client for consuming APIs.

See also:

- [OpenAPI Generator (maven Plugin)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin)
- [OpenAPI Generator (executable)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-cli)
- [CONFIG OPTIONS for java](https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/java.md)

### OpenAPI Generator Plugin Configuration

```
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>${openapi-generator.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/elevate-accelerator-proxy-services.yaml</inputSpec>
                <generatorName>java</generatorName>
                <library>okhttp-gson</library>
                <generateApiTests>false</generateApiTests>
                <generateModelTests>false</generateModelTests>
                <configOptions>
                    <sourceFolder>src/gen/main/java</sourceFolder>
                    <dateLibrary>java8</dateLibrary>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Generating The API Client Sources
Now that you have all the dependencies you need, you can generate the sources. To do this, use one of the following methods:

```Using Terminal```

Navigate to the root directory of the project within a terminal window and execute ```mvn clean compile``` command.

## Configuration <a name="configuration"></a>
1. Create your account on [Mastercard Developers](https://developer.mastercard.com/) if you don't have it already.
2. Create a new project here and add ***Elevate*** to it and click continue.
3. Download Sandbox Signing Key, a ```.p12``` file will be downloaded.
4. In the Client Encryption Keys section of the dashboard, click on the ```Actions``` dropdown and download the client encryption key, a ```.pem``` file will be downloaded.
5. Copy the downloaded ```.p12``` and ```.pem``` files to ```src/main/resources``` folder in your code.
6. Open ```src/main/resources/application.properties``` and configure:
    - ```mastercard.elevate.client.p12.path ```- Path to keystore (.p12) file, just change the name as per the downloaded file in step 5.
    - ```mastercard.elevate.client.ref.app.consumer.key``` - Copy the Consumer key from "Sandbox/Production Keys" section on your project page
    - ```mastercard.elevate.client.ref.app.keystore.alias``` - Alias of your key. Default key alias for the sandbox is ```keyalias```.
    - ```mastercard.elevate.client.ref.app.keystore.password``` -  Password of your Keystore. Default keystore password for sandbox project is ```keystorepassword```.
    - ```mastercard.elevate.client.ref.app.encryption.file ```- Path to encryption key (.pem) file, just change the name as per the downloaded file in step 5, e.g. ```src/main/resources/<fileName>.pem```.

## Use-Cases <a name="use-cases"></a>
1. **Benefits**
   Endpoint: "/benefits".
   Used to get benefits available to the merchant.

2. **Check Eligibility**    
   Endpoint: "/eligibility".
   Used to check eligibility of a credit card in elevate program for a specific benefit.

3. **Create Redemptions**    
   Endpoint: "/redemptions".
   Used to create a redemption for a credit card that was previously enrolled through the eligibilities resource.


More details can be found [here](https://developer.mastercard.com/elevate/documentation/use-cases/).


## Execute the Use-Cases   <a name="execute-the-use-cases"></a>
Below are the APIs exposed by this application:
- GET  <HOST>/elevate/benefits
- POST <Host>/elevate/eligibility      
- POST <Host>/elevate/redemptions

Once you have added the correct properties, you are ready to build the application. You can do this by navigating to the project’s base directory from the terminal and then by running the following command.

`mvn clean install`

When the project builds successfully, you can run the following command to start the project
- Run ```java -jar target/elevate-accelerator-1.0.0.jar``` command in the terminal to run the application.
- Open the browser and enter the url ```http://localhost:5001/elevate/``` and you will land on benefits page.
- Click on ```benefits```, ```eligibility``` or ```redemeptions``` tab and enter the details as required.
- When clicked on submit you will receive a JSON response for each service.

**NOTE:**   
- Update the json files located here: ```/src/main/resources/templates/``` with valid request data in order to execute these apis successfully.  
- For each new call, you should change the email address such as, john.doe@elevate.mastercard.com. If you use the same email address, you may get an error that the benefit has already been redeemed (this is expected). For each new call, you should change the card number. Use the number generator to generate a number that passes the MOD10/Luhn test. The range of eligible PANs is 5555555555550000 through 5555555555559999.

## Service Documentation <a name="documentation"></a>

Elevate Accelerator Proxy documentation can be found [here](https://developer.mastercard.com/elevate/documentation/use-cases/).


## API Reference <a name="api-reference"></a>
The Swagger API specification can be found [here](https://developer.mastercard.com/elevate/documentation/api-reference/).

## Support <a name="support"></a>
Please email **apisupport@mastercard.com** with any questions or feedback you may have.


## License <a name="license"></a>
<p>Copyright 2021 Mastercard</p>
<p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at:</p>
<pre><code>   http://www.apache.org/licenses/LICENSE-2.0
</code></pre>
<p>Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.</p>

Aditya
