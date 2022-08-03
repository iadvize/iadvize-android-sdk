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