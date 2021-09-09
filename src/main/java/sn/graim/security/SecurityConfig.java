package sn.graim.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select username as principal, password as credentials, active from utilisateur where username=?")
        .authoritiesByUsernameQuery("select utilisateur_username as principal, roles_nom_role as role from users_roles where utilisateur_username=?")
        .rolePrefix("ROLE_")
        .passwordEncoder(passwordEncoder());
    }
			
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/css/**","/js/**","/img/**","/plugins/**").permitAll()
		.antMatchers("/index","/form","/SaveUtilisateur","/getLogo","/entreprises","/candidats","/listeEmplois","/detailPoste","/reinitialisation","/listeCategorie","/profilParCandidat").permitAll()
		.antMatchers("/formRecruteur","/SaveRecruteur","/formEmploi","/SaveEmploi").hasRole("RECRUTEUR")
		.antMatchers("/postuler", "/dashboard","/saveComplement","/profil").hasRole("CANDIDAT")
		.antMatchers("/listeSecteurs","/formSecteur","/saveSecteur","/editSecteur","/updateSecteur","/deleteSecteur","/formMetier","/saveMetier","/editMetier","/updateMetier","/deleteMetier","/listeMetiers","/listeRegions","/formRegion","/saveRegion","/editRegion","/updateRegion","/deleteRegion","/listeRoles","/formRole","/saveRole","/editRole","/updateRole","/deleteRole").hasRole("ADMIN");
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().and().formLogin()
			.loginProcessingUrl("/j_spring_security_check")
			.loginPage("/connexion")
			.defaultSuccessUrl("/index")
			.failureUrl("/connexion?error=true")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
		
		//Config Remember me
		http.authorizeRequests().and()
		.rememberMe().tokenRepository(this.persistentTokenRepository())
		.tokenValiditySeconds(1*24*60*60);
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(this.dataSource);
		return db;
	}
}
