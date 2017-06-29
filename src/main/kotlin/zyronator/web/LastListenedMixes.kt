package zyronator.web

import org.springframework.hateoas.ResourceSupport
import zyronator.web.display.ListenerMixDisplay

data class LastListenedMixes(
        val currentListenerMix: ListenerMixDisplay,
        val nextListenerMix: ListenerMixDisplay
        ) : ResourceSupport()
