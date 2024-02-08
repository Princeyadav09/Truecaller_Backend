package instahyre.assigment.app.service.Impl;

import instahyre.assigment.app.entity.PhoneNumber;
import instahyre.assigment.app.entity.User;
import instahyre.assigment.app.exception.BusinessException;
import instahyre.assigment.app.model.Contact;
import instahyre.assigment.app.model.request.LoginRequest;
import instahyre.assigment.app.model.request.UserRequest;
import instahyre.assigment.app.model.response.UserResponse;
import instahyre.assigment.app.model.response.UserView;
import instahyre.assigment.app.repository.PhoneNumberRepository;
import instahyre.assigment.app.repository.UserRepository;
import instahyre.assigment.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Override
    public UserResponse userRegistration(UserRequest userRequest) {
        try {
            validate(userRequest.getPhoneNumber());
            if (userRequest.getName() == null || userRequest.getName().isEmpty()) {
                throw new BusinessException("Name cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (userRequest.getPhoneNumber() == null || userRequest.getPhoneNumber().isEmpty()) {
                throw new BusinessException("Phone Number cannot ne empty", HttpStatus.BAD_REQUEST);
            }
            if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
                throw new BusinessException("Password cannot ne empty", HttpStatus.BAD_REQUEST);
            }

            User user = userRepository.findByPhoneNumber(userRequest.getPhoneNumber());
            if (user != null) {
                throw new BusinessException("Phone number already exists !", HttpStatus.BAD_REQUEST);
            }
            user = new User();
            user.setName(userRequest.getName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setPassword(userRequest.getPassword());
            user.setEmail(userRequest.getEmail());

            User savedUser = userRepository.save(user);

            Contact contact0 = new Contact();
            contact0.setName(userRequest.getName());
            contact0.setPhoneNumber(userRequest.getPhoneNumber());

            List<Contact> contactList = userRequest.getContacts();
            if(contactList!=null) {
                contactList.add(contact0);
                    for (Contact contact : contactList) {
                        validate(contact.getPhoneNumber());
                        PhoneNumber phoneNumber = phoneNumberRepository.findByPhoneNumber(contact.getPhoneNumber());
                        if (phoneNumber != null) {
                            phoneNumber.getNames().add(contact.getName());
                        } else {
                            phoneNumber = new PhoneNumber();
                            phoneNumber.setPhoneNumber(contact.getPhoneNumber());
                            phoneNumber.setNames(Collections.singletonList(contact.getName()));
                        }
                        phoneNumberRepository.save(phoneNumber);
                    }
            }

            UserResponse userResponse = new UserResponse();
            userResponse.setId(savedUser.getId());
            userResponse.setName(savedUser.getName());
            userResponse.setPhoneNumber(savedUser.getPhoneNumber());
            userResponse.setEmail(savedUser.getEmail());
            return userResponse;

        } catch (BusinessException ex){
            throw  ex;
        } catch (Exception ex){
            throw new BusinessException(ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public UserResponse userLogin(LoginRequest loginRequest) {
       try{
           validate(loginRequest.getPhoneNumber());
           if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
               throw new BusinessException("Password cannot ne empty",HttpStatus.BAD_REQUEST);
           }
           User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
           if(user == null){
               throw new BusinessException("Number not registered !",HttpStatus.BAD_REQUEST);
           }
           if(!user.getPassword().equals(loginRequest.getPassword())){
               throw new BusinessException("Invalid Password !", HttpStatus.BAD_REQUEST);
           }

           UserResponse userResponse = new UserResponse();
           userResponse.setId(user.getId());
           userResponse.setName(user.getName());
           userResponse.setPhoneNumber(user.getPhoneNumber());
           userResponse.setEmail(user.getEmail());
           return userResponse;
       } catch (Exception ex){
           throw new BusinessException(ex);
       }

    }

    @Override
    public UserView getUser(String phoneNumber) {
        validate(phoneNumber);

            UserView userView = new UserView();
            User user = userRepository.findByPhoneNumber(phoneNumber);
            if(user != null){
                userView.setName(user.getName());
                userView.setPhoneNumber(user.getPhoneNumber());
                userView.setEmail(user.getEmail());
            } else {
                PhoneNumber phoneNumber1 = phoneNumberRepository.findByPhoneNumber(phoneNumber);
                if (phoneNumber1 != null) {
                    userView.setName(phoneNumber1.getNames().get(0));
                    userView.setPhoneNumber(phoneNumber1.getPhoneNumber());
                } else {
                    throw new BusinessException("Phone Number does not exists !",HttpStatus.BAD_REQUEST);
                }
            }
            return userView;
    }

    public void validate(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){
            throw new BusinessException("Phone Number cannot ne empty",HttpStatus.BAD_REQUEST);
        }
        if(phoneNumber.length() != 10){
            throw  new BusinessException("Phone Number is not valid !", HttpStatus.BAD_REQUEST);
        }
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(phoneNumber).matches()) {
            throw new BusinessException("Phone Number must contain only digits!", HttpStatus.BAD_REQUEST);
        }

    }

}
