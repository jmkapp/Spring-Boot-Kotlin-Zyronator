package zyronator.service

import org.springframework.hateoas.ResourceSupport

data class ListenerMixDisplay(
        val mixTitle: String = "",
        val recordedDate: String = "",
        val mixComment: String = "",
        val discogsApiUrl : String = "",
        val discogsWebUrl : String = "",
        val lastListenedDate : String = "",
        val listenerMixComment : String = "") : ResourceSupport()

