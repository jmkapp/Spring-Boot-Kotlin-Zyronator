package zyronator.web

import org.springframework.hateoas.ResourceSupport

data class LastListenedMixes(
        val currentMix : MixDisplay,
        val nextMix : MixDisplay
        ) : ResourceSupport()
