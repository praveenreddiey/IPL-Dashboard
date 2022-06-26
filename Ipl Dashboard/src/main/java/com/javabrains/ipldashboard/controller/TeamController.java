package com.javabrains.ipldashboard.controller;

import com.javabrains.ipldashboard.Repository.MatchRepository;
import com.javabrains.ipldashboard.Repository.TeamRepository;
import com.javabrains.ipldashboard.model.Team;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository=matchRepository;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team = teamRepository.findByTeamName(teamName).get();
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName,teamName));
        return team;
    }
}
