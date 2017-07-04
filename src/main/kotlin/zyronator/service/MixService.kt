package zyronator.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import zyronator.domain.Mix
import zyronator.domain.MixRepository

@Service
class MixService
{
    @Autowired
    private lateinit var _mixRepository : MixRepository

    fun random() : Mix
    {
        val quantity = _mixRepository.count()
        val index = (Math.random() * quantity)

        val mixPage = _mixRepository.findAll(PageRequest(index.toInt(), 1))

        return mixPage.content.get(0)
    }
}