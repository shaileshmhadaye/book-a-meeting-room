package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Person person = personRepository.findByUsername(username);
        if (person == null) throw new UsernameNotFoundException(username);
        //Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role: person.getRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getDescription()));
//        }

//        return new org.springframework.security.core.userdetails.User(person.getUsername(),
//                person.getPassword(), grantedAuthorities);
        return new UserDetailsImpl(person);
    }
}
