package ru.mazepa.FirsSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mazepa.FirsSecurity.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person , Integer> {


    Optional<Person> findByUsername(String username);

}
