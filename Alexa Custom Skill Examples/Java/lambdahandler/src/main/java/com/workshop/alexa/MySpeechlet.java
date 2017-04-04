package com.workshop.alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;

import java.util.Optional;

/**
 * A sample Alexa AWS Lambda speechlet, which handles all intents.
 */
public class MySpeechlet implements Speechlet {
  private static final String FIRST_INTENT = "MyFirstOpeningIntent";
  private static final String FOLLOW_UP_INTENT = "MyFollowUpIntent";
  private static final String UNKNOWN_INTENT = "UNKNOWN_INTENT";

  /**
   * Called when the user specifies an intent for this skill.
   */
  public SpeechletResponse onIntent(final IntentRequest intentRequest, final Session session) throws SpeechletException {
    final Intent intent = intentRequest.getIntent();
    final String intentName = Optional.ofNullable(intent).map(Intent::getName).orElse(UNKNOWN_INTENT);

    if (FIRST_INTENT.equals(intentName)) {
      return new FirstOpeningIntent().createResponse(intent);
    } else if (FOLLOW_UP_INTENT.equals(intentName)) {
      return new FollowUpIntent().createResponse(intent);
    } else {
      throw new SpeechletException("Unknown intent received.");
    }
  }


  /**
   * Called when the user launches the skill without specifying what they want.
   */
  public SpeechletResponse onLaunch(final LaunchRequest launchRequest, final Session session) throws SpeechletException {
    // Here you could create a response which ask what the user wants.
    return null;
  }

  /**
   * Called when the session starts.
   */
  public void onSessionStarted(final SessionStartedRequest sessionStartedRequest, final Session session) throws SpeechletException {
    // Initialization logic
  }

  /**
   * Called when the user ends the session.
   * Is not called when the skill returns shouldEndSession=true.
   */
  public void onSessionEnded(final SessionEndedRequest sessionEndedRequest, final Session session) throws SpeechletException {
    // Cleanup logic
  }
}

