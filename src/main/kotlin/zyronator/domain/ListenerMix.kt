package zyronator.domain

import org.hibernate.annotations.Formula
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.hateoas.ResourceSupport
import java.time.LocalDate
import javax.persistence.*

@Entity
data class ListenerMix(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long?,
        @ManyToOne
        val listener: Listener,
        @ManyToOne
        val mix: Mix,
        var lastListenedDate: LocalDate? = null,
        var comment: String? = "")

//@RepositoryRestResource(collectionResourceRel = "listenermixes", path = "listenermixes")
interface ListenerMixRepository : JpaRepository<ListenerMix, Long>
{
    fun findByListener(@Param("listener") listener : Listener) : List<ListenerMix>
    fun findByListenerName(@Param("name") name: String) : List<ListenerMix>
    fun findTopByListenerOrderByLastListenedDateDesc(@Param("listener") listener: Listener) : ListenerMix
    fun findTopByListenerOrderByLastListenedDateAsc(@Param("listener") listener: Listener) : ListenerMix
    fun findByMixAndListener(@Param("mix") mix : Mix, @Param("listener") listener : Listener) : ListenerMix
}

