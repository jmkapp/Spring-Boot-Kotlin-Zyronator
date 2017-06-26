package zyronator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zyronator.domain.*

@Service
class ListenerMixService
{
    @Autowired
    private lateinit var _listenerMixRepository : ListenerMixRepository

    fun findEarliest(listener : Listener) : ListenerMix?
    {
        return _listenerMixRepository.findTopByListenerOrderByLastListenedAsc(listener)
    }

    fun findLatest(listener : Listener) : ListenerMix?
    {
        return _listenerMixRepository.findTopByListenerOrderByLastListenedDesc(listener)
    }
}
