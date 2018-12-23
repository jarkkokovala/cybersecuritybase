package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/signups", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "signups";
    }

    @RequestMapping(value = "/signups/{id}", method = RequestMethod.GET)
    public String viewSignup(@PathVariable Long id, Model model) {
        model.addAttribute("signup", signupRepository.findOne(id));
        return "view";
    }    
    
    @RequestMapping(value = "/signups/{id}", method = RequestMethod.DELETE)
    public String remove(@PathVariable Long id) {
        signupRepository.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String list(Authentication authentication, Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        return "admin";
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/logout_success", method = RequestMethod.GET)
    public String logoutSuccess() {
        return "logout_success";
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }

    
}
