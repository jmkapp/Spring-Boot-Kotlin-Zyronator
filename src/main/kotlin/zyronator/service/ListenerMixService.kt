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

    fun getLastListened(listener: Listener) : LastListenedMixes
    {
        var currentListenerMix : ListenerMix? = _listenerMixRepository.findTopByListenerOrderByLastListenedDateAsc(listener)
        var nextListenerMix : ListenerMix? = _listenerMixRepository.findTopByListenerOrderByLastListenedDateDesc(listener)

        if(currentListenerMix == null)
        {
            currentListenerMix = findLatestGenerateRandom(listener)
        }

        if(nextListenerMix == null) {
            do
            {
                nextListenerMix = findEarliestGenerateRandom(listener = listener, exclude = currentListenerMix)
            } while (currentListenerMix.mix.id == nextListenerMix?.mix?.id)
        }

        val currentListenerMixDisplay = getListenerMixDisplay(currentListenerMix)
        val nextListenerMixDisplay = getListenerMixDisplay(nextListenerMix)

        return LastListenedMixes(currentListenerMix = currentListenerMixDisplay, nextListenerMix = nextListenerMixDisplay)
    }

    private fun findEarliestGenerateRandom(listener : Listener, exclude : ListenerMix) : ListenerMix
    {
        val listenerMix : ListenerMix? = _listenerMixRepository.findTopByListenerOrderByLastListenedDateAsc(listener)

        if(listenerMix == null || listenerMix.id == exclude.id)
        {
            return createRandom(listener)
        }
        else
        {
            return listenerMix
        }
    }

    private fun findLatestGenerateRandom(listener : Listener) : ListenerMix
    {
        val listenerMix : ListenerMix? = _listenerMixRepository.findTopByListenerOrderByLastListenedDateDesc(listener)

        if(listenerMix == null)
        {
            return createRandom(listener)
        }
        else
        {
            return listenerMix
        }
    }

    private fun createRandom(listener : Listener) : ListenerMix
    {
        val mix = _mixService.random()

        val retrievedListenerMix : ListenerMix? = _listenerMixRepository.findByMixAndListener(mix, listener)

        if(retrievedListenerMix == null)
        {
            val newListenerMix = ListenerMix(null, listener, mix)

            val savedListenerMix = _listenerMixRepository.saveAndFlush(newListenerMix)

            return savedListenerMix.copy()
        }
        else
        {
            return retrievedListenerMix
        }
    }

    private fun getListenerMixDisplay(listenerMix : ListenerMix?) : ListenerMixDisplay
    {
        if(listenerMix == null)
        {
            return ListenerMixDisplay()
        }
        else {
            val mix = listenerMix.mix

            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val recordDate = if (mix.recorded == null) "" else mix.recorded.format(formatter)
            val lastListenedDate = if (listenerMix.lastListenedDate == null) "" else listenerMix.lastListenedDate!!.format(formatter)

            val mixDisplay = ListenerMixDisplay(
                    mixTitle = mix.title,
                    recordedDate = recordDate,
                    comment = listenerMix.comment ?: "",
                    discogsApiUrl = mix.discogsApiUrl ?: "",
                    discogsWebUrl = mix.discogsWebUrl ?: "",
                    lastListenedDate = lastListenedDate)

            return mixDisplay
        }
    }
}
