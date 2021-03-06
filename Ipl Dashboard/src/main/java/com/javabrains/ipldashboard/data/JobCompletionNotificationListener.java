package com.javabrains.ipldashboard.data;

import com.javabrains.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
        }
        List<Object[]> list=new ArrayList<>();
        Map<String,Team> teamMap=new HashMap<>();
        entityManager.createQuery("select m.team1, count(*) from Match m group by m.team1",Object[].class)
                .getResultList()
                .stream()
                .map(e -> new Team((String)e[0],(long)e[1]))
                .forEach(team->teamMap.put(team.getTeamName(),team));

        entityManager.createQuery("select m.team2, count(*) from Match m group by m.team2",Object[].class)
                .getResultList()
                .stream()
                .forEach(
                        e->{
                            Team team= teamMap.get((String)e[0]);
//                            if(team==null){
//                                teamMap.put((String)e[0],new Team((String)e[0],(long)e[1]));
//                            }
                            team.setTotalMatches(team.getTotalMatches()+(long)e[1]);
                        });

        entityManager.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner",Object[].class)
                .getResultList()
                .stream()
                .forEach(
                        e->{
                            Team team=teamMap.get((String)e[0]);
                            if(team!=null){
                                team.setTotalWins((long)e[1]);
                            }

                        }
                );
        teamMap.values().forEach(team-> entityManager.persist(team));
        teamMap.values().forEach(team-> System.out.println(team));







    }
}
