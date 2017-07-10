package zyronator.web

import org.springframework.hateoas.ResourceSupport

data class ListenerDisplay(
        val name : String) : ResourceSupport()
