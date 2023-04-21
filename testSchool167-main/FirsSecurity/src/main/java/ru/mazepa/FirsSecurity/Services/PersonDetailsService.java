package ru.mazepa.FirsSecurity.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mazepa.FirsSecurity.Security.PersonDetails;
import ru.mazepa.FirsSecurity.models.Person;
import ru.mazepa.FirsSecurity.repository.PeopleRepository;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {


    private final PeopleRepository peopleRepository;
@Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь с таким именем не найден!");

        return new PersonDetails(person.get());
    }
}
