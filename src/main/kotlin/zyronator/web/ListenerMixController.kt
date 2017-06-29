package zyronator.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import zyronator.domain.ListenerMix
import zyronator.service.ListenerMixService
import zyronator.service.ListenerService
import zyronator.web.display.ListenerMixDisplay
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/listenerMixes")
open class ListenerMixController
{
    @Autowired
    private lateinit var _listenerService : ListenerService

    @Autowired
    private lateinit var _listenerMixService : ListenerMixService

    @PostMapping
    fun createListenerMix(@RequestBody listenerMix : ListenerMix) : ResponseEntity<ListenerMix>
    {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as org.springframework.security.core.userdetails.User

        val listenerId : Long = listenerMix.listener.id!!.toLong()
        val listener = _listenerService.get(listenerId)

        if(listener.name == user.username)
        {
            val newListenerMix = _listenerMixService.findOrCreate(listenerMix)
            newListenerMix.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(newListenerMix.id).withSelfRel())
            newListenerMix.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(newListenerMix.id).withRel("listenerMix"))
            newListenerMix.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(newListenerMix.id).slash("listener").withRel("listener"))
            newListenerMix.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(newListenerMix.id).slash("mix").withRel("mix"))

            return ResponseEntity(newListenerMix, HttpStatus.OK)
        }
        else
        {
            return ResponseEntity(null, HttpStatus.FORBIDDEN)
        }
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id : String, @RequestBody listenerMix: ListenerMix) : ResponseEntity<ListenerMixDisplay>
    {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as org.springframework.security.core.userdetails.User

        val retrieved = _listenerMixService.get(id.toLong())

        if(retrieved.listener.name == user.username)
        {
            val newListenerMix = _listenerMixService.save(id, listenerMix)

            val listenerMixDisplay = getListenerMixDisplay(newListenerMix)

            return ResponseEntity(listenerMixDisplay, HttpStatus.OK)
        }
        else
        {
            return ResponseEntity(null, HttpStatus.FORBIDDEN)
        }
    }

    fun getListenerMixDisplay(listenerMix : ListenerMix?) : ListenerMixDisplay
    {
        if(listenerMix == null)
        {
            return ListenerMixDisplay()
        }
        else
        {
            val mix = listenerMix.mix

            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val recordDate = if(mix.recorded == null) "" else mix.recorded.format(formatter)
            val lastListenedDate = if(listenerMix.lastListenedDate == null) "" else listenerMix.lastListenedDate!!.format(formatter)

            val mixDisplay = ListenerMixDisplay(
                    mixTitle = mix.title,
                    recordedDate = recordDate,
                    comment = listenerMix.comment ?: "",
                    discogsApiUrl = mix.discogsApiUrl ?: "",
                    discogsWebUrl = mix.discogsWebUrl ?: "",
                    lastListenedDate = lastListenedDate)

            mixDisplay.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(listenerMix.id).withSelfRel())
            mixDisplay.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(listenerMix.id).withRel("listenerMix"))
            mixDisplay.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(listenerMix.id).slash("listener").withRel("listener"))
            mixDisplay.add(ControllerLinkBuilder.linkTo(ListenerMixController::class.java).slash(listenerMix.id).slash("mix").withRel("mix"))

            return mixDisplay
        }
    }
}
