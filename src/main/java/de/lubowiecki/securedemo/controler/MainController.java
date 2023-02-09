package de.lubowiecki.securedemo.controler;

import de.lubowiecki.securedemo.model.Token;
import de.lubowiecki.securedemo.model.User;
import de.lubowiecki.securedemo.model.UserDto;
import de.lubowiecki.securedemo.repository.TokenRepository;
import de.lubowiecki.securedemo.repository.UserRepository;
import de.lubowiecki.securedemo.service.CustomEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CustomEmailService emailService;

    @GetMapping("")
    public String index(Model model) {
        return "home";
    }

    @GetMapping({"login", "login/{sub}"})
    public String login(@PathVariable Optional<String> sub, Model model) {
        sub.ifPresent(s -> {
            model.addAttribute(s, true);
        });
        return "login";
    }

    @GetMapping("mail")
    public String sendMail(Model model) {
        emailService.sendSimpleEmail("p.parker@shield.org", "Du bist raus...", "Das reicht. Du bist bei uns raus...");
        return "redirect:/";
    }

    @GetMapping("register")
    public String register(UserDto userDto, Model model) {
        return "register";
    }

    @PostMapping("register")
    public String registerProcess(@Valid UserDto userDto, BindingResult result, Model model) throws MessagingException {

        if(!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "error.userDto", "Passwörter müssen übereinstimmen.");
            // Ein Fehler für das Objekt im userDto und das Feld passwordConfirmation
        }

        if(result.hasErrors()) {
            return "register";
        }
        User user = userDto.convert(passwordEncoder);
        userRepository.save(user);
        Token token = new Token(user, Token.TokenType.ACTIVATION);
        tokenRepository.save(token);
        //emailService.sendSimpleEmail(user.getEmail(), "Registrierung", "Du hast dich erfolgreich registriert...");
        //emailService.sendHtmlEmail(user.getEmail(), "Registrierung");
        emailService.sendHtmlRegisterEmail(user, token.getId().toString());
        return "redirect:/register/success";
    }

    @GetMapping("register/success")
    public String registerSuccess(UserDto userDto, Model model) {
        model.addAttribute("success", true);
        return "register";
    }

    @GetMapping("activate/{token}")
    public String checkToken(@PathVariable String token, Model model) {
        Optional<Token> opt = tokenRepository.findById(UUID.fromString(token));
        // TODO: Token abfragen, User aktivieren, Token löschen
        return "register";
    }
}
