package mx.edu.ebc.comisiones.services

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import mx.edu.ebc.comisiones.pojos.EbcUser

class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      EbcUser user = new EbcUser()
        if (user == null) {
            throw new UsernameNotFoundException(
              String.format("Username not found for domain, username=%s, domain=%s"));
        }
        return user;
    }
}