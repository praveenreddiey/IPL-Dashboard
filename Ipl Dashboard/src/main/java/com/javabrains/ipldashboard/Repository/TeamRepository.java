package com.javabrains.ipldashboard.Repository;

import com.javabrains.ipldashboard.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findByTeamName(String teamName);
}
