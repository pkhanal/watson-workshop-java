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
  
- We will later copy username and password into the project.

#### Prerequisites
- JDK 7 or higher
- Maven 3.0 or higher
  - for running project from command line as discussed in later section, we will need version > 3.3.1

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

## Let's build a chatbot using Conversation Service

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
#### config.properties file
Create config.properties file in the root directory of the project. The [sample properties file](/sample-project/watson-workshop-sample/sample.config.properties) can be found in this repository. Add credentials from Conversation service to the config file.

#### API Usage
```
ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
service.setUsernameAndPassword(
  prop.getProperty("CONVERSATION_USERNAME"),
  prop.getProperty("CONVERSATION_PASSWORD"));

// conversation context
Map<String, Object> ctx = new HashMap<>();

MessageRequest messageRequest = createChatMessage("", ctx);
do {
    MessageResponse response = service.message(
      prop.getProperty("CONVERSATION_WORKSPACE_ID"),
      messageRequest).execute();

    writeBotResponseToConsole(response.getTextConcatenated("\n"));

    messageRequest = createChatMessage(readUserInput(), response.getContext());
} while (true);

```
You can find the full source code in [Conversation.java](https://github.com/pkhanal/watson-workshop-java/blob/master/sample-project/watson-workshop-sample/src/main/java/io/pkhanal/github/Conversation.java)


## Go Reactive!
Often, we will run into a situation where we are leveraging multiple services in our application for cognitive capability. As of **June 14 2017**, there are [19 cognitive services](https://www.ibm.com/watson/developercloud/services-catalog.html) available in IBM Watson Platform. With this microservices based offerings, it is highly likely that you will be using more than one services in your application. It often requires you two combine / chain multiple services to get the result. That's when the Reactive API proves handy. It's functional approach to asynchronous programming helps developer to chain / combine multiple service calls in a reactive fashion.

Since v3.0.1, each Service(TextToSpeech, LanguageTranslator, SpeechToText...) API has ``.rx()`` method that returns ``CompletableFuture``. ``CompletableFuture`` provides powerful APIs to build asynchronous system.

For example, let's say you are trying to translate text from one language to another and then apply speech to text service to the translated text.

```
...

LanguageTranslator translator = new LanguageTranslator();
translator.setUsernameAndPassword("<username>", "<password>");

TextToSpeech tts = new TextToSpeech();
tts.setUsernameAndPassword("<username>", "<password>");

translator
  .translate("hello", Language.ENGLISH, Language.FRENCH)
  .rx()
  .thenApply(translationResult -> translationResult.getFirstTranslation())
  .thenApply(translation -> tts.synthesize(translation, Voice.FR_RENEE, AudioFormat.WAV).rx())
  .thenAccept(App::processSpeechSynthesis);
  
...
```
You can find the full source code in [ReactiveTranslatorAndTextToSpeech.java](https://github.com/pkhanal/watson-workshop-java/blob/master/sample-project/watson-workshop-sample/src/main/java/io/pkhanal/github/ReactiveTranslatorAndTextToSpeech.java)

This is a smiple example and I would say we explored only the tip of iceberg through this example. With Reactive API, it is easier to combine, compose and execute asynchronous calls to create a complex asynchronous system. Imagine a scenario where you want to make three different asynchronous service calles in parallel and want to move to immediately move to next stage regardless service gives you the result first. Or say you want to combine results of multiple service calls before moving onto next service call. I would recomment looking into the online resources on CompletableFuture and how it can be leveraged to build asynchronous system.


## Sample Project
The [sample project](/sample-project/watson-workshop-sample/) for this workshop is available alonside this repository. You can import the project in your IDE and run them.

### Setup
Copy sample.config.properties and rename the copy to config.properties. Add the service crendentials for Conversation, Language Translator & Text to Speech services.

### Run from command line

#### Conversation
mvn exec:java@Conversation

#### Language Translation from English to French and then apply Text to Speech
mvn exec:java@ReactiveTranslationTextToSpeech -Dexec.args="'good bye'"

