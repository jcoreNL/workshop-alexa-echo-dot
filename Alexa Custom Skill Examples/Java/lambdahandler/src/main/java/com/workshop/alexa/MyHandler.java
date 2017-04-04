package com.workshop.alexa;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * The entry point for a lambda function, which initializes our speechlet. Think of this as a 'main' function.
 */
public class MyHandler extends SpeechletRequestStreamHandler {
  private static final Set<String> supportedApplicationIds = new HashSet<>();


  static {
    /*
     * Uncomment this statement and populate with your skill's application ID(s) to
     * prevent someone else from configuring a skill that sends requests to this function.
     */
    // supportedApplicationIds.add("amzn1.echo-sdk-ams.app.[unique-value-here]");
  }

  public MyHandler() {
    super(new MySpeechlet(), supportedApplicationIds);
  }
}
