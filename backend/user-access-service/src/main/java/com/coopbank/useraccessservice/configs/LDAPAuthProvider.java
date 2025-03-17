package com.coopbank.useraccessservice.configs;


import com.coopbank.useraccessservice.dtos.LdapAuthResponseDTO;
import com.coopbank.useraccessservice.dtos.LdapObjectDTO;
import com.coopbank.useraccessservice.exceptions.LdapException;
import com.coopbank.useraccessservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.AuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import java.util.Hashtable;


@Component
@Log4j2
@RequiredArgsConstructor
public class LDAPAuthProvider implements AuthenticationProvider{

    public static final String INVALID_LOGIN_DETAILS = "Invalid login details";

    @Value("${ldap.server.host}")
    private String ldapUrl;

    @Value("${ldap.server.port}")
    private int ldapPort;

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LDAPAuthProvider.class);

    public String getTrimEmail(String email) {
        try {
            int index = email.indexOf('@');
            return email.substring(0, index);
        } catch (StringIndexOutOfBoundsException e) {
            return email;
        }
    }

    //Authenticate user based on LDAP
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        if (StringUtils.isEmpty(username)) {
            throw new BadCredentialsException(INVALID_LOGIN_DETAILS);
        }
        try {
            String password = authentication.getCredentials().toString();
            final LdapAuthResponseDTO ldapAuthenticated = isLdapAuthenticated(username, password);
            if (ldapAuthenticated.isAuthenticated()) {
                LdapObjectDTO ldapObject = ldapAuthenticated.getLdapObject();
                System.out.print("****==========LDAP LOGIN SUCCESS : " + ldapObject.getFirstName() + " " + ldapObject.getDepartment() + "===========****");

                //Check if user exists
                userService.findByUsername(username);

                UserDetails userDetails = User.builder()
                        .username(username)
                        .password("") // password is not stored
                        .authorities(new SimpleGrantedAuthority("USER"))
                        .build();
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            } else {
                throw new BadCredentialsException(INVALID_LOGIN_DETAILS);
            }
        } catch (UsernameNotFoundException | AuthenticationException ex) {
            throw new BadCredentialsException(INVALID_LOGIN_DETAILS, ex);
        }
    }

    protected static LdapObjectDTO searchUserDetails(String searchFilter, DirContext ctx) throws NamingException {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        sc.setTimeLimit(0);
        sc.setCountLimit(1);
        sc.setReturningAttributes(new String[]{"*"});
        NamingEnumeration<SearchResult> ne = ctx.search("DC=CO-OPBANK,DC=CO,DC=KE", searchFilter, sc);
        String firstName;
        String lastName;
        String emailAddress;
        String department;
        SearchResult tmp = ne.next();
        Attributes at = tmp.getAttributes();
        firstName = at.get("givenName").toString();
        lastName = at.get("sn").toString();
        emailAddress = at.get("mail").toString();
        department = at.get("department") != null ? at.get("department").toString() : "";
        return new LdapObjectDTO(firstName.replace("givenName:", "").trim(),
                lastName.replace("sn:", "").trim(),
                emailAddress.replace("mail:", "").trim().toLowerCase(),
                department.replace("department:", "").trim());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    private LdapAuthResponseDTO isLdapAuthenticated(String email, String password) {
        final LdapObjectDTO ldapObject;
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://" + ldapUrl + ":" + ldapPort);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "co-opbank\\" + getTrimEmail(email));
            env.put(Context.SECURITY_CREDENTIALS, password);
            env.put(Context.REFERRAL, "throw");
            // Create the initial context
            LdapContext ctx = new InitialLdapContext(env, null);
            String dn = "@co-opbank.co.ke";
            ldapObject = searchUserDetails("(&((mail=" + getTrimEmail(email) + dn + ")))", ctx);
            ctx.close();
            return new LdapAuthResponseDTO(true, ldapObject);
        } catch (Exception  e) {
            if (e.getLocalizedMessage().contains("[LDAP: error code 49 - 80090308")) {
                logger.error("Just an invalid password");
                throw new BadCredentialsException("Invalid Password");
            }
            throw new LdapException(e.getLocalizedMessage(), e.getCause());
        }
    }
}
