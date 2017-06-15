## Watson Workshop

The tutorial outlines the technical recipies involved in leveraging [Watson Developer Cloud Java SDK](https://github.com/watson-developer-cloud/java-sdk) to add cognitive capabilities into java applications.
We will dive into Java APIs for following watson services: 
- [Conversation]()
- [Language Translator]()
- [Text to Speech]()

We will also explore the [Reactive API](https://github.com/watson-developer-cloud/java-sdk#introduce-reactive-api-call-for-v301) introduced since version **v3.0.1** through examples.

### Getting the Service Credentials
You will need the username and password credentials for each service. Service credentials are different from your Bluemix account username and password.

To get your service credentials, follow these steps:

- Log in to Bluemix at https://bluemix.net.

- Create an instance of the service:

  - In the Bluemix Catalog, select the service you want to use.
  - Under Add Service, type a unique name for the service instance in the Service name field. For example, type my-service-name. Leave the default values for the other options.
  - Click **Create**.
  
- Copy your credentials:
  - On the left side of the page, click Service Credentials to view your service credentials.
  - Copy username and password to your project.

#### Prerequisites
- JDK 7 or higher
- Maven 3.0 or higher

#### Generate a maven project
```
mvn archetype:generate \
-DarchetypeGroupId=org.apache.maven.archetypes \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DgroupId=io.pkhanal.github \
-DartifactId=watson-workshop-sample \
-DinteractiveMode=false
```

#### Import the project into your IDE of choice

### Let's build a chatbot using Conversation Service

#### Log in to bluemix, create the service, and launch the visual tool

#### Import conversation workspace 
You can download the [workspace json file](/data/watson-resource-finder-conversation-workspace.json) available in the repository. The sample workspace can be used to create a chabot to find documentation for watson services. You can import workspace from the visual tool by clicking the icon marked in the image below.

![Import Workspace](/images/import-workspace.png?raw=true "Import Workspace")

#### Maven Dependency
```
<dependency>
	<groupId>com.ibm.watson.developer_cloud</groupId>
	<artifactId>conversation</artifactId>
	<version>3.8.0</version>
</dependency>
```


