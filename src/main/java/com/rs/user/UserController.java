package com.rs.user;


import com.rs.jwt.JwtTokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtility jwtTokenUtility;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired UserInfoRepository userInfoRepository;

    @PostMapping("login")
    public ResponseEntity<LoginResponse>login(@RequestBody LoginInfo loginInfo ){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword())
        );
        UserInfo user = (UserInfo) authentication.getPrincipal();
        String accessToken = jwtTokenUtility.generateAccessToken(user);
        LoginResponse loginResponse = new LoginResponse(user.getUsername(), accessToken);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("registrasi")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationInfo registrationInfo){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registrationInfo.getUsername());
        userInfo.setPassword(passwordEncoder.encode(registrationInfo.getPassword()));
        userInfo.setAddress(registrationInfo.getAddress());
        userInfo.setName(registrationInfo.getName());
        userInfoRepository.save(userInfo);
        return ResponseEntity.ok().build();
    }


    /*UserService userService = new UserService();

    @GetMapping("/")
    public ResponseEntity<Vector<MyUser>> test() {
        return new ResponseEntity<Vector<MyUser>>(userService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<String>User(@RequestBody MyUser user){
        if (user!=null){
            userService.User(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

     */
}
