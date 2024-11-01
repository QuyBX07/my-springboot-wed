package com.example.foodwed.service;

import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.Request.UserLogin;
import com.example.foodwed.entity.User;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserReponsitory userRepository;


    public User createUser(UserCreateRequest request){
        User user = new User();
        if(userRepository.existsByEmail(request.getEmail())){
            throw new Appexception(ErrorCode.USSER_EXITED);
        }
        user.setFullname(request.getFullname());
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

//    public User loginUser(UserLogin request) throws AuthenticationException {
//        // Tìm người dùng bằng email
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new Appexception(ErrorCode.USER_EMAIL_Error));
//
//        // Kiểm tra mật khẩu
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new Appexception(ErrorCode.PASSWORD_NOT_CORECT);
//        }
//
//        // Nếu xác thực thành công, trả về người dùng (hoặc token nếu bạn muốn)
//        return user; // hoặc bạn có thể trả về một token
//    }

}
