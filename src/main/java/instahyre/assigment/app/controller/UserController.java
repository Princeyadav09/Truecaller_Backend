package instahyre.assigment.app.controller;

import instahyre.assigment.app.model.request.LoginRequest;
import instahyre.assigment.app.model.request.UserRequest;
import instahyre.assigment.app.model.response.UserResponse;
import instahyre.assigment.app.model.response.UserView;
import instahyre.assigment.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> userRegistration(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.userRegistration(userRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> userLogin(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.userLogin(loginRequest),HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<UserView> getUser(@RequestParam String phoneNumber){
        return new ResponseEntity<>(userService.getUser(phoneNumber),HttpStatus.OK);
    }

}
