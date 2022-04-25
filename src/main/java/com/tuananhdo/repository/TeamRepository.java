package com.tuananhdo.repository;

import com.tuananhdo.entity.Team;
import com.tuananhdo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

}
