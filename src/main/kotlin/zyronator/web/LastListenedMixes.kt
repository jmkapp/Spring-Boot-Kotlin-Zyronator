package zyronator.web

import org.springframework.hateoas.ResourceSupport
import zyronator.service.ListenerMixDisplay

data class LastListenedMixes(
        val currentListenerMix: ListenerMixDisplay,
        val nextListenerMix: ListenerMixDisplay
        ) : ResourceSupport()
