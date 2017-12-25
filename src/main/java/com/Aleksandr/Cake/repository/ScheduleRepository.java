package com.Aleksandr.Cake.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Aleksandr.Cake.model.Schedule;

@Repository("scheduleRepository")
public interface ScheduleRepository  extends JpaRepository<Schedule, Integer> {

	List<Schedule> findByDateOreder(final Date dateOreder);
}
