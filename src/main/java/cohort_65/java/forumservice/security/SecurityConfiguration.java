package cohort_65.java.forumservice.security;

import cohort_65.java.forumservice.accounting.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    final CustomWebSecurity webSecurity;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/register", "/forum/posts/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager(
                        "hasRole('ADMIN') or authentication.name == #login"))
                .requestMatchers("/user/{login}/role/{role}")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager(
                        "authentication.name == #login"))

                .requestMatchers(HttpMethod.POST, "/forum/post/{author}")
                .access(new WebExpressionAuthorizationManager(
                        "authentication.name == #author"))

                .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                .access((auth, context) -> {
                    boolean checkAuthor = webSecurity.isPostAuthor(auth.get().getName(), context.getVariables().get("id").toString());
                    boolean checkModer = context.getRequest().isUserInRole(Role.MODER.name());
                    return new AuthorizationDecision(checkAuthor || checkModer);
                })

                .requestMatchers(HttpMethod.PUT, "/forum/post/{id}")
                .access((auth, context) -> new AuthorizationDecision(webSecurity.isPostAuthor(auth.get().getName(), context.getVariables().get("id").toString())))

                .requestMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}")
                .access(new WebExpressionAuthorizationManager(
                        "authentication.name == #author"))
                .anyRequest().authenticated());
        return http.build();
    }
}
