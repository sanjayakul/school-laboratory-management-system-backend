    LoginResponseDto login(LoginRequestDto request);
}

import com.laboratory.management.system.model.dto.CreateUserRequestDto;
import com.laboratory.management.system.model.dto.UserDto;
import com.laboratory.management.system.service.criteria.UserCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto createUser(CreateUserRequestDto request);

    Page<UserDto> findUserByFilters(UserCriteria criteria, Pageable pageable);
}

