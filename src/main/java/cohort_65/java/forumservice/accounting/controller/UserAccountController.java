package cohort_65.java.forumservice.accounting.controller;

import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.UserRegisterDto;
import cohort_65.java.forumservice.accounting.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserAccountController {

    final UserAccountService userAccountService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto userRegisterDto) {
        return userAccountService.register(userRegisterDto);
    }

    @GetMapping("/user/{login}")
    public UserDto getUserByLogin(@PathVariable String login) {
        return userAccountService.getUserByLogin(login);
    }
}
