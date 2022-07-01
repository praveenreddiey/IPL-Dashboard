package com.javabrains.ipldashboard.controller;

import com.javabrains.ipldashboard.Repository.MatchRepository;
import com.javabrains.ipldashboard.Repository.TeamRepository;
import com.javabrains.ipldashboard.model.Team;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository=matchRepository;
    }
    @GetMapping("/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team = teamRepository.findByTeamName(teamName).get();
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName,4));
        return team;
    }
}
