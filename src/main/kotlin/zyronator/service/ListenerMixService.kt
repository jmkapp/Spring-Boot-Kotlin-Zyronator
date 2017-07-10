package zyronator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zyronator.domain.*

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

    fun update(id : Long, listenerMix : ListenerMix) : ListenerMix
    {
        val retrieved = _listenerMixRepository.findOne(id)

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
            val returnListenerMix = newListenerMix.copy()
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
        val allMixes = _mixService.findAll()
        val allListenerMixes = _listenerMixRepository.findByListener(listener)

        val nextMixSelector = NextMixSelector(allMixes, allListenerMixes)

        var nextMix : Mix

        if(currentListenerMix == null)
        {
            nextMix = nextMixSelector.selectMix()
        }
        else
        {
            nextMix = nextMixSelector.selectMix(currentListenerMix.mix)
        }

        var nextListenerMix : ListenerMix? = _listenerMixRepository.findByMixAndListener(nextMix, listener)

        if(nextListenerMix == null)
        {
            nextListenerMix = ListenerMix(id = null, mix = nextMix, listener = listener)
            nextListenerMix = _listenerMixRepository.saveAndFlush(nextListenerMix)
        }

        return nextListenerMix!!.copy()
    }
}
