package zyronator.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import zyronator.domain.Listener
import zyronator.service.ListenerService
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

@RestController
@RequestMapping("/listeners")
open class ListenerController()
{
    @Autowired
    private lateinit var _listenerService : ListenerService

    @Autowired
    private lateinit var _listenerMixController: ListenerMixController

    @PatchMapping("/{id}")
    fun updateListenerPassword(@PathVariable id : String, @RequestBody listener : Listener) : ResponseEntity<Listener>
    {
        val existingListener : Listener? = _listenerService.get(id.toLong())

        if(existingListener != null && !listener.password.isNullOrEmpty())
        {
            _listenerService.save(listener)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @PostMapping
    fun createListener(@RequestBody listener : Listener) : ResponseEntity<ListenerDisplay>
    {
        if(!listener.name.isNullOrEmpty() && !listener.password.isNullOrEmpty())
        {
            val newListener = _listenerService.create(listener)
            val listenerDisplay = ListenerDisplay(newListener.name)
            return ResponseEntity.status(HttpStatus.OK).body(listenerDisplay)
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @GetMapping("/{id}")
    fun getListener(@PathVariable id : String): ResponseEntity<ListenerDisplay>
    {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as org.springframework.security.core.userdetails.User

        val listener = _listenerService.get(id.toLong())

        if(user.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")) || listener.name == user.username)
        {
            val listenerDisplay = ListenerDisplay(listener.name)
            listenerDisplay.add(linkTo(ListenerController::class.java).slash(listener.id).withSelfRel())
            listenerDisplay.add(linkTo(ListenerController::class.java).slash(listener.id).withRel("listener"))
            listenerDisplay.add(linkTo(ListenerController::class.java).slash(listener.id).slash("lastListened").withRel("lastListened"))
            return ResponseEntity<ListenerDisplay>(listenerDisplay, HttpStatus.OK)
        }
        else
        {
            return ResponseEntity<ListenerDisplay>(null, HttpStatus.FORBIDDEN)
        }
    }

    @GetMapping("/search/findByName")
    fun findByName(@RequestParam("name") name : String) : ResponseEntity<ListenerDisplay>
    {
        val listener = _listenerService.findByName(name)

        val listenerDisplay = ListenerDisplay(name)

        listenerDisplay.add(linkTo(ListenerController::class.java).slash(listener.id).withSelfRel())
        listenerDisplay.add(linkTo(ListenerController::class.java).slash(listener.id).withRel("listener"))
        listenerDisplay.add(linkTo(ListenerController::class.java).slash(listener.id).slash("lastListened").withRel("lastListened"))
        return ResponseEntity<ListenerDisplay>(listenerDisplay, HttpStatus.OK)
    }

    @GetMapping("/{id}/lastListened")
    fun findLastListenedMixes(@PathVariable id : String) : ResponseEntity<LastListenedMixes>
    {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as org.springframework.security.core.userdetails.User

        val listener = _listenerService.get(id.toLong())

        if(listener.name == user.username)
        {
            val lastListenedMixes = _listenerMixController.getLastListened(listener)
            lastListenedMixes.add(linkTo(ListenerController::class.java).slash(id).slash("/lastListened").withSelfRel())
            lastListenedMixes.add(linkTo(ListenerController::class.java).slash(id).slash("/lastListened").withRel("lastListened"))

            return ResponseEntity(lastListenedMixes, HttpStatus.OK)
        }
        else
        {
            return ResponseEntity(null, HttpStatus.FORBIDDEN)
        }
    }
}
