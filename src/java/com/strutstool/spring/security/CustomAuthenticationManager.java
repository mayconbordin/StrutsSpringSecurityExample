package com.strutstool.spring.security;

import com.app.web.model.entity.User;
import com.app.web.model.repository.UserRepository;
import com.app.web.model.repository.UserRepositoryHibernate;
import com.strutstool.hash.SHA256;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * A custom authentication manager that allows access if the user details
 * exist in the database and if the username and password are not the same.
 * Otherwise, throw a {@link BadCredentialsException}
 */
public class CustomAuthenticationManager implements AuthenticationManager {
    protected static final Logger logger = Logger.getLogger(CustomAuthenticationManager.class);

    // Our custom DAO layer
    private UserRepository userRepository = new UserRepositoryHibernate();

    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        logger.debug("Performing custom authentication");

        // Init a database user object
        User user = null;

        try {
            // Retrieve user details from database
            user = userRepository.findByName(auth.getName());

            if (user == null) {
                logger.error("User does not exists!");
                throw new BadCredentialsException("User does not exists!");
            }
        } catch (Exception e) {
            logger.error("User does not exists!");
            throw new BadCredentialsException("User does not exists!");
        }

        // Compare passwords
        // Make sure to encode the password first before comparing
        String passw = SHA256.calculate((String) auth.getCredentials());
        if (!user.getPassword().equals(passw)) {
            logger.error("Wrong password!");
            throw new BadCredentialsException("Wrong password!");
        }

        // Here's the main logic of this custom authentication manager
        // Username and password must be the same to authenticate
        //if (auth.getName().equals(auth.getCredentials()) == true) {
        //    logger.debug("Entered username and password are the same!");
        //    throw new BadCredentialsException("Entered username and password are the same!");
        //} else {
            logger.debug("User details are good and ready to go");
            return new UsernamePasswordAuthenticationToken(
                auth.getName(),
                auth.getCredentials(),
                getAuthorities(user.getAuthority()));
        //}
    }

    /**
     * Retrieves the correct ROLE type depending on the access level, where access level is an Integer.
     * Basically, this interprets the access value whether it's for a regular user or admin.
     *
     * @param access an integer value representing the access of the user
     * @return collection of granted authorities
     */
    public Collection<GrantedAuthority> getAuthorities(Integer access) {
        // Create a list of grants for this user
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

        // All users are granted with ROLE_USER access
        // Therefore this user gets a ROLE_USER by default
        logger.debug("Grant ROLE_USER to this user");
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));

        // Check if this user has admin access
        // We interpret Integer(1) as an admin user
        if ( access.compareTo( User.ROLE_ADMIN ) == 0) {
            // User has admin access
            logger.debug("Grant ROLE_ADMIN to this user");
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }

        // Return list of granted authorities
        return authList;
    }
}