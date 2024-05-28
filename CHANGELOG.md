# 2.12.9 (Cantal)

### Release date 2024/05/28

**Bug fixes**

- Fix isolated markdown deeplink handling

# 2.12.8 (Cantal)

### Release date 2024/05/14

**Bug fixes**

- Fix line feed escaping breaking markdown syntax in bot messages
- Fix text selection breaking markdown link handling

**Dependencies**

# 2.12.7 (Cantal)

### Release date 2024/04/11

**Bug fixes**

- Fix secured preferences initialization issue in case of modified decryption key

# 2.12.6 (Cantal)

### Release date 2024/04/08

**Features**

- Add copy-paste selection in messages

**Bug fixes**

- Fix secured preferences initialization issue with Android auto-backup strategy

# 2.12.5 (Cantal)

### Release date 2024/03/18

**Features**

- Rework the initiate API, adding a callback + implementing retry behavior

**Bug fixes**

- Fix markdown links not triggering the SDK click handler
- Fix a crash occuring when visitor spam messages
- Add some missing obfuscation instructions

# 2.12.4 (Cantal)

### Release date 2024/01/11

**Features**

- Add APIs for managing iAdvize Push Notifications more finely

# 2.12.3 (Cantal)

### Release date 2023/12/20

**Features**

- Support simple Markdown syntax inside QuickReply messages
- Add some translations for GDPR messages (cs, da, pl, sk, sv)

**Bug fixes**

- Fix a display issue on ProductOffer messages when no offer pric is set

**Dependencies**

- Remove deprecated `play-services-safetynet` dependency in favor of `play-services-basement`

# 2.12.2-rnbackport (Cantal)

### Release date 2023/12/05

This is a version created from the `2.12.2` release for integration into the 
ReactNative `0.72.7` plugin project.
Please use the `2.12.2` version if you are integrating on native Android.

**Dependencies**

Dependencies were downgraded to be aligned to the ReactNative `0.72.7` standards:

- Gradle `7.6`
- Android Gradle Plugin `7.4.1`
- Java `11`
- Kotlin `1.7.22`
- Android Minimum SDK `21`
- Android Compile SDK `33`
- Android Target SDK `33`
- Android Build Tools `33.0.2`

# 2.12.2 (Cantal)

### Release date 2023/11/27

**Bug fixes**

- Fix potential stuck state during GDPR process
- Fix conversation not being started properly if network disconnects during MUC/SUB subscription
- Remove OnBackPressedHandler which was causing issues in hybrid integrations

**Dependencies**

- Android Target SDK `33` -> `34`
- Android Gradle Plugin `8.1.1` -> `8.1.2`
- Kotlin `1.8.21` -> `1.9.20`
- Twilio `7.6.1` -> `7.6.4`

# 2.12.1 (Cantal)

### Release date 2023/10/23

**Features**

- Remove preview image when it is empty (previously used a placeholder)

**Bug fixes**

- Fix web & markdown links display

# 2.12.0 (Cantal)

### Release date 2023/10/23

**Features**

- Add automatic auth token refresh management

**Dependencies**

- Android Gradle Plugin `8.1.0` -> `8.1.1`

# 2.11.0 (Beaufort)

### Release date 2023/08/07

**Features**

- Allow a more sophisticated message color customization
- Add a LogLevel mode to remove all logs

**Bug fixes**

- Fix conversation management after various network connection issues (phone sleep / app in bakground)

**Dependencies**

- Android Gradle Plugin `7.4.1` -> `8.1.0`
- Android Build Tools `33.0.1` -> `33.0.2`
- Kotlin `1.8.10` -> `1.8.21`
- Apollo `3.7.5` -> `3.8.2`
- Firebase `31.0.0` -> `32.1.0`

# 2.10.1 (Angelot)

### Release date 2023/05/31

**Bug fixes**

- Fix font update on several message types

# 2.10.0 (Angelot)

### Release date 2023/05/25

**Features**

- Disable file attachment buttons when it is disabled in Admin chatbox template

**Bug fixes**

- Fix wrong message alignment

# 2.9.2

### Release date 2023/05/03

**Bug fixes**

- Fix crash when message continas an unrecognized deeplink

# 2.9.1

### Release date 2023/03/30

**Bug fixes**

- Fix message alignment on hybrid platforms

# 2.9.0

### Release date 2023/03/28

**Features**

- Disable satisfaction survey after failed bot transfer if parametrized in the admin
- Handle the Estimated Waiting Time messages

**Bug fixes**

- Fix pre-conversation custom data not being sent on conversation start
- Fix targeting process not being fully restarted after conversation end

**Dependencies**

- Apollo `3.6.2` -> `3.7.5`
- Twilio `7.5.1` -> `7.6.1`

# 2.8.3

### Release date 2023/02/08

**Bug fixes**

- Fix file picker permissions on Android 13

**Dependencies**

- Kotlin `1.7.20` -> `1.8.10`
- Android Gradle Plugin `7.3.1` -> `7.4.1`

# 2.8.2

### Release date 2022/12/30

**Bug fixes**

- Fix NPS values to 0-10 (was 1-10)

# 2.8.1

### Release date 2022/12/09

**Features**

- Add support for visitor blocking

**Bug fixes**

- Fix intempestive "new message" badge showing on default floating button
- Fix log levels on several warning stack traces

# 2.8.0

### Release date 2022/10/20

**Features**

- Add support for chat-to-video escalation

**Bug fixes**

- Fix multiple answers on GDPR & Satisfaction messages
- Add a failsafe on presentChatbox() API to prevent opening Chatbox when conditions are not met
- Add a dismissChatbox() API to close the Chatbox programmatically
- Add **FLAG_ACTIVITY_NEW_TASK** flag to SDK activities (issue for ReactNative integrations)

# 2.7.0

### Release date 2022/08/24

**Features**

- Basic Markdown support in messages
- Update preferences library to Android EncryptedSharedPreferences

**Bug fixes**

- Fix default picture showing in empty image cards
- Force UI thread on default floating button APIs

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