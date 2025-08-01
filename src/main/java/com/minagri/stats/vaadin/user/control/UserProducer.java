package com.minagri.stats.vaadin.user.control;

import com.minagri.stats.vaadin.user.entity.User;
import io.quarkus.oidc.IdToken;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.eclipse.microprofile.jwt.Claims.*;


@Slf4j
@Dependent
public class UserProducer {
    @Inject
    @IdToken
    JsonWebToken idToken;


    @Inject
    SecurityIdentity securityIdentity;

    @ConfigProperty(name = "security.fid.override")
    Optional<String> fidOverride;

    private final static String COGNITO_PERSONNEL_NUMBER_CLAIM = "custom:personnel_id";

    @Produces
    public User user() {
        User user = new User();
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setName(getUserName());
        user.setEmail(getEmail());
        user.setRoles(securityIdentity.getRoles());

        return user;
    }


    private String getFirstName(){
        return idToken.getClaim(given_name);
    }

    private String getLastName(){
        return idToken.getClaim(family_name);
    }


    private String getUserName() {
        String name = idToken.getClaim("name");
        String givenName = idToken.getClaim("given_name");
        String familyName = idToken.getClaim("family_name");

        String firstname = (givenName == null || givenName.isBlank()) ? name : givenName;

        if (firstname == null || firstname.isBlank()) {
            return familyName;
        }
        if (familyName == null || familyName.isBlank()) {
            return firstname;
        }
        return firstname + " " + familyName;
    }

    private String getEmail(){
        return idToken.getClaim(email);
    }


    @SuppressWarnings("unchecked")
    static String parseUserId(Object preferredUsername) {
        String userId = switch (preferredUsername) {
            case null -> null;
            case Collection<?> collection -> ((List<String>) collection).getFirst();
            case String user -> user;
            default -> throw new IllegalArgumentException("unable to parse preferred username: " + preferredUsername);
        };

        if (userId == null) {
            return null;
        }
        if (userId.chars().filter(ch -> ch == '\\').count() == 1) {
            return userId.split("\\\\")[1];
        }
        return userId;
    }
}
