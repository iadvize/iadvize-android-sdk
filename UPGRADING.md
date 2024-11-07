## 2.14.2 > 2.14.3

*Nothing to report*

## 2.14.1 > 2.14.2

This release updates the build configuration to the following:

- Gradle `8.10.2`
- Android Gradle Plugin `8.6.1`
- Java `17`
- Kotlin `2.0.0`
- Android Minimum SDK `24`
- Android Compile SDK `35`
- Android Target SDK `35`
- Android Build Tools `35.0.0`

Depending on your project, you may need to update your configuration to integrate this version.

## 2.14.0 > 2.14.1

*Nothing to report*

## 2.13.1 > 2.14.0

### Debug Info

This releases adds a new `debugInfo` API that returns the status of the SDK at any given moment. This API could be used for debugging
purposes, you can add the JSON string output to your log reporting tool.

```
IAdvizeSDK.debugInfo()
```

```
{
  "targeting": {
    "screenId": "67BA3181-EBE2-4F05-B4F3-ECB07A62FA92",
    "activeTargetingRule": {
      "id": "D8821AD6-E0A2-4CB9-BF45-B2D8A3CF4F8D",
      "conversationChannel": "chat"
    },
    "isActiveTargetingRuleAvailable": false,
    "currentLanguage": "en"
  },
  "device": {
    "model": "iPhone",
    "osVersion": "17.5",
    "os": "iOS"
  },
  "ongoingConversation": {
    "conversationChannel": "chat",
    "conversationId": "02012815-4BDA-42EF-87DC-5C6ED317AF7F"
  },
  "chatbox": {
    "useDefaultFloatingButton": true,
    "isChatboxPresented": false
  },
  "activation": {
    "activationStatus": "activated",
    "authenticationMode": "simple",
    "projectId": "7260"
  },
  "connectivity": {
    "wifi": true,
    "isReachable": true,
    "cellular": false
  },
  "visitor": {
    "vuid": "d4a57969c7fc4e2a9380f3931fdcee3a965650eb9c6b4",
    "tokenExpiration": "2025-02-27T08:14:11Z"
  },
  "sdkVersion": "2.15.4"
}
```

### Targeting Listener failure callback

This release also adds a callback to notify the integrator about targeting rule trigger failures. This takes the form of a new callback inside
the `TargetingListener`: 

```
interface TargetingListener {
    fun onActiveTargetingRuleAvailabilityUpdated(isActiveTargetingRuleAvailable: Boolean)

    fun onActiveTargetingRuleAvailabilityUpdateFailed(error: IAdvizeSDK.Error)
}
```

This will be called when triggering the targeting rule fails and give the reason of the failure when possible.
Please note that the targeting rule triggering may fail, but for standard reasons (for instance if there is no agent availabale to answer). In those cases this `onActiveTargetingRuleAvailabilityUpdateFailed` callback would not be called, only the usual `onActiveTargetingRuleAvailabilityUpdated` would be called with a `false` value for `isActiveTargetingRuleAvailable`.

> To integrate this update you will have to update your code where you use a `TargetingListener` to add this new callback.

### Error ecapsulation

The iAdvize SDK errors are now all part of a generic `IAdvizeSDK.Error` object. This is now the type that is used in the `IAdvizeSDK. Callback` failure method (that is used as an asynchronous return for multiple APIs).

```
IAdvizeSDK.activate(
  projectId,
  authenticationOption,
  gdprOption,
  object : IAdvizeSDK.Callback {
      override fun onSuccess() {
          // Success
      }

      override fun onFailure(error: IAdvizeSDK.Error) {
          // Error
      }
  })
```

> To integrate this update you will have to update your code where you use an `IAdvizeSDK.Callback`, especially in the `initiate`, `activate` ot the notification methods.

## 2.13.0 > 2.13.1

This release adds a new LogLevel.ALL to force the logging of all possible logs of the SDK. This must be used with caution as latencies may be noticed in the hosting app, so do not use this feature without iAdvize explicit authorization for live debugging.

