package com.workshop.alexa;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

import java.util.Optional;

public class AlexaUtil {
  private static final String NO_VALUE = "No value";

  public static SpeechletResponse createSpeechletResponse(final String title, final String speechText) {
    return SpeechletResponse.newTellResponse(createPlainTextOutputSpeech(speechText), createSimpleCard(title, speechText));
  }

  /**
   * Gets the value of a slot. Returns a default value if it isn't set.
   *
   * @param slot The slot.
   */
  public static String getSlotValue(final Slot slot) {
    return Optional.ofNullable(slot).map(Slot::getValue).orElse(NO_VALUE);
  }

  /**
   * Creates a visual representation of the response, in the form of a card (it's shown in the Alexa app for example).
   *
   * @param title The title of the card.
   * @param content The content of the card
   */
  private static SimpleCard createSimpleCard(final String title, final String content) {
    final SimpleCard card = new SimpleCard();
    card.setTitle(title);
    card.setContent(content);
    return card;
  }

  /**
   * Creates a PlainTextOutputSpeech object which tells Alexa what to say.
   *
   * @param speechText The text Alexa has to say.
   */
  private static PlainTextOutputSpeech createPlainTextOutputSpeech(final String speechText) {
    final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return speech;
  }
}
