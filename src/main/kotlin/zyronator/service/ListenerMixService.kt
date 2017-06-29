package zyronator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zyronator.domain.*

@Service
class ListenerMixService
{
    @Autowired
    private lateinit var _listenerMixRepository : ListenerMixRepository

    fun get(id : Long) : ListenerMix
    {
        return _listenerMixRepository.getOne(id)
    }

    fun save(id : String, listenerMix : ListenerMix) : ListenerMix
    {
        val retrieved = _listenerMixRepository.findOne(id.toLong())

        retrieved.comment = listenerMix.comment
        retrieved.lastListenedDate = listenerMix.lastListenedDate

        _listenerMixRepository.saveAndFlush(retrieved)

        val copyListenerMix = retrieved.copy()

        return copyListenerMix
    }

    fun findOrCreate(listenerMix : ListenerMix) : ListenerMix
    {
        val retrievedListenerMix : ListenerMix? = _listenerMixRepository.findByMixAndListener(listenerMix.mix, listenerMix.listener)

        if(retrievedListenerMix == null)
        {
            val newListenerMix = _listenerMixRepository.saveAndFlush(listenerMix)
            val returnListenerMix = newListenerMix.copy(
                    mixTitle = newListenerMix.mix.title,
                    discogsApiUrl = if (newListenerMix.mix.discogsApiUrl == null) "" else newListenerMix.mix.discogsApiUrl,
                    discogsWebUrl = if (newListenerMix.mix.discogsWebUrl == null ) "" else newListenerMix.mix.discogsWebUrl)
            return returnListenerMix
        }
        else
        {
            return retrievedListenerMix
        }
    }

    fun findEarliest(listener : Listener) : ListenerMix?
    {
        return _listenerMixRepository.findTopByListenerOrderByLastListenedDateAsc(listener)
    }

    fun findLatest(listener : Listener) : ListenerMix?
    {
        return _listenerMixRepository.findTopByListenerOrderByLastListenedDateDesc(listener)
    }
}