## 2.12.9 > 2.13.0

*Nothing to report*

## 2.12.8 > 2.12.9

*Nothing to report*

## 2.12.7 > 2.12.8

*Nothing to report*

## 2.12.6 > 2.12.7

*Nothing to report*

## 2.12.5 > 2.12.6

*Nothing to report*

## 2.12.4 > 2.12.5

The `initiate` API now has a IAdvizeSDK.Callback as an optional argument to inform the host app if it fails.
Before calling that callback failure method, the API will first retry once, so if the callback failure is called it means the 
API has failed twice to initiate the SDK.

## 2.12.3 > 2.12.4

In this release the Push Notification APIs has been enhanced so that you can now clear the iAdvize Push Notifications on demand.

The SDK now provides a specific Notification Channel, where all iAdvize push notifications may be placed. That way, the SDK will automatically clear this notification channel when Chatbox is opened, and you can clear it manually by calling one of the SDK APIs.

First of all you need to create this noticfication channel:

```
IAdvizeSDK.notificationController.createNotificationChannel(context)
```

Like before, when receiving a notification, you can check if it's an iAdvize push notifications. If so specify the iAdvize SDK channel id when displaying it :

```
if (IAdvizeSDK.notificationController.isIAdvizePushNotification(remoteMessage.data)) {

  val notification = NotificationCompat.Builder(this, IAdvizeSDK.notificationController.channelId)
    ... // notification config
    .build()
} else {
    // Host app notification handling
}
```

This Notification Channel is automatically cleared when opening the Chatbox, if you want to clear it manually at another time you can call this API:

```
IAdvizeSDK.notificationController.clearIAdvizePushNotifications()
```

## 2.12.2 > 2.12.3

*Nothing to report*

## 2.12.1 > 2.12.2

*Nothing to report*

## 2.12.0 > 2.12.1

*Nothing to report*

## 2.11.0 > 2.12.0

*Nothing to report*

## 2.10.1 > 2.11.0

This release deprecates the ChatboxConfiguration.mainColor setting and adds new ways to customize the look and feel of the messages, both the ones from the visitor and the ones from the agent. Please review the new parameters to customize them to your liking.

This release adds a new LogLevel.NONE to disable all console logs and all logging capture. Please note that this disables iAdvize functional logs aggregation as well so debugging issues will be made harder if this mode is chosen.

## 2.10.0 > 2.10.1

*Nothing to report*

## 2.9.2 > 2.10.0

From this release and onward, the possibility to upload files in the conversation is based on the option
available in the Admin Chatbox Builder. To enable/disable it go to your iAdvize Administration Panel then :
> Engagement > Notifications & Chatbox > Chatbox (Customize) > Composition box (tab) > Allow the visitor to upload images and pdf

## 2.9.1 > 2.9.2

*Nothing to report*

## 2.9.0 > 2.9.1

*Nothing to report*

## 2.8.3 > 2.9.0

*Nothing to report*

## 2.8.2 > 2.8.3

The Kotlin version used in the SDK was updated from `1.7.20` to `1.8.10`. You will need to update your
Kotlin version accordingly in order for your project to compile.

## 2.8.1 > 2.8.2

*Nothing to report*

## 2.8.0 > 2.8.1

*Nothing to report*

## 2.7.0 > 2.8.0

Release 2.8 added support for chat-to-video escalation. Agents can now during a CHAT conversation
propose a video call to a visitor. The experience and the flow are similar to the VIDEO channel
conversations.

The Chatbox API has seen some minor changes.

`IAdvizeSDK.chatboxController.presentChatbox(context)` now does not open the Chatbox anymore if the
conditions for conversation are not met. The conditions are the same than the ones controlling the
display of the chat button : there should be either an ongoing conversation or an available active
targeting rule. A new API has been added to close the Chatbox programmatically :
`IAdvizeSDK.chatboxController.dismissChatbox()`

