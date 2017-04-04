def lambda_handler(event, context):
    # Uncomment this if statement and populate with your skill's application ID to
    # prevent someone else from configuring a skill that sends requests to this function.

    #if (event["session"]["application"]["applicationId"] !=
    #        "amzn1.echo-sdk-ams.app.[unique-value-here]"):
    #    raise ValueError("Invalid Application ID")

    if event["session"]["new"]:
        on_session_started({"requestId": event["request"]["requestId"]}, event["session"])

    if event["request"]["type"] == "LaunchRequest":
        return on_launch(event["request"], event["session"])
    elif event["request"]["type"] == "IntentRequest":
        return on_intent(event["request"], event["session"])
    elif event["request"]["type"] == "SessionEndedRequest":
        return on_session_ended(event["request"], event["session"])

""" Called when the user specifies an intent for this skill. """
def on_intent(intent_request, session):
    intent = intent_request["intent"]
    intent_name  = intent_request["intent"]["name"]

    if intent_name  == "MyFirstOpeningIntent":
        return call_my_first_opening_intent(intent)
    elif intent_name  == "MyFollowUpIntent":
        return call_my_follow_up_intent(intent)
    else:
        raise ValueError("Invalid intent")

"""
This is your first Alexa Intent! Typically it greats you, or something alike, and asks you what you want to do next.
Because #should_end_session is false, it will keep the session open, so you can call the same skill-name without specifying it again.
In other words, you can have a conversation with Alexa within the possibilities of the current skill.
Utterance: "Alexa, tell 'Super Awesome Skill' my name is Deniz." (Session will stay open)
Follow up with: "greet me with WAZAAAAAA" (call skill below)
"""
def call_my_first_opening_intent(intent):
    session_attributes = {}
    card_title = "welcome"
    name  = intent["slots"]["MyName"]["value"]
    speech_output = "Hi %s, I am so relieved I've got someone to talk to! What can I do for you, sweetie?" % name
    repromt_text = "I've got no clue to what you want from me... Tell me again, please?"
    should_end_session = False

    return build_response(session_attributes, build_speechlet_response(
        card_title, speech_output, repromt_text, should_end_session))

"""
This is a stand alone Intent. It will close the session after you called it. However, you can call this one without specifying the skill name
if you are in a conversation with Alexa and the session stays open.
In addition, this skill will pass a message from the request. You should specify this message in the Intent in alexa developer.
You call either:
"Alexa, tell 'Super Awesome Skill' to Greet me with what's up" (New session)
or
"Greet me with what's up" (Session open)
"""
def call_my_follow_up_intent(intent):
    session_attributes = {}
    card_title = "greeting"
    greeting = intent["slots"]["Greeting"]["value"]
    speech_output = "%s, you're the best!" % greeting
    repromt_text = "Sorry, I could't make any chocolate of that" # This is an awesome Dutch saying :)
    should_end_session = True

    return build_response(session_attributes, build_speechlet_response(
        card_title, speech_output, repromt_text, should_end_session))

""" Called when the session starts. """
def on_session_started(session_started_request, session):
    print "on session started"

""" Called when the user launches the skill without specifying what they want. """
def on_launch(launch_request, session):
    print "on launch"

"""
Called when the user ends the session.
Is not called when the skill returns should_end_session=true.
"""
def on_session_ended(session_ended_request, session):
    print "on session ended"
    # Add cleanup logic here

""" Helper function that builds all of the responses. """
def build_speechlet_response(title, output, reprompt_text, should_end_session):
    return {
        "outputSpeech": {
            "type": "PlainText",
            "text": output
        },
        "card": {
            "type": "Simple",
            "title": title,
            "content": output
        },
        "reprompt": {
            "outputSpeech": {
                "type": "PlainText",
                "text": reprompt_text
            }
        },
        "should_end_session": should_end_session
    }

""" Helper function that builds all of the responses. """
def build_response(session_attributes, speechlet_response):
    return {
        "version": "1.0",
        "sessionAttributes": session_attributes,
        "response": speechlet_response
    }