package zyronator.web.display

import org.springframework.hateoas.ResourceSupport

data class ListenerMixDisplay(
        val mixTitle: String = "",
        val recordedDate: String = "",
        val comment : String = "",
        val discogsApiUrl : String = "",
        val discogsWebUrl : String = "",
        val lastListenedDate : String = "") : ResourceSupport()

