package com.Aleksandr.Cake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Aleksandr.Cake.model.Schedule;

@Repository("scheduleRepository")
public interface ScheduleRepository  extends JpaRepository<Schedule, Integer> {

}
