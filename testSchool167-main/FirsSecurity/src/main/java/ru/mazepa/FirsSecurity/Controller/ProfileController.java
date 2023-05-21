package ru.mazepa.FirsSecurity.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private final PersonDetailsService personDetailsService;

    public ProfileController(PersonValidator personValidator, RegistrationService registrationService, PersonDetailsService personDetailsService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personDetailsService = personDetailsService;
    }


    @GetMapping()
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());


        return "profile/profile";

    }

    @GetMapping("/edit")
    public String editProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person", personDetails.getPerson());

        // передаем объект person в модель для отображения его текущих значений в форме


        return "/profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("person") @Valid Person person, Model model , BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        personValidator.validate(person, bindingResult);
            model.addAttribute("person", person);
            // устанавливаем обновленные значения для объекта person
            person.setId(personDetails.getPerson().getId());
            person.setPassword(personDetails.getPerson().getPassword());
            person.setRole(personDetails.getPerson().getRole());
            registrationService.updateUser(person, personDetails);
            // обновление данных
            personDetails.getPerson().setSurname(person.getSurname());
            personDetails.getPerson().setName(person.getName());
            personDetails.getPerson().setUsername(person.getUsername());
            personDetails.getPerson().setYearOfBirth(person.getYearOfBirth());

            personDetailsService.updatePerson(personDetails.getPerson());


            // перенаправляем пользователя на страницу его профиля
            return "redirect:/profile";
        }


    }



