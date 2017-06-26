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
        @Formula("(select m.title from mix m where m.id = mix_id)")
        val mixTitle: String,
        @Formula("(select m.discogs_api_url from mix m where m.id = mix_id)")
        val discogsApiUrl : String,
        @Formula("(select m.discogs_web_url from mix m where m.id = mix_id)")
        val discogsWebUrl: String,
        val lastListened: LocalDate,
        val comment: String = "") : ResourceSupport()

//@RepositoryRestResource(collectionResourceRel = "listenermixes", path = "listenermixes")
interface ListenerMixRepository : JpaRepository<ListenerMix, Long>
{
    fun findByListenerName(@Param("name") name: String) : List<ListenerMix>
    fun findTopByListenerOrderByLastListenedDesc(@Param("listener") listener: Listener) : ListenerMix
    fun findTopByListenerOrderByLastListenedAsc(@Param("listener") listener: Listener) : ListenerMix
    fun findByMixAndListener(@Param("mix") mixUrl : Mix, @Param("listener") listenerUrl : Listener) : ListenerMix

//    @Query("SELECT mix1, mix2 ")
//    fun testQuery(@Param("listener") listener: Listener) : List<ListenerMix>
}

