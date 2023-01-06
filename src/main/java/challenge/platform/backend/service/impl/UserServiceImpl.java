package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.User;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.UserDto;
import challenge.platform.backend.payload.UserResponse;
import challenge.platform.backend.repository.UserRepository;
import challenge.platform.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // convert entity to dto
    private UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    // convert dto to entity
    private User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // convert dto to entity
        User userToCreate = mapToEntity(userDto);
        userToCreate.setCreated(LocalDate.now());
        User createdUser = userRepository.save(userToCreate);

        return mapToDto(createdUser);
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<User> users = userRepository.findAll(pageable);

        // get content for page object
        UserResponse userResponse = new UserResponse();

        userResponse.setContent(users.getContent().stream().map(user -> mapToDto(user)).toList());
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        User found = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        found.setFirst_name(userDto.getFirst_name());
        found.setFam_name(userDto.getFam_name());
        found.setPassword(userDto.getPassword());
        found.setEmail(userDto.getEmail());
        found.setId_challengge_compl(userDto.getId_challengge_compl());
        User savedBook = userRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteUserById(long id) {
        User found = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(found);
    }


}
