# AWS

This is a sample template for AWS - Below is a brief explanation of what we have generated for you:

```bash
.
├── HelloWorldFunction
│   ├── build.gradle                                   <-- Java Dependencies
│   ├── gradle                                         <-- Gradle related Boilerplate
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradlew                                        <-- Linux/Mac Gradle Wrapper
│   ├── gradlew.bat                                    <-- Windows Gradle Wrapper
│   └── src
│       ├── main
│       │   └── java
│       │       └── helloworld                         <-- Source code for a lambda function
│       │           ├── App.java                       <-- Lambda function code
│       │           └── GatewayResponse.java           <-- POJO for API Gateway Responses object
│       └── test                                       <-- Unit tests
│           └── java
│               └── helloworld
│                   └── AppTest.java
├── README.md                                          <-- This instructions file
└── template.yaml
```

## Requirements

* AWS CLI already configured with Administrator permission
* [Java SE Development Kit 8 installed](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Docker installed](https://www.docker.com/community-edition)

## Setup process

### Installing dependencies

```bash
sam build
```

You can also build on a Lambda like environment by using:

```bash
sam build --use-container
```

### Local development

**Invoking function locally through local API Gateway**

```bash
sam local start-api
```

If the previous command ran successfully you should now be able to hit the following local endpoint to invoke your function `http://localhost:3000/hello`

**SAM CLI** is used to emulate both Lambda and API Gateway locally and uses our `template.yaml` to understand how to bootstrap this environment (runtime, where the source code is, etc.) - The following excerpt is what the CLI will read in order to initialize an API and its routes:

```yaml
...
Events:
    HelloWorld:
        Type: Api # More info about API Event Source: https://github.com/awslabs/serverless-application-model/blob/master/versions/2016-10-31.md#api
        Properties:
            Path: /hello
            Method: get
```

## Packaging and deployment

Firstly, we need a `S3 bucket` where we can upload our Lambda functions packaged as ZIP before we deploy anything - If you don't have a S3 bucket to store code artifacts then this is a good time to create one:

```bash
aws s3 mb s3://BUCKET_NAME
```

Next, run the following command to package our Lambda function to S3:

```bash
sam package \
    --output-template-file packaged.yaml \
    --s3-bucket nasdaq-news-scraper
```

Next, the following command will create a Cloudformation Stack and deploy your SAM resources.

```bash
sam deploy \
    --template-file packaged.yaml \
    --stack-name nasdaq-news-scraper \
    --capabilities CAPABILITY_IAM
```

> **See [Serverless Application Model (SAM) HOWTO Guide](https://github.com/awslabs/serverless-application-model/blob/master/HOWTO.md) for more details in how to get started.**

After deployment is complete you can run the following command to retrieve the API Gateway Endpoint URL:

```bash
aws cloudformation describe-stacks \
    --stack-name nasdaq-news-scraper \
    --query 'Stacks[].Outputs'
```

## Testing

We use `JUnit` for testing our code and you can simply run the following command to run our tests:

```bash
gradle test
```

# Appendix

## AWS CLI commands

AWS CLI commands to package, deploy and describe outputs defined within the cloudformation stack:

```bash
sam package \
    --template-file template.yaml \
    --output-template-file packaged.yaml \
    --s3-bucket nasdaq-news-scraper

sam deploy \
    --template-file packaged.yaml \
    --stack-name nasdaq-news-scraper \
    --capabilities CAPABILITY_IAM \
    --parameter-overrides MyParameterSample=MySampleValue

aws cloudformation describe-stacks \
    --stack-name aws --query 'Stacks[].Outputs'
```

## Bringing to the next level

Here are a few ideas that you can use to get more acquainted as to how this overall process works:

* Create an additional API resource (e.g. /hello/{proxy+}) and return the name requested through this new path
* Update unit test to capture that
* Package & Deploy

Next, you can use the following resources to know more about beyond hello world samples and how others structure their Serverless applications:

* [AWS Serverless Application Repository](https://aws.amazon.com/serverless/serverlessrepo/)
