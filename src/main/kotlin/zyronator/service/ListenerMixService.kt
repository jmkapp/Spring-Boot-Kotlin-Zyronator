package zyronator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zyronator.domain.*
import zyronator.web.LastListenedMixes
import java.time.format.DateTimeFormatter

@Service
class ListenerMixService
{
    @Autowired
    private lateinit var _listenerMixRepository : ListenerMixRepository

    @Autowired
    private lateinit var _mixService : MixService

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
                    discogsApiUrl = newListenerMix.mix.discogsApiUrl ?: "",
                    discogsWebUrl = newListenerMix.mix.discogsWebUrl ?: "")
            return returnListenerMix
        }
        else
        {
            return retrievedListenerMix
        }
    }

    fun getCurrentMix(listener : Listener) : ListenerMix?
    {
        val currentListenerMix : ListenerMix? = _listenerMixRepository.findTopByListenerOrderByLastListenedDateDesc(listener)

        if(currentListenerMix?.lastListenedDate == null)
        {
            return null
        }

        return currentListenerMix.copy()
    }

    fun getNextMix(listener : Listener, currentListenerMix: ListenerMix?) : ListenerMix
    {
        var nextListenerMix : ListenerMix? = _listenerMixRepository.findTopByListenerOrderByLastListenedDateAsc(listener)

        if(nextListenerMix == null || nextListenerMix.id == currentListenerMix?.id)
        {
            do
            {
                nextListenerMix = createRandom(listener)
            } while (currentListenerMix?.mix?.id == nextListenerMix?.mix?.id)
        }

        if(nextListenerMix!!.id == null)
        {
            nextListenerMix = _listenerMixRepository.saveAndFlush(nextListenerMix)
        }

        return nextListenerMix!!.copy()
    }

    private fun createRandom(listener : Listener) : ListenerMix
    {
        val mix = _mixService.random()

        val retrievedListenerMix : ListenerMix? = _listenerMixRepository.findByMixAndListener(mix, listener)

        if(retrievedListenerMix == null)
        {
            return ListenerMix(null, listener, mix)
        }
        else
        {
            return retrievedListenerMix
        }
    }
}
