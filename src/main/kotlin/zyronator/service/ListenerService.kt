package zyronator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import zyronator.domain.*

@Service
class ListenerService
{
    @Autowired
    private lateinit var _listenerRepository : ListenerRepository

    @Autowired
    private lateinit var _listenerMixService : ListenerMixService

    fun save(listener : Listener) : Listener
    {
        val hashedPassword = BCryptPasswordEncoder().encode(listener.password)

        val newListener = listener.copy(password = hashedPassword)

        return _listenerRepository.saveAndFlush(newListener)
    }

    fun create(listener : Listener) : Listener
    {
        val hashedPassword = BCryptPasswordEncoder().encode(listener.password)

        val newListener = Listener(
                id = null,
                name = listener.name,
                password = hashedPassword,
                enabled = true)

        return _listenerRepository.saveAndFlush(newListener)
    }

    fun get(id : Long) : Listener
    {
        return _listenerRepository.findOne(id)
    }
}