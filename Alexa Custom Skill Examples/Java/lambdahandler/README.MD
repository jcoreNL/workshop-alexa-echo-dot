# Building with Maven

Run the command

    mvnw assembly:assembly -DdescriptorId=jar-with-dependencies package

Then upload the jar file located in the `target` folder.

# Building with Gradle

Run the command

    gradlew build

Then upload the fat jar file located in the `build/libs` folder.

# Setup in AWS Lambda

After uploading the jar file, set the `Handler` to `com.workshop.alexa.MyHandler`, as that class is the entry point for the lambda function. 