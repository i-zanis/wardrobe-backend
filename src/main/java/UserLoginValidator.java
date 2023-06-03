//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//public class UserLoginValidator implements Validator {
//
//  @Override
//  public boolean supports(Class<?> clazz) {
//    return UserDTO.class.isAssignableFrom(clazz);
//  }
//
//  @Override
//  public void validate(Object target, Errors errors) {
//    UserDTO userDTO = (UserDTO) target;
//
//    if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
//      errors.rejectValue("name", "name.empty");
//    }
//
//    if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
//      errors.rejectValue("email", "email.empty");
//    } else if (!userDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
//      errors.rejectValue("email", "email.invalid");
//    }
//
//    if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
//      errors.rejectValue("password", "password.empty");
//    } else if (userDTO.getPassword().length() < 8 || userDTO.getPassword().length() > 16) {
//      errors.rejectValue("password", "password.length");
//    }
//  }
// }