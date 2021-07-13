[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

<img src="https://user-images.githubusercontent.com/17723986/47799626-f3982700-dd2a-11e8-983c-77d1a3ed7f53.png" width="280" height="96" alt="iAdvize">

# Android Conversation SDK

Take your app to the next step and provide a **unique conversational experience** to your users!

Embed the iAdvize Conversation SDK in your app and connect your visitors with your professional agents or ibbü experts through a **fully customised chat experience**. Visitors can ask a question and will receive answers directly on their devices.

## Compatibility

| Version | Minimum Android Version | Kotlin Version |
| ------- | ----------------------- | -------------- |
| 2.1.0-beta1   | API 19                  | 1.5.10         |

## Table of contents
* [Setup](#setup)
	* [App creation](#creation)
	* [SDK dependencies](#dependencies)
	* [SDK Activation](#activation)
	* [GDPR](#gdpr)
	* [SDK cleanup](#cleanup)
	* [Logging](#logging)
* [Targeting](#targeting)
	* [Targeting Language](#language)
	* [Activate a targeting rule](#rule)
	* [Targeting rule availability](#availability)
	* [Follow user navigation](#navigation)
* [Conversation](#conversation)
	* [Ongoing conversation](#ongoing)
* [Push notifications](#notification)
	* [Configuration](#push-register)
	* [Reception](#push-receive)
* [Chatbox](#chatbox)
	* [Chat button](#button)
	* [Customization](#config)
		* [Main color](#color)
		* [Navigation bar](#navbar)
		* [Font](#font)
		* [Automatic message](#automaticmessage)
		* [GDPR message](#gdprmessage)
		* [Brand avatar](#avatar)	
* [Transaction](#transaction)
* [API reference](#api-reference)

<a name="setup"></a>
## Setup

<a name="creation"></a>
### App creation

1. Ask your iAdvize Admin to create a **Mobile App** on the administration website. *If you want to enable the iAdvize SDK push notifications for your user you have to provide your GCM API key when you create your app on the administration website.*

2. Ask your iAdvize Admin to create a new **Web & Mobile App** targeting campaign on the administration website and to give you the following information:
    - **projectId**: id of your project
    - **targetingRuleId(s)**: one or multiple rules which you will be able to activate by code during the user navigation (see #Targeting section)

<a name="dependencies"></a>
### SDK dependencies

**Step 1**. Add the JitPack maven repository to the list of repositories:

```gradle
// Project-level build.gradle

allprojects {
  repositories {
    google()
    maven { url "https://raw.github.com/iadvize/iadvize-android-sdk/master" }
    maven { url 'https://plugins.gradle.org/m2/' }
    maven { url 'https://jitpack.io' }
    mavenCentral()
  }
}
```

**Step 2**. Link your project with the iAdvize Conversation SDK dependency, add this line to your app's `build.gradle`:

```gradle
// Module-level build.gradle

configurations {
  all*.exclude group: 'xpp3', module: 'xpp3'
  debug
  release
}

dependencies {
  // If you use AndroidX you can use latest SDK
  implementation 'com.iadvize:iadvize-sdk:2.0.0-beta1'

  // Otherwise you can use the last non-AndroidX version
  implementation 'com.iadvize:iadvize-sdk:1.5.1'
}
```
> :warning: From version 1.6.0 the SDK uses AndroidX.

<a name="activation"></a>
### SDK Activation

#### Initiate

First of all, you have to initiate the iAdvize SDK by providing a reference to your current Android application:

```kotlin
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    IAdvizeSDK.initiate(this)
  }
}
```

#### Activate

To activate the SDK you can use the **activate** function. You also have access to a asynchronous callback in order to know if the SDK has been successfully activated (and to retry later if the activation fails):

```kotlin
IAdvizeSDK.activate(
  "your-project-id",
  AuthenticationOption,
  GDPROption,
  object : IAdvizeSDKCallback {
    override fun onSuccess() {
      // Activation succeded
    }
		
    override fun onFailure(t: Throwable) {
      // Activation failed
    }
  }
)
```

Once the iAdvize Conversation SDK is successfully activated, you should see a success message in the IDE console:

```
✅ iAdvize conversation activated, the version is x.x.x.
```

<a name="gdpr"></a>
### GDPR

By default, when you activate the SDK, the GDPR will be disabled. 

To enable it, you can pass a GDPR option while activating the SDK. This GDPROption dictates how the SDK behaves when the user taps on the “More information” button:

1. `GDPROptionEnabled.LegalUrl(URL)`: will open the given URL containing GDPR information
2. `GDPREnabledOption.Listener(GDPRListener)`: will call the given listener so that your app can show the relevant GDPR information

The GDPR process is now activated for your users and a default message will be provided to collect the user consent. Please check the [Customise](#customise) section below if you want to customise this message.

<a name="cleanup"></a>
### SDK Cleanup

When you first activate the SDK with a connected user, using the `AuthenticateOption.Simple` mode, you also have to logout the user from the iAdvize SDK when the user logs out from your app, in orderto preserve the confidentiality of his conversation:

```kotlin
IAdvizeSDK.logout()
```

<a name="logging"></a>
### Logging

By default, the SDK will **only log Warnings and Errors** in the Android Studio console. You can make it more verbose and choose between multiple levels of log for a better integration experience:

```kotlin
 IAdvizeSDK.logLevel = Logger.Level.VERBOSE
```

<a name="targeting"></a>
## Targeting

The targeting process is managed by the `IAdvizeSDK.targetingController`

<a name="language"></a>
### Targeting Language

By default, the SDK will use the device language for **targeting a conversation**. With this variable you can specify the language you want to use for targetting:

```kotlin
IAdvizeSDK.targetingController.language = SDKLanguageOption.Custom(Language.FR)
```

> :warning: This `language` property is NOT intended to change the language displayed in the SDK.

<a name="rule"></a>
### Activate a targeting rule

For the iAdvize SDK to work, you have to setup an active targeting rule. To do so, you can call the following method:

```kotlin
IAdvizeSDK.targetingController.activateTargetingRule(targetingRuleUUID)
```

<a name="availability"></a>
### Targeting rule availability

The targeting rule availability check will be triggered when you update the active targeting rule (see [Activate a targeting rule](#rule))

If you want to be informed of rule availability updates, you can add a listener:

```kotlin
IAdvizeSDK.targetingController.listeners.add(object : TargetingListener {
  override fun onActiveTargetingRuleAvailabilityChanged(isActiveTargetingRuleAvailable: Boolean) {
    // Active rule availability changed to $isActiveTargetingRuleAvailable
  }
})
```

<a name="navigation"></a>
### Follow user navigation

To allow iAdvize statitics to be processed you need to inform the SDK when the user navigates through your app. To do so, just call:

```kotlin
IAdvizeSDK.targetingController.registerUserNavigation()
```

<a name="conversation"></a>
## Conversation

The lifecycle of the conversation is managed by the `IAdvizeSDK.conversationController`

<a name="ongoing"></a>
### Ongoing conversation

To know and to observe the evolution of the conversation state, you will have access to a variable:

```kotlin
IAdvizeSDK.conversationController.hasOngoingConversation()
```

You can also add a listener to be informed in real time about conversation events:

```kotlin
IAdvizeSDK.conversationController.listeners.add(object : ConversationListener {
  override fun onOngoingConversationStatusChanged(hasOngoingConversation: Boolean) {
    // SDK ongoing conversation status changed to $hasOngoingConversation
  }

  override fun onNewMessageReceived(content: String) {
    // A new message was received via the SDK
  }

  override fun handleClickedUrl(uri: Uri): Boolean {
    // A message link was clicked, return true if you want your app to handle it
    return false
  }
})
```

If you are only interested in some of this listener methods, a default **NoOpConversationListener** is also available for code simplicity.

<a name="notification"></a>
## Notification

The entry point for notifications is the `IAdvizeSDK.notificationController`

SDK Messages are received internally when the SDK is activated, however your user may kill your app before a conversation has ended. In that case you will receive push notifications.

<a name="push-register"></a>
### Configuration

If you want to be informed of SDK messages received when your app is not running you should register the current **push token** of the device:

```kotlin
IAdvizeSDK.notificationController.registerPushToken(
  "push-token",
  object : IAdvizeSDKCallback {
    override fun onSuccess() {
      // Register succeded
    }
		
    override fun onFailure(t: Throwable) {
      // Register failed
    }
  }
)
```
You can register your push token at any time.

By default, push notifications are activated if you have setup the push notifications information for your app on the iAdvize administration website. You can manually enable/disable them at any time using:

```kotlin
IAdvizeSDK.notificationController.enablePushNotifications(object : IAdvizeSDKCallback {
  override fun onSuccess() {
    // Enable succeded
  }
	
  override fun onFailure(t: Throwable) {
    // Enable failed
  }
})

IAdvizeSDK.notificationController.disablePushNotifications(object : IAdvizeSDKCallback {
  override fun onSuccess() {
    // Disable succeded
  }

  override fun onFailure(t: Throwable) {
    // Disable failed
  }
})
```

<a name="push-receive"></a>
### Reception

Once you receive a push notification, you can easily verify that this notification concerns the SDK:

```kotlin
IAdvizeSDK.notificationController.isIAdvizePushNotification(remoteMessage.getData())
```
where `remoteMessage` is the object representing the push notification.

<a name="chatbox"></a>
## Chatbox

In order to invite your users to enter in a conversational experience, you will need to use the `IAdvizeSDK.chatboxController`.

<a name="button"></a>
### Chat button

When the active targeting rule is available, a chat button is displayed to invite the user to chat.

You can decide to let the SDK manage the chat button visibility or control it yourself using the following flag:

```kotlin
IAdvizeSDK.chatboxController.useDefaultChatButton = true
```

#### Default chat button
If `useDefaultChatButton = true` the SDK will use the iAdvize default chat button, manage its visibility, and open the chatbox when user presses it.

The default chat button is anchored to the bottom-left of your screen, you can change its position using:

```kotlin
IAdvizeSDK.chatboxController.setChatButtonPosition(leftMargin, bottomMargin)
```

#### Custom chat button
If `useDefaultChatButton = false` this default button will not show and it is your responsability to:

- design your own custom floating or fixed button to invite your user to chat
- hide/show your button following the active [Targeting rule availability](#availability)
- open the chatbox when the user presses your button, using the following method:

```kotlin
IAdvizeSDK.chatboxController.presentChatboxActivity(context)
```

<a name="config"></a>
### Customization

You can customize the chatbox UI by calling hte following method:

```kotlin
IAdvizeSDK.chatboxController.setupChatbox(chatboxConfiguration)
```

A simple snippet to only change one value:

```kotlin
IAdvizeSDK.chatboxController.apply {
  setupChatbox(configuration.apply {
    mainColor = Color.RED
  })
}
```

The ChatboxConfiguration allow you to customize the following attributes:

<a name="color"></a>
#### Main color

You can setup a main color on the SDK which will be applied to the color of:

- the default Chat button (if you use it)
- the send button in the Conversation View
- the blinking text cursor in the “new message” input in the Conversation View
- the background color of the message bubbles (only for sent messages)

```kotlin
configuration.mainColor = Color.RED
```

<a name="navbar"></a>
#### Navigation bar

You can configure the Toolbar of the Chatbox and modify:

- the background color
- the main color
- the title

```kotlin
configuration.toolbarBackgroundColor = Color.BLACK
configuration.toolbarMainColor = Color.WHITE
configuration.toolbarTitle = "Conversation"
```

<a name="font"></a>
#### Font

You can update the font used in the UI of the IAdvize Conversation SDK. You just have to call this method to setup your own font:

```kotlin
configuration.fontPath = "fonts/ProximaNova-Regular.otf"
```

The font file must be in the assets folder of your application. Here the file is in `assets/fonts/ProximaNova-Regular.otf`

<a name="automaticmessage"></a>
#### Automatic message

A first automatic message can be setup to be displayed as an operator message in the Chatbox. By default, no message will be displayed. This message will also be used and displayed when the user accepts the GDPR. You can set an automatic message through:

```kotlin
configuration.automaticMessage = "Hi, just ask a question and we will answer you asap!"
``` 

<a name="gdprmessage"></a>
#### GDPR message

If you want to activate the GDPR consent collect feature through the iAdvize Conversation SDK, please refer to the [GDPR section](#gdpr).

Once the GDPR is activated, you can easily customise the GDPR message you want to display to your users to collect their consent:

```kotlin
configuration.gdprMessage = "Your own GDPR message."
```     

<a name="avatar"></a>
#### Brand avatar

You can update the brand avatar displayed for the incoming messages. You can specify an URL or a Drawable. Gifs are not supported.

```kotlin
configuration.incomingMessageAvatar = IncomingMessageAvatar.Image(drawable)
configuration.incomingMessageAvatar = IncomingMessageAvatar.Url(url)
```

<a name="transaction"></a>
## Transaction

You can register a transaction within your application using the `IAdvizeSDK.transactionController`:

```kotlin
IAdvizeSDK.transactionController.register(
  Transaction(
    "transactionId",
    Date(),
    10.00,
    Currency.EUR
  )
)
```
<a name="api-reference"></a>
## API Reference
See API Reference [here](https://iadvize.github.io/iadvize-android-sdk/).

## And you’re done! 💪

Well done! You’re now ready to take your app to the next step and provide a unique conversational experience to your users! 🚀

