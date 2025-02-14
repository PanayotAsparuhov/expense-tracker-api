package com.pairlearning.expensetracker.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;


import com.pairlearning.expensetracker.Constants;
import com.pairlearning.expensetracker.domain.User;
import com.pairlearning.expensetracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.ConstantCallSite;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email,password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String, Object> userMap)
    {
        String firstName=(String) userMap.get("firstName");
        String lastName=(String) userMap.get("lastName");
        String email=(String) userMap.get("email");
        String password=(String) userMap.get("password");
        User user = userService.registerUser(firstName,lastName,email,password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }
    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        byte[] apiSecretKey = Constants.API_SECRET_KEY.getBytes();

        // Проверка за дължината на ключа
        if (apiSecretKey.length < 32) {
            // Генерира валиден Base64 ключ
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            Constants.API_SECRET_KEY = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        }

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)  // Използва валиден ключ
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }



//    private Map<String, String> generateJWTToken(User user){
//        long timestamp = System.currentTimeMillis();
//        byte[] apiSecretKey = Constants.API_SECRET_KEY.getBytes();
//        if (apiSecretKey.length < 32) {
//            throw new IllegalArgumentException("API_SECRET_KEY трябва да е поне 256-битов (32 байта).");
//        }
//
//        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
//                .setIssuedAt(new Date(timestamp))
//                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
//                .claim("userId", user.getUserId())
//                .claim("email",user.getEmail())
//                .claim("firstName", user.getFirstName())
//                .claim("lastName", user.getLastName())
//                .compact();
//
//        Map<String, String> map = new HashMap<>();
//        map.put("token", token);
//        return map;
//    }
}
