[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

<img src="https://user-images.githubusercontent.com/17723986/47799626-f3982700-dd2a-11e8-983c-77d1a3ed7f53.png" width="280" height="96" alt="iAdvize">

# Android Conversation SDK

Take your app to the next step and provide a unique conversational experience to your users!

Embed the iAdvize Conversation SDK in your app and connect your visitors with your professional agents or ibbü experts through a fully customised chat experience. Visitors can ask a question and will receive answers directly on their devices with push notifications, in or outside your app.

You will find an example of integration in the sample of this repository.

iAdvize Android Conversation SDK supports versions from API 16.

## [Integration](#integrate)
* [App creation](#creation)
* [SDK dependency](#dependency)
* [Logging](#logging)
* [Targeting Language](#targeting-language)
* [Registering your application ID](#register)
* [Activating the SDK](#activate)
* [GDPR](#gdpr)
* [Registering push token](#push)
* [Registering user](#user)
* [Chat button](#button)
* [Push notification](#notification)
* [Registering a transaction](#transaction)
* [Targeting rule](#targeting)

## [Customisation](#customise)
- [Chat button](#customisebutton)
- [Conversation view](#customiseconversation)
- [Main color](#customisecolor)
- [Navigation bar](#customisenavigation)
- [Font](#customisefont)
- [Automatic message](#customisemessage)
- [GDPR message](#customisegdpr)
- [Brand avatar](#avatar)

<a name="integrate"></a>
# Integration

<a name="creation"></a>
## App creation

Ask your iAdvize Admin to create a “Mobile App” on the administration website and to give you those two information displayed in the “Mobile App” list  (**application ID, JWT secret**).

To create the Mobile App, you will need to provide to your Administrator some information regarding the push notifications: just give your GCM API key to your administrator.

You can register your application to the `SDKStatusListener` in `IAdvizeManager` to be informed when the SDK is enabled or disabled in the iAdvize administration website.

<a name="dependency"></a>
## SDK dependency

**Step 1**. Add the JitPack maven repository to the list of repositories:
```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
        maven { url "https://raw.github.com/iadvize/iadvize-android-sdk/master" }
    }
}
```

**Step 2**. Link your project with the iAdvize Conversation SDK dependency, add this line to your app's `build.gradle`:
```gradle
//requires with AndroidX
dependencies {
    implementation 'com.iadvize:iadvize-sdk:1.7.0'
}

//to use before AndroidX
dependencies {
    implementation 'com.iadvize:iadvize-sdk:1.5.1'
}
```

> :warning: From version 1.6.0 the SDK uses androidx.

Now you should be able to import `com.iadvize.conversation.sdk.*` in ny file you want to use it.

<a name="logging"></a>
## Logging

By default, the SDK will only log Warnings and Errors in the Android Studio console. You can make it more verbose and choose between multiple levels of log for a better integration experience:
```kotlin
IAdvizeManager.logLevel = Logger.Level.VERBOSE
```

<a name="targeting-language"></a>
## Targeting Language

By default, the SDK will use the device language for **targeting a conversation**. With this variable you can specify the language you want to use for targetting:
```kotlin
IAdvizeManager.language = SDKLanguageOption.Custom(Language.FR)
```

> :warning: The `IAdvizeManager.language` property is NOT intended to change the language displayed in the SDK.

<a name="register"></a>
## Registering your application ID

Using the application ID (see Step 1 above), you can register your application by calling:
```kotlin
IAdvizeManager.registerApplicationId(this, "your-own-application-identifier-uuid")
```

<a name="activate"></a>
## Activating the SDK

⚠️⚠️⚠️ N.B. You have to check if the activation succeeds before you try to show a Chat Button (the default or a custom one). You also have to check the `isEnabled` flag which indicates you if the SDK is currently enabled or disabled by the SDK Administrator.

A listener is available in order to know if the SDK has been successfully activated (and to retry later if the activation fails):
```kotlin
IAdvizeManager.activate(JWTOption.Secret("yourjwtsecret"), "connecteduseruniqueidentifierornull", GDPROption.Disabled(), UUID.fromString("targetingruleid"), object : ActivateListener {
            override fun onActivateFailure(t: Throwable) {}
            override fun onActivateSuccess(isEnabled: Boolean) {}
        })
```

You also have to set your controller as a `SDKStatusListener`:

```
class MainActivity : SDKStatusListener, (...)
```


and implement both functions:

```
override fun onSdkDisabled() {
    // Hide or disable your custom chat button
}

override fun onSdkEnabled() {
    // Show or enable your custom chat button
}
```

You have two ways to activate the iAdvize Conversation SDK depending on the security model you choose.
 - For the in-app security model:
```kotlin
IAdvizeManager.activate(JWTOption.Secret("yourjwtsecret"), "connecteduseruniqueidentifierornull", GDPROption.Disabled(), UUID.fromString("targetingruleid"))
```
 - For the server-side security model:
```kotlin
IAdvizeManager.activate(JWTOption.Token("yourjwttoken"), "connecteduseruniqueidentifierornull", GDPROption.Disabled(), UUID.fromString("targetingruleid"))
```

The `externalId` a unique identifier you can provide to identify your connected user across sessions and devices. It should not contain any private information (should not be an email, a phone number, a name...) of the user and should be opaque and unforgeable (e.g. a dynamic UUID). If your user isn’t logged-in you can pass a void value for this parameter (`null` in Kotlin).

The `ruleId` allow to target visitors proactively or reactively, based on their browsing behaviour, their profile or their business criteria (e.g. basket amount, scoring, etc). Ask your iAdvize Admin to give you this identifier.

Once the iAdvize Conversation SDK is successfully activated, you should see a message like this in the IDE console:
```kotlin
iAdvize Conversation: ✅ iAdvize conversation activated, the version is x.x.x.
```

<a name="gdpr"></a>
## GDPR

By default when you activate the SDK, the GDPR will be disabled. You can activate the GDPR feature by passing a new parameter to the activate method and provide a mandatory Legal Information URL link or a delegate to manage your own action on the tap on `More information` button with it:
```kotlin
IAdvizeManager.activate(JWTOption.Token("yourjwttoken"), "connecteduseruniqueidentifierornull", gdprOption = GDPROption.Enabled(GDPREnabledOption.LegalUrl(URL("https://www.iadvize.com/en/legal-notice/"))), UUID.fromString("targetingruleid"))
```

```kotlin
IAdvizeManager.activate(JWTOption.Token("yourjwttoken"), "connecteduseruniqueidentifierornull", gdprOption = GDPROption.Enabled(GDPREnabledOption.Listener(this)), UUID.fromString("targetingruleid"))
```
The GDPR process is now activated for your users and a default message will be provided to collect the user consent. Please check the [Customise](#customise) section below if you want to customise this message.

<a name="push"></a>
## Registering push token

In order to allow your users to receive operators or experts answers in real time, you should register the current push token of the device:
```kotlin
IAdvizeManager.registerPushToken("pushtoken")
```
You can register it at any time after you activate the SDK (Step 4).

<a name="user"></a>
## Registering user

You can register User information which will be displayed in the profile section of the ChatBox on the operator Desk. To do so just call the `registerUser()` method on the `IAdvizeManager` object:
```kotlin
IAdvizeManager.registerUser(User("Antoine"))
```
Note: You need to activate the SDK (see step 4) before registering the User.

<a name="button"></a>
## Chat button

In order to invite your users to enter in a conversational experience, you have to display the default Chat button (see the [Customise](#customise) section below if you want to display your own). You can also, at any time, hide it. To do this you can simply use:
```kotlin
IAdvizeConversationManager.showChatButton()
IAdvizeConversationManager.hideChatButton()
```

You can use your own button to display the conversation by calling the following method:
```kotlin
IAdvizeConversationManager.presentConversationViewActivity(context)
```

<a name="notification"></a>
## Push notification

Once you receive a push notification, you can easily verify that this notification concerns the SDK and ask the SDK to handle this notification for you:
```kotlin
IAdvizeManager.isIAdvizePushNotification(remoteMessage.getData())
IAdvizeManager.handlePushNotification(this, remoteMessage.getData(), R.mipmap.notification_logo)
```
*where `remoteMessage` is the object representing the push notification and `this` the application context.

If you need, you can enable or disable the push notifications (there are enabled by default)
```kotlin
IAdvizeManager.enablePushNotifications(object : EnablePushNotificationsListener {
    override fun onEnableSuccess() {}
    override fun onEnableFailure(t: Throwable) {}
})

IAdvizeManager.disablePushNotifications(object : DisablePushNotificationsListener {
    override fun onDisableSuccess() {}
    override fun onDisableFailure(t: Throwable) {}
})
```

<a name="transaction"></a>
## Registering a transaction

When you want to register a transaction within your application, you can call the following method by passing a `Transaction` object:
```kotlin
IAdvizeTransactionManager.register(Transaction("transactionId", Date(), 10.00, Currency.EUR))
```

<a name="targeting"></a>
## Targeting rule

When you want to update a targeting rule, you can call the following method by passing the new `ruleId`:
```kotlin
IAdvizeManager.setTargetingRule(UUID.fromString("myNewRuleId"))
```

To customise the SDK, check the next section.

<a name="customise"></a>
# Customisation

<a name="customisebutton"></a>
## Chat button

By default, the SDK provides you a Chat button which you can integrate as-is. You can easily implement your own Chat button and by just calling this method below, you will be able to present the Conversation View:

```kotlin
IAdvizeConversationManager.presentConversationViewActivity(context)
```

You can get the number of unread messages via the method: 

```kotlin
IAdvizeConversationManager.unreadMessages()
```

You can also update the Chat Button position:

```kotlin
/**
 * Update the chat button (the default one) position. Use this method if you use the iAdvize default
 * chat button and you use showChatButton()/hideChatButton() methods to display/hide it.
 *
 * The default chat button is aligned to the bottom-left of the screen with a default
 * margin to (left: 10, bottom: 10). Increasing the bottom margin value will move the chat
 * button up and increasing the left margin value will move the chat button left.
 *
 *          Container view (default = main window)
 *          +----------------------------------+
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |                                  |
 *          |             chatButtonView       |
 * leftMargin = 10   +----+                    |
 *          <------> |    |                    |
 *          |        |    |                    |
 *          |        +--^-+                    |
 *          |            |                     |
 *          |            | bottomMargin = 10   |
 *          +------------v---------------------+
 */

IAdvizeConversationManager.setChatButtonPosition(leftMarginInPx, bottomMarginInPx)
```

<a name="customiseconversation"></a>
## Conversation view

For the next steps, you will have access to a configuration “object” to customise all Conversation View attributes. You can access the default configuration and update it like this:

```kotlin
// Get the current configuration
val configuration = IAdvizeConversationManager.configuration
// Update some configuration attributes
configuration.mainColor = Color.RED
// Setup this new configuration
IAdvizeConversationManager.setupConversationView(configuration)
```

For the next steps, we will only show you the different attributes that you can setup on this “configuration” object.

<a name="customisecolor"></a>
## Main color

You can setup a main color on the SDK which will be applied to the color of:

- the default Chat button (if you use it)
- the send button in the Conversation View
- the blinking text cursor in the “new message” input in the Conversation View
- the background color of the message bubbles (only for sent messages)

```kotlin
// Update the main color
configuration.mainColor = Color.RED
```

<a name="customisenavigation"></a>
## Navigation bar

You can configure the Toolbar of the Conversation View and modify:

- the background color
- the main color
- the title

```kotlin
// Update the background color of the navigation bar
configuration.toolbarBackgroundColor = Color.BLACK
// Update the main color of the toolbar (color of buttons and labels inside it)
configuration.toolbarMainColor = Color.WHITE
// Update the title of the navigation bar
configuration.toolbarTitle = "Conversation"
```

<a name="customisefont"></a>
## Font

You can update the font used in the UI of the IAdvize Conversation SDK. You just have to call this method to setup your own font:

```kotlin
// Update the font
configuration.fontPath = "fonts/ProximaNova-Regular.otf"
```

The font file must be in the assets of your application. Here the file is in `assets/fonts/ProximaNova-Regular.otf`

<a name="customisemessage"></a>
## Automatic message

A first automatic message can be setup to be displayed as an operator message in the Conversation View. By default, no message will be displayed. This message will also be used and displayed when the user accepts the GDPR. You can set an automatic message through the Conversation configuration:

```kotlin
// Update the automatic message
configuration.automaticMessage = "Hi, just ask a question and we will answer you asap!"
```

<a name="customisegdpr"></a>
## GDPR message

If you want to activate the GDPR consent collect feature through the iAdvize Conversation SDK, please refer to the [GDPR](#gdpr) section above.

Once the GDPR is activated, you can easily customise the GDPR message you want to display to your users to collect their consent:

```kotlin
// Update the GDPR message
configuration.gdprMessage = "Your own GDPR message."
```

<a name="avatar"></a>
## Brand avatar

You can update the brand avatar displayed for the incoming messages. You can specify an URL or a Drawable. Gifs are not supported. You can set an avatar through the Conversation configuration:

```kotlin
// Update the incoming message avatar with a Drawable
configuration.incomingMessageAvatar = IncomingMessageAvatar.Image(ContextCompat.getDrawable(this, R.drawable.avatar))

// Update the incoming message avatar with an URL
configuration.incomingMessageAvatar = IncomingMessageAvatar.Url(URL("your-url"))
```

Well done! You’re now ready to take your app to the next step and provide a unique conversational experience to your users! 🚀
