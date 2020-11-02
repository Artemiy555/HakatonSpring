package com.example.HakatonSpring.controller;

import com.example.HakatonSpring.accessingDataMySql.HistoryRepository;
import com.example.HakatonSpring.accessingDataMySql.StatisticsRepository;
import com.example.HakatonSpring.accessingDataMySql.UserRepository;
import com.example.HakatonSpring.model.Games;
import com.example.HakatonSpring.model.History;
import com.example.HakatonSpring.model.Statistics;
import com.example.HakatonSpring.model.User;
import com.example.HakatonSpring.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class StaticController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("/")
    public String homePage(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        model.addAttribute("welcome", "Welcome " + user.getEmail() + "!");
        return "sign-in";
    }
    /**
     * Login
     */
    @GetMapping(value = "/myAccount")
    public String myAccount(@RequestParam(value = "token") String token,Model model) {
        String name = jwtUtils.getUserNameFromJwtToken(token);
        List<Statistics> statistics = statisticsRepository.findAllByUser(userRepository.findByName(name).get());
        List<History> histories = historyRepository.findAllByUser(userRepository.findByName(name).get());
        //List<History> histories = his.findAllByUser(userRepository.findByName(name).get());


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        model.addAttribute("years", arr(2019, 2050));
//        int year = 2019;
//        model.addAttribute("year", year);
        List<Games> games = statistics.stream().map(Statistics::getGames).collect(Collectors.toList());
        List<Integer> wins = statistics.stream().map(Statistics::getWin).collect(Collectors.toList());
        List<Integer> loses = statistics.stream().map(Statistics::getLose).collect(Collectors.toList());
        List<String> earnings = statistics.stream().map(Statistics::getEarning).collect(Collectors.toList());
        model.addAttribute("name", games);
        model.addAttribute("wins",  wins);
        model.addAttribute("loses",  loses);
        model.addAttribute("earnings",  earnings);

        List<Games> nameH = histories.stream().map(History::getGames).collect(Collectors.toList());
        List<String> money = histories.stream().map(History::getMoney).collect(Collectors.toList());
        List<String> time = histories.stream().map(e -> String.valueOf(e.getTime())).collect(Collectors.toList());
        model.addAttribute("nameH", nameH);
        model.addAttribute("money", money);
        model.addAttribute("time", time);
//
//        model.addAttribute("name",  statistics.get(0).getGames());
//        model.addAttribute("money",  statistics.get(0).getGames());
//        model.addAttribute("time",  statistics.get(0).getGames());
        return "myAccount";
    }

    @GetMapping(value = "/sign-up")
    public String signUp(Model model) {
        return "sign-up";
    }

    @GetMapping(value = "/ttt")
    public String ttt(Model model) {
        return "ttt";
    }

    @GetMapping(value = "/home")
    public String race(Model model) {
        return "race";
    }
}

