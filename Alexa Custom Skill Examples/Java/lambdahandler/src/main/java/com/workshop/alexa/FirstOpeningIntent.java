package com.workshop.alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;

public class FirstOpeningIntent {
  private static final String INTENT_RESPONSE = "Hi %s, I am so relieved I've got someone to talk to! What can I do for you, sweetie?";
  private static final String INTENT_CARD_TITLE = "welcome";
  private static final String MY_NAME_SLOT = "MyName";

  public SpeechletResponse createResponse(final Intent intent) {
    final String myName = AlexaUtil.getSlotValue(intent.getSlot(MY_NAME_SLOT));
    final String speechText = String.format(INTENT_RESPONSE, myName);
    return AlexaUtil.createSpeechletResponse(INTENT_CARD_TITLE, speechText);
  }
}
