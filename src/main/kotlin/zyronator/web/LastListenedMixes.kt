package zyronator.web

import org.springframework.hateoas.ResourceSupport

data class LastListenedMixes(
        val currentListenerMix: ListenerMixDisplay,
        val nextListenerMix: ListenerMixDisplay
        ) : ResourceSupport()
