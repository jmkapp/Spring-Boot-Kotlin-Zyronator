package zyronator.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.UserDetailsService


@Configuration
@EnableGlobalAuthentication
open class ResourceSecurityConfiguration : WebSecurityConfigurerAdapter()
{
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity)
    {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/mixes/**").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic()
    }

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.
                userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder
    {
        return BCryptPasswordEncoder()
    }
}
