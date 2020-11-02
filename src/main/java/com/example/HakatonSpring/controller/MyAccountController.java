package com.example.HakatonSpring.controller;

import com.example.HakatonSpring.accessingDataMySql.HistoryRepository;
import com.example.HakatonSpring.accessingDataMySql.StatisticsRepository;
import com.example.HakatonSpring.accessingDataMySql.UserRepository;
import com.example.HakatonSpring.model.History;
import com.example.HakatonSpring.model.Statistics;
import com.example.HakatonSpring.model.User;
import com.example.HakatonSpring.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/myAccount")
public class MyAccountController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    private JwtUtils jwtUtils;

    int num = 0;
    int count = 0;

    @GetMapping("/getInfoUser")
    public Optional<User> getUser(@RequestParam String token) {
        String name = jwtUtils.getUserNameFromJwtToken(token);
        return userRepository.findByName(name);
    }

    @GetMapping("/getHistory")
    public Iterable<History> getHistory(@RequestParam String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        return historyRepository.findAllByUser(userRepository.findByName(userName).get());
    }

    @GetMapping("/getStatics")
    public Iterable<Statistics> getStatics(@RequestParam String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        return statisticsRepository.findAllByUser(userRepository.findByName(userName).get());
    }

    @PutMapping("/putAvatar")
    public boolean putAvatar(@RequestParam String token, @RequestParam String avatar) {
        try {
            String userName = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByName(userName).get();
            user.setAvatar(avatar);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PutMapping("/put")
    public boolean putUser(@Valid @RequestBody User user, @RequestParam String token) {
        try {
            String userName = jwtUtils.getUserNameFromJwtToken(token);
            User user1 = userRepository.findByName(userName).get();
            user1.setPass(user.getPass());
            user1.setDataOfBith(user.getDataOfBith());
            user1.setText(user.getText());
            userRepository.save(user1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/startGame")
    public void startGame() {
        count = 4;
        num = (int) (1 + Math.random() * 10);
    }

    @PostMapping("/verifyByGame")
    public boolean verifyByGame(@RequestParam String token, @RequestParam int num) {
        if (count != 0) {
            if (this.num == num) {
                String userName = jwtUtils.getUserNameFromJwtToken(token);
                User user = userRepository.findByName(userName).get();
                switch (count) {
                    case 1:
                        user.setBalance(user.getBalance() + 25);
                        userRepository.save(user);
                        count = 0;
                        return true;
                    case 2:
                        user.setBalance(user.getBalance() + 50);
                        userRepository.save(user);
                        count = 0;
                        return true;
                    case 3:
                        user.setBalance(user.getBalance() + 75);
                        userRepository.save(user);
                        count = 0;
                        return true;
                    case 4:
                        user.setBalance(user.getBalance() + 100);
                        userRepository.save(user);
                        count = 0;
                        return true;
                    default:
                        return false;
                }
            } else {
                count--;
                return false;
            }
        } else return false;

    }

}
