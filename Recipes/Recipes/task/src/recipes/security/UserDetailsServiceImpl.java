package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.gateways.UserGateway;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserGateway gateway;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = gateway.findByEmail(username);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UsernameNotFoundException("User '" + username + "' not found!");
  }
}
