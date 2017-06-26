package zyronator.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.time.LocalDate
import javax.persistence.*

@Entity
data class Mix(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id : Long,
        @Column(nullable = false)
        val title : String,
        val recorded: LocalDate,
        val comment: String,
        val discogsApiUrl: String,
        val discogsWebUrl: String)

//@RepositoryRestResource(collectionResourceRel = "mixes", path = "mixes")
interface MixRepository : JpaRepository<Mix, Long>
{
    fun findByTitleIgnoreCase(@Param("title") title: String) : Mix
    fun findByTitleContainingIgnoreCase(@Param("text") text: String) : List<Mix>
    fun findByTitleAndDiscogsApiUrlAndDiscogsWebUrl(
            @Param("title") title : String,
            @Param("discogsApiUrl") discogsApiUrl: String,
            @Param("discogsWebUrl") discogsWebUrl: String) : Mix
    fun findTopByOrderByRecordedAsc() : Mix
    fun findTopByOrderByRecordedDesc() : Mix
}

@Component("beforeCreateMixValidator")
class MixValidator : Validator
{
    override fun validate(target: Any, errors: Errors)
    {
        val mix = target as Mix

        if (checkInputString(mix.title)) {
            errors.rejectValue("title", "title.empty");
        }
    }

    override fun supports(clazz: Class<*>): Boolean
    {
        return Mix::class.java.equals(clazz)
    }

    private fun checkInputString(input: String?): Boolean
    {
        return input == null || input.trim { it <= ' ' }.length == 0
    }
}