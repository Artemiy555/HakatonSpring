package com.example.HakatonSpring.controller;

import com.example.HakatonSpring.accessingDataMySql.HistoryRepository;
import com.example.HakatonSpring.accessingDataMySql.StatisticsRepository;
import com.example.HakatonSpring.accessingDataMySql.UserRepository;
import com.example.HakatonSpring.model.*;
import com.example.HakatonSpring.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/raceGame")
public class RaceGameController {

    private static final Logger logger = LoggerFactory.getLogger(RaceGameController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/startGame")
    public Rase startGame(@RequestParam String token, @RequestParam int idBar, @RequestParam int bet) {

        logger.info("Token ---- " + token);
        logger.info("idBar ---- " + idBar);
        logger.info("bet ---- " + bet);

        Rase rase = new Rase();
        Set<Double> doubles = rase.getDoubles();
        doubles.add(Math.random());
        doubles.add(Math.random());
        doubles.add(Math.random());
        doubles.add(Math.random());
        doubles.add(Math.random());

        double max = Collections.max(doubles);
        logger.info("TOKEN " + token);
        String userName = jwtUtils.getUserNameFromJwtToken(token);

        logger.info("User Name by Token --- " + userName);
        User user = userRepository.findByName(userName).get();

        List<History> history = new ArrayList<>();
        history.add(new History());
        history.get(0).setUser(user);
        history.get(0).setGames(Games.RASE);

        List<Statistics> statistics;
        if(statisticsRepository.existsByGamesAndUser(Games.RASE,user)) {
            statistics = statisticsRepository.findAllByGamesAndUser(Games.RASE, user);
        }else{
            statistics = new ArrayList<>();
            statistics.add(new Statistics());
            statistics.get(0).setUser(user);
            statistics.get(0).setGames(Games.RASE);
            statistics.get(0).setLose(0);
            statistics.get(0).setWin(0);
            statistics.get(0).setEarning("0");
        }

        int statBal = Integer.parseInt(statistics.get(0).getEarning());

        int count = 0;
        for (Double dob : doubles) {
            if (dob == max && count == idBar) {
                int bal =user.getBalance()+bet;
                user.setBalance(bal);
                statBal = statBal + bet;
                statistics.get(0).setEarning(String.valueOf(statBal));
                statistics.get(0).setWin(statistics.get(0).getWin()+1);
                statisticsRepository.save(statistics.get(0));
                history.get(0).setMoney(String.valueOf(bet));
                historyRepository.save(history.get(0));
                rase.setWinLose(true);

                userRepository.save(user);
                return rase;
            }
            count++;
        }
        
        statBal = statBal - bet;
        statistics.get(0).setEarning(String.valueOf(statBal));
        history.get(0).setMoney(String.valueOf(-bet));
        history.get(0).setTime(new Date());
        statistics.get(0).setWin(statistics.get(0).getLose()+1);
        statisticsRepository.save(statistics.get(0));
        historyRepository.save(history.get(0));
        user.setBalance(user.getBalance()-bet);
        rase.setWinLose(false);
        userRepository.save(user);

        return rase;
    }

}
