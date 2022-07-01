package com.javabrains.ipldashboard.Repository;

import com.javabrains.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match,Long> {
    List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    public default List<Match> findLatestMatchesByTeam(String team1, int count){
        Pageable pageable=PageRequest.of(0, 3);
        return findByTeam1OrTeam2OrderByDateDesc(team1, team1,PageRequest.of(0, count));
    }
}
