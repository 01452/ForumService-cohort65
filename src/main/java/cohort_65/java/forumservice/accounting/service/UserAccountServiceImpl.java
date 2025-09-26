package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dao.UserAccountRepository;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.UserRegisterDto;
import cohort_65.java.forumservice.accounting.dto.exception.UserExistsException;
import cohort_65.java.forumservice.accounting.dto.exception.UserNotFoundException;
import cohort_65.java.forumservice.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    final UserAccountRepository userAccountRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
            throw new UserExistsException();
        }
        UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto getUserByLogin(String login) {
        UserAccount userAccount = userAccountRepository.findById(login)
                .orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }
}
