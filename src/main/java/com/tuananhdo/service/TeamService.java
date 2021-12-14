package com.tuananhdo.service;

import com.sun.mail.imap.protocol.ID;
import com.tuananhdo.entity.Team;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.TeamNotFoundException;
import com.tuananhdo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    public List<Team> listAllTeams() {
        return teamRepository.findAll();
    }

    public void saveTeam(Team team) {

        teamRepository.save(team);
    }

    public Team getTeamById(Integer id) throws TeamNotFoundException {
        return teamRepository.findById(id).orElseThrow(
                () -> new TeamNotFoundException("Could not find with team id " + id));
    }
    
    public void deleteTeam(Integer id) throws TeamNotFoundException {
        teamRepository.deleteById(id);
    }
}
