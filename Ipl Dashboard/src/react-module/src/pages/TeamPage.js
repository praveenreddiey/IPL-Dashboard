import React, { useEffect, useState } from "react";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { MatchDetailCard } from "../components/MatchDetailCard";

export const TeamPage = () => {
  const [team, setTeam] = useState({ matches: [] });

  useEffect(() => {
    const fetchMatches = async () => {
      const response = await fetch(
        "http://localhost:8080/team/Mumbai%20Indians"
      );
      const data = await response.json();
      setTeam(data);
    };
    fetchMatches();
  }, []);

  return (
    <div className="TeampPage">
      <h1>{team.teamName}</h1>
      <MatchDetailCard match={team.matches[0]} />
      {team.matches.slice(1).map((match) => (
        <MatchSmallCard match={match} />
      ))}
    </div>
  );
};
