package com.iadvize.conversation.sdk.demo.feature

import android.net.Uri
import com.iadvize.conversation.sdk.IAdvizeSDK
import com.iadvize.conversation.sdk.demo.utility.send
import com.iadvize.conversation.sdk.feature.conversation.ConversationListener
import com.iadvize.conversation.sdk.feature.conversation.OngoingConversation
import com.iadvize.conversation.sdk.feature.targeting.TargetingListener

class ConversationFlowManager : TargetingListener, ConversationListener {
    private var visible = false
        set(value) {
            val update = field != value
            field = value
            if (update) notifyUpdate()
        }

    override fun onOngoingConversationUpdated(ongoingConversation: OngoingConversation?) {
        refreshButtonState()
    }

    override fun onActiveTargetingRuleAvailabilityUpdated(isActiveTargetingRuleAvailable: Boolean) {
        refreshButtonState()
    }

    override fun onNewMessageReceived(content: String) {
        // no-op
    }

    // Use default link behavior (open default browser)
    override fun handleClickedUrl(uri: Uri): Boolean = false

    private fun refreshButtonState() {
        val sdkActivated = IAdvizeSDK.activationStatus == IAdvizeSDK.ActivationStatus.ACTIVATED
        val chatboxOpened = IAdvizeSDK.chatboxController.isChatboxPresented()
        val ruleAvailable = IAdvizeSDK.targetingController.isActiveTargetingRuleAvailable()
        val hasOngoingConv = IAdvizeSDK.conversationController.ongoingConversation() != null
        visible = sdkActivated && !chatboxOpened && (hasOngoingConv || ruleAvailable)
    }

    private fun notifyUpdate() {
        if (visible) send(ShowButton()) else send(HideButton())
    }
}

class ShowButton
class HideButton