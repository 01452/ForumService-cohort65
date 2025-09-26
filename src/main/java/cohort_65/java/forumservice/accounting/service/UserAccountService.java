package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.UserRegisterDto;

public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);

    UserDto getUserByLogin(String login);
}
