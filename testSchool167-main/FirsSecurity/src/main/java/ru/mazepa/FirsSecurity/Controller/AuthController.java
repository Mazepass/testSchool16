package ru.mazepa.FirsSecurity.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mazepa.FirsSecurity.Services.RegistrationService;
import ru.mazepa.FirsSecurity.Validators.PersonValidator;
import ru.mazepa.FirsSecurity.models.Person;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";

    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/auth/registration" ;

        registrationService.register(person);
        System.out.println("Chcked");

        return "redirect:/auth/login";

    }
}
