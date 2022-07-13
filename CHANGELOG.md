# 2.6.0

### Release date 2022/07/08

- Add secured authentication management
- Add support for georouting cards
- Add visitor event tracking management
- Bug fixes & performance improvements

# 2.5.2

### Release date 2022/06/15

- Fix camera not starting when taking a picture

# 2.5.1

### Release date 2022/05/20

- Add video flow events
- Bug fixes & performance improvements

# 2.5.0

### Release date 2022/05/12

> *⚠️ This release brings breaking API changes.*

- Add support for video conversations
- Bug fixes & performance improvements

# 2.4.3

### Release date 2022/02/24

- Bug fixes & performance improvements

# 2.4.2

### Release date 2022/02/23

- Bug fixes & performance improvements

# 2.4.1

### Release date 2022/02/14

- Update build to support Android 32 / AGP 7.1 / Kotlin 1.6 / Java 11
- Bug fixes & performance improvements

# 2.4.0

### Release date 2022/02/11

- Update build to support Android 31 / AGP 7+ / Kotlin 1.6 / Java 11
- Fix previews in link messages
- Add satisfaction flow
- Fix activation success callback not being called when SDK is already activated

# 2.3.3

### Release date 2022/01/14

- Fix concurrency issues with conversation message list access
- Fix crash when opening attachement in conversation

# 2.3.2

### Release date 2021/12/10

- Allow to maintain the current targeting rule active while registering a user navigation
- Bug fixes & performance improvements

# 2.3.0

### Release date 2021/11/25

- Add a visual indicator on default chat button when there are unread visitor messages
- Add an API to save visitor conversation custom data
- Add typing indicator on visitor and agent side
- Bug fixes & performance improvements

# 2.2.4

### Release date 2021/10/22

- Add chatbox open/close listener
- Bug fixes & performance improvements

# 2.2.3

### Release date 2021/10/08

- Add access to SDK activation status
- Bug fixes & performance improvements

# 2.2.2

### Release date 2021/09/30

- Bug fixes & performance improvements

# 2.2.1

### Release date 2021/09/10

- Bug fixes & performance improvements

# 2.2.0

### Release date 2021/09/10

- Add anonymous authentication
- Add the ability to load past conversations
- Bug fixes & performance improvements

# 2.1.0-beta6

### Release date 2021/08/04

- Various bug fixes & performance improvements

# 2.1.0-beta5

### Release date 2021/08/03

- Pre-recorded links display
- Various bug fixes & performance improvements

# 2.1.0-beta4

### Release date 2021/07/30

- Fix file attachment
- Various bug fixes & performance improvements

# 2.1.0-beta3

### Release date 2021/07/21

- Bug fixes & performance improvements

# 2.1.0-beta2

### Release date 2021/07/16

- Add targeting rule availability polling
- Bug fixes & performance improvements

# 2.1.0-beta1

### Release date 2021/07/07

- Add pro-actives bots
- Hide sensitives information from chatbox
- Bug fixes & performance improvements

# 2.0.0-beta1

### Release date 2021/06/11

- Add new conversation workflow to manage real time conversation & targeting
- Revamp SDK Architecture

# 1.7.0

### Release date 2021/01/27

- Modify the behaviour of the application deactivation which is now only defined by the
  administration flag: activated or deactivated. We don't take into account if the visitor has
  already chatted.

# 1.6.0

### Release date 2020/10/20

- Update to Android SDK 30
- Update dependency versions
- Fix an issue to send photo on Android 11 devices.

# 1.5.1

### Release date 2020/09/24

- Fix an issue to send photo on several devices above Android 10.
- Fix crash when a visitor quit the Conversation View quickly after refuse GDPR.
- Try to fix proguard issue with jjwt

# 1.5.0

### Release date 2020/06/15

- Add Portuguese localization

# 1.4.0

### Release date 2020/04/28

- iAdvize SDK is now translated into Lithuanian.

# 1.3.6

### Release date 2020/02/17

- Fix a bug on notifications.
- Fix a bug on logger.

# 1.3.5

### Release date 2020/01/27

- Fix a bug when the user try to open a file just after uploaded it.

# 1.3.4

### Release date 2019/12/19

- Fix integration conflict on FileProvider

# 1.3.3

### Release date 2019/07/11

- Added a GDPR listener to handle in a custom way the "More Information" action of the user.

### Release date 2019/06/21

- Fix the access level of properties of some variables of the IAdvizeManager and
  IAdvizeConversationManager classes.

# 1.3.1

### Release date 2019/06/04

- Added a callback to inform that the conversation was opened.
- Give access to unread count messages on IAdvizeConversationManager.

# 1.3.0

### Release date 2019/05/14

- Added the possibility to send and receive attachments (files or images) in conversation.
- Fix unread messages counter in the IAdvizeConversationManagerListener.
- Update the documentation to precise that the SDK must be activated before registering a
  User (`IAdvizeManager.registerUser(User("Antoine"))`).

### Release date 2019/04/30

- Expose the IAdvizeConversationManagerListener to be able to subscribe to new messages and unread
  messages counter events.

# 1.2.2

### Release date 2019/03/21

- Fix when updating the conversation when the visitor receives a message via a push notification.

# 1.2.1

### Release date 2019/03/19

- Using the automatic message when accepting the GDPR.

# 1.2.0

### Release date 2019/02/21

- Add a property in the ConversationViewConfiguration to update the avatar displayed for the
  incoming messages.
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