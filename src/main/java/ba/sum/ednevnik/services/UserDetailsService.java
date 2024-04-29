package ba.sum.ednevnik.services;

import ba.sum.ednevnik.models.User;
import ba.sum.ednevnik.models.UserDetails;
import ba.sum.ednevnik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repository.findByEmail(username);
        return new UserDetails(u);
    }
}
