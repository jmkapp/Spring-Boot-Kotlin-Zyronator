package zyronator.web

import org.springframework.hateoas.ResourceSupport

data class MixDisplay(
        val title : String = "",
        val recorded : String = "",
        val comment : String = "",
        val discogsApiUrl : String = "",
        val discogsWebUrl : String = "") : ResourceSupport()

