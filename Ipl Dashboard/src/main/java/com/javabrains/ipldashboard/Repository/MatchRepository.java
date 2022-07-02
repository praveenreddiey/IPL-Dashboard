package com.javabrains.ipldashboard.Repository;

import com.javabrains.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match,Long> {
    List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

//    List<Match> findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
//            String team1, LocalDate date1, LocalDate date2,
//            String team2,LocalDate date3, LocalDate date4);

    @Query("select m from Match m where (m.team1 = :teamName or m.team2= :teamName) and m.date between :dateStart and :dateEnd order by date desc")
    List<Match> getMatchesByTeamBetweenDates(
            @Param("teamName") String teamName,
            @Param("dateStart") LocalDate dateStart,
            @Param("dateEnd") LocalDate dateEnd);

    public default List<Match> findLatestMatchesByTeam(String team1, int count){
        Pageable pageable=PageRequest.of(0, 3);
        return findByTeam1OrTeam2OrderByDateDesc(team1, team1,PageRequest.of(0, count));
    }
}
