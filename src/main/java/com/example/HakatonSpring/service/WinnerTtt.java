package com.example.HakatonSpring.service;

import com.example.HakatonSpring.accessingDataMySql.HistoryRepository;
import com.example.HakatonSpring.accessingDataMySql.StatisticsRepository;
import com.example.HakatonSpring.accessingDataMySql.UserRepository;
import com.example.HakatonSpring.model.Games;
import com.example.HakatonSpring.model.History;
import com.example.HakatonSpring.model.Statistics;
import com.example.HakatonSpring.model.User;
import com.example.HakatonSpring.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WinnerTtt {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public void winner(String player, int bet) {
        String userName = jwtUtils.getUserNameFromJwtToken(player);
        User user = userRepository.findByName(userName).get();
        user.setBalance(user.getBalance() + bet);

        List<Statistics> statistics;
        if (statisticsRepository.existsByGamesAndUser(Games.TTT, user)) {
            statistics = statisticsRepository.findAllByGamesAndUser(Games.TTT, user);
        } else {
            statistics = new ArrayList<>();
            statistics.add(new Statistics());
            statistics.get(0).setUser(user);
            statistics.get(0).setGames(Games.TTT);
            statistics.get(0).setLose(0);
            statistics.get(0).setWin(0);
            statistics.get(0).setEarning("0");
        }
        int statBal = Integer.parseInt(statistics.get(0).getEarning());
        statBal = statBal + bet;
        statistics.get(0).setEarning(String.valueOf(statBal));
        statistics.get(0).setWin(statistics.get(0).getWin() + 1);
        statisticsRepository.save(statistics.get(0));

        List<History> history = new ArrayList<>();
        history.add(new History());
        history.get(0).setUser(user);
        history.get(0).setTime(new Date());
        history.get(0).setGames(Games.TTT);
        history.get(0).setMoney(String.valueOf(bet));
        historyRepository.save(history.get(0));

        userRepository.save(user);
    }

    public void loser(String player, int bet) {
        String userName = jwtUtils.getUserNameFromJwtToken(player);
        User user = userRepository.findByName(userName).get();
        user.setBalance(user.getBalance() - bet);

        List<Statistics> statistics;
        if (statisticsRepository.existsByGamesAndUser(Games.TTT, user)) {
            statistics = statisticsRepository.findAllByGamesAndUser(Games.TTT, user);
        } else {
            statistics = new ArrayList<>();
            statistics.add(new Statistics());
            statistics.get(0).setUser(user);
            statistics.get(0).setGames(Games.TTT);
            statistics.get(0).setLose(0);
            statistics.get(0).setWin(0);
            statistics.get(0).setEarning("0");
        }
        int statBal = Integer.parseInt(statistics.get(0).getEarning());
        statBal = statBal - bet;
        statistics.get(0).setEarning(String.valueOf(statBal));
        statistics.get(0).setLose(statistics.get(0).getLose() + 1);
        statisticsRepository.save(statistics.get(0));

        List<History> history = new ArrayList<>();
        history.add(new History());
        history.get(0).setUser(user);
        history.get(0).setGames(Games.TTT);
        history.get(0).setMoney("-"+bet);
        historyRepository.save(history.get(0));
        userRepository.save(user);
    }
}