## 2.6.0 > 2.7.0

*Nothing to report*

## 2.5.1 > 2.6.0

Release 2.6 added support for secured authentication. Instead of using `Anonymous` or `Simple`
authentication option, you can now choose to use your own authentication system and pass it along to
the SDK. You will need to configure a JWEProvider and pass it to the SDK in the activation method:

```
IAdvizeSDK.activate(
  projectId,
  AuthenticationOption.Secured(object : AuthenticationOption.JWEProvider {
    override fun onJWERequested(callback: AuthenticationOption.JWECallback) {
      // Fetch JWE from your own secure auth process
      callback.onJWERetrieved(jwe)
      // or callback.onJWEFailure(exception)
    }
  }),
  gdprOption,
  callback
)
```

## 2.5.0 > 2.5.1

*Nothing to report*

## 2.4.3 > 2.5.0

Starting with this version 2.5 the SDK added the support for video conversations. This feature made
us review our internal code architecture so updating to this latest release needs some migration
work.

### Package modification

A lot of the SDK classes have be moved to a more straightforward package structure. Here are some
examples :

```
com.iadvize.conversation.sdk.utils.logger.Logger => com.iadvize.conversation.sdk.feature.logger.Logger
com.iadvize.conversation.sdk.model.language.LanguageOption => com.iadvize.conversation.sdk.feature.targeting.LanguageOption
com.iadvize.conversation.sdk.model.auth.AuthenticationOption => com.iadvize.conversation.sdk.feature.authentication.AuthenticationOption
com.iadvize.conversation.sdk.controller.conversation.ConversationListener => com.iadvize.conversation.sdk.feature.conversation.ConversationListener
com.iadvize.conversation.sdk.model.SDKCallback => com.iadvize.conversation.sdk.IAdvizeSDK.Callback
```

### Default Floating Button

All the APIs of the iAdvize default floating button have been moved to dedicated classes (there were
previously part of the ChatboxConfiguration) :

**Before**

```
IAdvizeSDK.chatboxController.setChatButtonPosition(16, 16)
IAdvizeSDK.chatboxController.useDefaultChatButton = true
```

**After**

```
IAdvizeSDK.defaultFloatingButtonController.setupDefaultFloatingButton(
  DefaultFloatingButtonOption.Enabled(
    DefaultFloatingButtonConfiguration(
      anchor = Gravity.START or Gravity.BOTTOM, 
      margins = DefaultFloatingButtonMargins(), 
      backgroundTint = ContextCompat.getColor(this, R.color.colorPrimary),
      iconTint = Color.WHITE,
    )
  )
)
```

### ConversationChannel

In order to manage textual conversation as well as video ones, a `ConversationChannel` object was
added. This information has to be used during the rule activation using the added wrapper
class `TargetingRule`.

**Before**

```
IAdvizeSDK.targetingController.activateTargetingRule(UUID.fromString("your-targeting-rule-id"))
```

**After**

```
private val targetingRule = TargetingRule(
  UUID.fromString("your-targeting-rule-id"), ConversationChannel.CHAT // or ConversationChannel.VIDEO
)
IAdvizeSDK.targetingController.activateTargetingRule(targetingRule)
```

The information is passed along the `ongoingConversation` flag also using an added wrapper
class `OngoingConversation`.

**Before**

```
override fun onOngoingConversationStatusChanged(hasOngoingConversation: Boolean) { 
  Log.d("iAdvize SDK Demo", "SDK update hasOngoingConversation: $hasOngoingConversation")
}
```

**After**

```
override fun onOngoingConversationUpdated(ongoingConversation: OngoingConversation?) { 
  Log.d("iAdvize SDK Demo","SDK update hasOngoingConversation: ${ongoingConversation != null}")
}
```

### Integration documentation

Please refer to our up-to-date public integration documentation if needed, it contains code snippets
for each feature of the SDK:
https://developers.iadvize.com/documentation/mobile-sdk
