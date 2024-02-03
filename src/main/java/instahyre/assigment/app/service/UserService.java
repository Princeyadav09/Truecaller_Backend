package instahyre.assigment.app.service;


import instahyre.assigment.app.entity.PhoneNumber;
import instahyre.assigment.app.model.request.LoginRequest;
import instahyre.assigment.app.model.request.UserRequest;
import instahyre.assigment.app.model.response.UserResponse;
import instahyre.assigment.app.model.response.UserView;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface UserService {
    UserResponse userRegistration(UserRequest userRequest);
    UserResponse userLogin(LoginRequest loginRequest);
    UserView getUser(String phoneNumber);
}
