package mapper;


import model.User;
import model.builder.UserBuilder;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;


public class UserMapper {

    public static UserDTO converUserToUserDTO(User user) {
        return new UserDTOBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .build();
    }

    public static User convertUserDTOToUser(UserDTO userDTO){
        return new UserBuilder()
                .setId(userDTO.getId())
                .setPassword(userDTO.getPassword())
                .setUsername(userDTO.getUsername())
                .build();
    }

    public static List<UserDTO> convertUserListToUserDTOList(List<User> userList){
        return userList.parallelStream().map(UserMapper::converUserToUserDTO).collect(Collectors.toList());
    }
    public static List<User> convertUserDTOListToUserList(List<UserDTO> userDTOList){
        return userDTOList.parallelStream().map(UserMapper::convertUserDTOToUser).collect(Collectors.toList());
    }
}

