package zyronator.service

import zyronator.domain.ListenerMix
import zyronator.domain.Mix
import java.util.concurrent.ThreadLocalRandom

internal class NextMixSelector(
        private val _allMixes : List<Mix>,
        allListenerMixes : List<ListenerMix>)
{
    private val _allListenerMixes : List<ListenerMix>
    private val _unMatchedMixes : List<Mix>
    val unMatchedExists : Boolean

    init
    {
        _allListenerMixes = allListenerMixes.sortedBy { item -> item.lastListenedDate }

        val temporaryUnmatchedMixes = _allMixes.toMutableList()

        for(listenerMix in _allListenerMixes)
        {
            for(mix in _allMixes)
            {
                if(listenerMix.mix.id == mix.id)
                {
                    temporaryUnmatchedMixes.remove(mix)
                }
            }
        }

        _unMatchedMixes = temporaryUnmatchedMixes.toList()
        unMatchedExists = _unMatchedMixes.isNotEmpty()
    }

    internal fun selectMix() : Mix
    {
        if(unMatchedExists)
        {
            val randomNumber = ThreadLocalRandom.current().nextInt(0, _unMatchedMixes.count())
            return _unMatchedMixes.get(randomNumber)
        }
        else
        {
            val listenerMix = _allListenerMixes.get(0)
            return listenerMix.mix
        }
    }

    internal fun selectMix(excludeMix : Mix) : Mix
    {
        var mix : Mix

        do
        {
            mix = selectMix()
        }
            while(excludeMix.id == mix.id)

        return mix
    }
}
