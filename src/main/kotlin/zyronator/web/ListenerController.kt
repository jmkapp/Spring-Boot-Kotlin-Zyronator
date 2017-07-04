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
import zyronator.service.ListenerMixService

@RestController
@RequestMapping("/listeners")
open class ListenerController()
{
    @Autowired
    private lateinit var _listenerService : ListenerService

    @Autowired
    private lateinit var _listenerMixService : ListenerMixService

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
    fun createListener(@RequestBody listener : Listener) : ResponseEntity<Listener>
    {
        if(!listener.name.isNullOrEmpty() && !listener.password.isNullOrEmpty())
        {
            val newListener = _listenerService.create(listener)
            return ResponseEntity.status(HttpStatus.OK).body(newListener)
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @GetMapping("/{id}")
    fun getListener(@PathVariable id : String): ResponseEntity<Listener>
    {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as org.springframework.security.core.userdetails.User

        val listener = _listenerService.get(id.toLong())

        if(user.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")) || listener.name == user.username)
        {
            listener.add(linkTo(ListenerController::class.java, id).slash("/" + id).withSelfRel())
            return ResponseEntity<Listener>(listener, HttpStatus.OK)
        }
        else
        {
            return ResponseEntity<Listener>(null, HttpStatus.FORBIDDEN)
        }
    }

    @GetMapping("/{id}/lastListened")
    fun findLastListenedMixes(@PathVariable id : String) : ResponseEntity<LastListenedMixes>
    {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as org.springframework.security.core.userdetails.User

        val listener = _listenerService.get(id.toLong())

        if(listener.name == user.username)
        {
            val lastListenedMixes = _listenerMixService.getLastListened(listener)
            lastListenedMixes.add(linkTo(ListenerController::class.java).slash(id).slash("/lastListened").withSelfRel())

            return ResponseEntity(lastListenedMixes, HttpStatus.OK)
        }
        else
        {
            return ResponseEntity(null, HttpStatus.FORBIDDEN)
        }
    }
}
