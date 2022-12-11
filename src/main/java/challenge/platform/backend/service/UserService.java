package challenge.platform.backend.service;

import challenge.platform.backend.payload.UserDto;
import challenge.platform.backend.payload.UserResponse;

public interface UserService {

    UserDto createUser(UserDto bookDto);

    UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);

    UserDto getUserById(long id);

    UserDto updateUser(long id, UserDto userDto);

    void deleteUserById(long id);

}
