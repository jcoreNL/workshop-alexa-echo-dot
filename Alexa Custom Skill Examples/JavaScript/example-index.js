'use strict';

function buildResponse(sessionAttributes, speechletResponse) {
    return {
        version: '1.0',
        response: speechletResponse,
        sessionAttributes,
    };
}

// --------------- Functions that control the skill's behavior -----------------------

/**
 * This is your first Alexa Intent! Typically it greats you, or something alike, and asks you what you want to do next.
 * Because #shouldEndSession is false, it will keep the session open, so you can call the same skill-name without specifying it again.
 * In other words, you can have a conversation with Alexa within the possibilities of the current skill.
 * Utterance: "Alexa, tell 'Super Awesome Skill' my name is Deniz." (Session will stay open)
 * Follow up with: "greet me with WAZAAAAAA" (call skill below)
 */
function callMyFirstOpeningIntent(intentRequest, callback) {
    const sessionAttributes = {};
    const cardTitle = 'welcome';
    const name = intentRequest.intent.slots.MyName.value;
    const speechOutput = `Hi ${name} , I am so relieved I've got someone to talk to! What can I do for you, sweetie?`;
    const repromptText = "I've got no clue to what you want from me... Tell me again, please?";
    const shouldEndSession = false;

    callback(sessionAttributes,
        buildSpeechletResponse(cardTitle, speechOutput, repromptText, shouldEndSession));
}

/**
 * This is a stand alone Intent. It will close the session after you called it. However, you can call this one without specifying the skill name
 * if you are in a conversation with Alexa and the session stays open.
 * In addition, this skill will pass a message from the request. You should specify this message in the Intent in alexa developer.
 * You call either:
 * "Alexa, tell 'Super Awesome Skill' to Greet me with what's up" (New session)
 * or
 * "Greet me with what's up" (Session open)
 */
function callMyFollowUpIntent(intentRequest, callback) {
    const sessionAttributes = {};
    const cardTitle = 'greeting';
    const greeting = intentRequest.intent.slots.Greeting.value;
    const speechOutput = `${greeting}, you're they are the best!`;
    const repromptText = "Sorry, I could't make any chocolate of that"; //This is an awesome Dutch saying :)
    const shouldEndSession = true;

    callback(sessionAttributes,
        buildSpeechletResponse(cardTitle, speechOutput, repromptText, shouldEndSession));
}


// --------------- Helpers that build all of the responses -----------------------

function buildSpeechletResponse(title, output, repromptText, shouldEndSession) {
    return {
        outputSpeech: {
            type: 'PlainText',
            text: output,
        },
        card: {
            type: 'Simple',
            title: `SessionSpeechlet - ${title}`,
            content: `SessionSpeechlet - ${output}`,
        },
        reprompt: {
            outputSpeech: {
                type: 'PlainText',
                text: repromptText,
            },
        },
        shouldEndSession,
    };
}


// --------------- Events -----------------------

/**
 * Called when the session starts.
 */
function onSessionStarted(sessionStartedRequest, session) {
    console.log(`onSessionStarted requestId=${sessionStartedRequest.requestId}, sessionId=${session.sessionId}`);
}

/**
 * Called when the user launches the skill without specifying what they want.
 */
function onLaunch(launchRequest, session) {
    console.log(`onLaunch requestId=${launchRequest.requestId}, sessionId=${session.sessionId}`);
}

/**
 * Called when the user specifies an intent for this skill.
 */
function onIntent(intentRequest, session, callback) {
    console.log(`onIntent requestId=${intentRequest.requestId}, sessionId=${session.sessionId}`);


    const intentName = intentRequest.intent.name;

    // Dispatch to your skill's intent handlers
    if (intentName === 'MyFirstOpeningIntent') {
        callMyFirstOpeningIntent(intentRequest, callback);
    }
    else if (intentName === 'MyFollowUpIntent') {
        callMyFollowUpIntent(intentRequest, callback);
    } else {
        throw new Error('Invalid intent');
    }
}

/**
 * Called when the user ends the session.
 * Is not called when the skill returns shouldEndSession=true.
 */
function onSessionEnded(sessionEndedRequest, session) {
    console.log(`onSessionEnded requestId=${sessionEndedRequest.requestId}, sessionId=${session.sessionId}`);
    // Add cleanup logic here
}


// --------------- Main handler -----------------------

// Route the incoming request based on type (LaunchRequest, IntentRequest,
// etc.) The JSON body of the request is provided in the event parameter.
exports.handler = (event, context, callback) => {
    try {
        console.log(`event.session.application.applicationId=${event.session.application.applicationId}`);

        /**
         * Uncomment this if statement and populate with your skill's application ID to
         * prevent someone else from configuring a skill that sends requests to this function.
         */

        // if (event.session.application.applicationId !== 'amzn1.echo-sdk-ams.app.[unique-value-here]') {
        // callback('Invalid Application ID');
        // }


        if (event.session.new) {
            onSessionStarted({requestId: event.request.requestId}, event.session);
        }

        if (event.request.type === 'LaunchRequest') {
            onLaunch(event.request, event.session);
        } else if (event.request.type === 'IntentRequest') {
            onIntent(event.request,
                event.session,
                (sessionAttributes, speechletResponse) => {
                    callback(null, buildResponse(sessionAttributes, speechletResponse));
                });
        } else if (event.request.type === 'SessionEndedRequest') {
            onSessionEnded(event.request, event.session);
            callback();
        }
    } catch (err) {
        callback(err);
    }
};
