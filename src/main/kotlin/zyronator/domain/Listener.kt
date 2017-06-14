package zyronator.domain

import com.fasterxml.jackson.annotation.JsonCreator
import javax.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.hateoas.ResourceSupport
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Entity
data class Listener(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long?,
        @Column(nullable = false, unique = true)
        val name : String,
        @Column(nullable = false)
        val password : String,
        val enabled : Boolean = false) : ResourceSupport()

//@RepositoryRestResource(collectionResourceRel = "listeners", path = "listeners")
interface ListenerRepository : JpaRepository<Listener, Long>
{
    fun findByName(@Param("name") name: String) : Listener
}

@Component("beforeCreateListenerValidator")
class ListenerValidator : Validator
{
    override fun validate(target: Any, errors: Errors)
    {
        val user = target as Listener

        if (checkInputString(user.name)) {
            errors.rejectValue("name", "name.empty");
        }
    }

    override fun supports(clazz: Class<*>): Boolean
    {
        return Listener::class.java.equals(clazz)
    }

    private fun checkInputString(input: String?): Boolean
    {
        return input == null || input.trim { it <= ' ' }.length == 0
    }
}