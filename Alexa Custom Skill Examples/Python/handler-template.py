def lambda_handler(event, context):
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
    # implement me

""" Called when the session starts. """
def on_session_started(session_started_request, session):
    # implement me

""" Called when the user launches the skill without specifying what they want. """
def on_launch(launch_request, session):
    # implement me

"""
Called when the user ends the session.
Is not called when the skill returns should_end_session=true.
"""
def on_session_ended(session_ended_request, session):
    # implement me

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