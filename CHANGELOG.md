# 1.3.1

### Release date 2019/06/04

- Added a callback to inform that the conversation was opened.
- Give access to unread count messages on IAdvizeConversationManager.

# 1.3.0

### Release date 2019/05/14

- Added the possibility to send and receive attachments (files or images) in conversation.
- Fix unread messages counter in the IAdvizeConversationManagerListener.
- Update the documentation to precise that the SDK must be activated before registering a User (`IAdvizeManager.registerUser(User("Antoine"))`).

### Release date 2019/04/30

- Expose the IAdvizeConversationManagerListener to be able to subscribe to new messages and unread messages counter events.

# 1.2.2

### Release date 2019/03/21

- Fix when updating the conversation when the visitor receives a message via a push notification.

# 1.2.1

### Release date 2019/03/19

- Using the automatic message when accepting the GDPR.

# 1.2.0

### Release date 2019/02/21

- Add a property in the ConversationViewConfiguration to update the avatar displayed for the incoming messages.
- Add a property in the IAdvizeManger to update the language of the Conversation targeting.

# 1.1.2

### Release date 2019/01/16

- Hide the keyboard when a visitor quit the Conversation View.
- Fix on the management of dates in the conversation. 

# 1.1.1

### Release date 2018/12/19

- Fix crash when a visitor quit the Conversation View quickly.
- Update on the disabling/enabling the SDK feature via iAdvize Administration.

# 1.1.0

### Release date 2018/12/18

- Disabling/Enabling the SDK via iAdvize Administration.
- Added targeting rule to set up message delivery to operators.
- Use of server time for messages.

# 1.0.3

### Release date 2018/10/08

- iAdvize Conversation SDK first version.