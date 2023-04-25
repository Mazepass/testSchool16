package ru.mazepa.FirsSecurity.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mazepa.FirsSecurity.Security.PersonDetails;
import ru.mazepa.FirsSecurity.models.Person;
import ru.mazepa.FirsSecurity.repository.PeopleRepository;
@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

@Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
    this.passwordEncoder = passwordEncoder;
}

    @Transactional
    @CacheEvict(value = "person", key = "#personDetails.person.id")
    public void register(Person person){
     String encodedPassword = passwordEncoder.encode(person.getPassword());
     person.setPassword(encodedPassword);
     System.out.println("Сохранено");
     person.setRole("ROLE_USER");
     peopleRepository.save(person);



    }
    @Transactional
    @CacheEvict(value = "person", key = "#personDetails.person.id")
    public void updateUser(Person person, PersonDetails personDetails) {
        person.setId(personDetails.getPerson().getId());
        person.setRole(personDetails.getPerson().getRole());
        String encodedPassword = person.getPassword();
        person.setPassword(encodedPassword);
        peopleRepository.save(person);
    }

}
