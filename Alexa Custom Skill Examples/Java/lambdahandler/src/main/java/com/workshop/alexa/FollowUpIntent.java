package com.workshop.alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;

public class FollowUpIntent {
  private static final String INTENT_RESPONSE = "%s, you're the best!";
  private static final String INTENT_CARD_TITLE = "greeting";
  private static final String GREETING_SLOT = "Greeting";

  public SpeechletResponse createResponse(final Intent intent) {
    final String greeting = AlexaUtil.getSlotValue(intent.getSlot(GREETING_SLOT));
    final String speechText = String.format(INTENT_RESPONSE, greeting);
    return AlexaUtil.createSpeechletResponse(INTENT_CARD_TITLE, speechText);
  }
}
