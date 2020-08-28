package org.example.mongo.config;

import org.example.mongo.repos.UserSocialConnectionRepository;
import org.example.mongo.repos.impl.MongoUsersConnectionRepository;
import org.example.mongo.service.FacebookConnectionSignup;
import org.example.mongo.service.FacebookSignInAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.example.mongo"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${spring.social.facebook.appSecret}")
    String appSecret;

    @Value("${spring.social.facebook.appId}")
    String appId;

    @Autowired
    private UserSocialConnectionRepository userSocialConnectionRepository;

    @Autowired
    private FacebookConnectionSignup facebookConnectionSignup;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*","/signin/**","/signup/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout();
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ConnectionFactoryLocator connectionFactoryLocator = connectionFactoryLocator();
        UsersConnectionRepository usersConnectionRepository = getUsersConnectionRepository(connectionFactoryLocator);
        ((MongoUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new FacebookSignInAdapter());
    }

    private ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
        return registry;
    }

    private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator
                                                                           connectionFactoryLocator) {
        MongoUsersConnectionRepository repository = new MongoUsersConnectionRepository(userSocialConnectionRepository,
                socialAuthenticationServiceLocator(), Encryptors.noOpText());
        repository.setConnectionSignUp(facebookConnectionSignup);
        return repository;
    }

    @Bean
    public SocialAuthenticationServiceLocator socialAuthenticationServiceLocator() {
        SocialAuthenticationServiceRegistry registry = new SocialAuthenticationServiceRegistry();

        OAuth2ConnectionFactory<Facebook> facebookConnectionFactory = new FacebookConnectionFactory(appId,appSecret);
        OAuth2AuthenticationService<Facebook> facebookAuthenticationService = new OAuth2AuthenticationService<Facebook>(facebookConnectionFactory);
        facebookAuthenticationService.setDefaultScope("");
        registry.addAuthenticationService(facebookAuthenticationService);

        //add google
        /*OAuth2ConnectionFactory<Google> googleConnectionFactory = new GoogleConnectionFactory(environment.getProperty('google.clientId'),
                environment.getProperty('google.clientSecret'));
        OAuth2AuthenticationService<Google> googleAuthenticationService = new OAuth2AuthenticationService<Google>(googleConnectionFactory);
        googleAuthenticationService.setScope('https://www.googleapis.com/auth/userinfo.profile');
        registry.addAuthenticationService(googleAuthenticationService);

        //add twitter
        OAuth1ConnectionFactory<Twitter> twitterConnectionFactory = new TwitterConnectionFactory(environment.getProperty('twitter.consumerKey'),
                environment.getProperty('twitter.consumerSecret'));
        OAuth1AuthenticationService<Twitter> twitterAuthenticationService = new OAuth1AuthenticationService<Twitter>(twitterConnectionFactory);
        registry.addAuthenticationService(twitterAuthenticationService);*/

        //add facebook
        return registry;
    }
}

