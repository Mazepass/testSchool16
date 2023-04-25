package ru.mazepa.FirsSecurity.Controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mazepa.FirsSecurity.Security.PersonDetails;
import ru.mazepa.FirsSecurity.Services.PersonDetailsService;
import ru.mazepa.FirsSecurity.Services.RegistrationService;
import ru.mazepa.FirsSecurity.Validators.PersonValidator;
import ru.mazepa.FirsSecurity.models.Person;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    PersonDetailsService personDetailsService;

    public ProfileController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }
    @CacheEvict(value = "persons", allEntries = true)
    @CachePut(value = "persons", key = "#person.id")
    @GetMapping
    public String profile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();

   model.addAttribute("person" , personDetails.getPerson() );
    return "profile/profile" ;

    }

@GetMapping("/edit")
    public String editProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

    // передаем объект person в модель для отображения его текущих значений в форме
    model.addAttribute("person", personDetails.getPerson());



    return "/profile/edit";
    }
    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("person") Person person, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        // устанавливаем обновленные значения для объекта person
        person.setId(personDetails.getPerson().getId());
        person.setPassword(personDetails.getPerson().getPassword());
        person.setRole(personDetails.getPerson().getRole());
        registrationService.updateUser(person , personDetails);

        // сохраняем обновленный объект person в базе данных

        // перенаправляем пользователя на страницу его профиля
        return "redirect:/profile";
    }




}
