package zyronator.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.sql.ResultSet

//@Configuration
//@EnableGlobalAuthentication
//class JdbcSecurityConfiguration : GlobalAuthenticationConfigurerAdapter() {
//    @Bean
//    fun userDetailsService(jdbcTemplate: JdbcTemplate): UserDetailsService {
//        val userRowMapper = { rs: ResultSet, i: Int ->
//            User(
//                    rs.getString("account_name"),
//                    rs.getString("password"),
//                    rs.getBoolean("enabled"),
//                    rs.getBoolean("enabled"),
//                    rs.getBoolean("enabled"),
//                    rs.getBoolean("enabled"),
//                    AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"))
//        }
//        return UserDetailsService{ username ->
//            jdbcTemplate.queryForObject("SELECT * FROM account WHERE account_name = ?",
//                    userRowMapper)
//        }
//    }
//
//    @Autowired
//    private val userDetailsService: UserDetailsService? = null
//
//    @Throws(Exception::class)
//    override fun init(auth: AuthenticationManagerBuilder?) {
//        auth!!.userDetailsService<UserDetailsService>(this.userDetailsService)
//    }
//}

//}


//class JezzerRowMapper : RowMapper<User>
//{
//    override fun mapRow(rs: ResultSet, rowNum: Int): User
//    {
//        val user = User(
//                rs.getString("ACCOUNT_NAME"),
//                rs.getString("PASSWORD"),
//                rs.getBoolean("ENABLED"),
//                rs.getBoolean("ENABLED"),
//                rs.getBoolean("ENABLED"),
//                rs.getBoolean("ENABLED"),
//                AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"))
//
//        return user
//    }
//}

//class JezzerUserDetailsService : UserDetailsService
//{
//    override fun loadUserByUsername(username: String): UserDetails
//    {
//
//    }
//
//}
