package zyronator.web

import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zyronator.domain.ListenerMix
import zyronator.domain.Mix
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/mixes")
open class MixController
{
    fun getMixDisplay(listenerMix : ListenerMix?) : MixDisplay
    {
        if(listenerMix == null)
        {
            return MixDisplay()
        }
        else
        {
            val mix = listenerMix.mix

            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val recordDate = if(mix.recorded == null) "" else mix.recorded.format(formatter)

            val mixDisplay = MixDisplay(
                    title = mix.title,
                    recorded = recordDate,
                    comment = if(mix.comment == null) "" else mix.comment,
                    discogsApiUrl = if(mix.discogsApiUrl == null) "" else mix.discogsApiUrl,
                    discogsWebUrl = if (mix.discogsWebUrl == null) "" else mix.discogsWebUrl)

            mixDisplay.add(ControllerLinkBuilder.linkTo(MixController::class.java, mix.id).slash("/" + mix.id).withSelfRel())

            return mixDisplay
        }
    }
}
