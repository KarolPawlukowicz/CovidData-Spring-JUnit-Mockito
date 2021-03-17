package com.tracker.Covid.respository;

import com.tracker.Covid.model.Covid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CovidRepository extends JpaRepository<Covid, Long>{

}
