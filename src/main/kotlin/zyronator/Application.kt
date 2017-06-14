package zyronator

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Validator
import java.util.Arrays
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

@SpringBootApplication
open class Application

fun main(args: Array<String>)
{
    SpringApplication.run(Application::class.java, *args)
}

//Required due to a bug in Spring
// see http://www.baeldung.com/spring-data-rest-validators
@Configuration
open class ValidationEventRegister : InitializingBean
{
    @Autowired
    var validatingRepositoryEventListener: ValidatingRepositoryEventListener? = null

    @Autowired
    private val validators: Map<String, Validator>? = null

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        val events = Arrays.asList("beforeCreate")

        for (entry : Map.Entry<String, Validator> in validators!!)
        {
            events.stream()
                    .filter { p -> entry.key.startsWith(p) }
                    .findFirst()
                    .ifPresent { p ->
                        validatingRepositoryEventListener!!
                                .addValidator(p, entry.value)
                    }
        }
    }

//    @Configuration
//    @EnableAuthorizationServer
//    @EnableResourceServer
//    open class ResourceOAuthSecurityConfiguration : ResourceServerConfigurerAdapter()
//    {
//        override fun configure(http : HttpSecurity)
//        {
//            http.authorizeRequests()
//                    .antMatchers("/").permitAll()
//                    .antMatchers("/listeners/**").authenticated()
//                    .antMatchers("/listenerMixes/**").authenticated()
//        }
//    }
}
