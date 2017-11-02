package com.Aleksandr.Cake.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Aleksandr.Cake.model.Schedule;
import com.Aleksandr.Cake.repository.ScheduleRepository;

@Controller
public class CalendarController {

	private final Logger LOGGER = LoggerFactory.getLogger(CalendarController.class);

	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping(value = "/calendar")
	public String calendar() {
		LOGGER.info("-- open page calendar");
		return "calendar";
	}

	@ModelAttribute("schedules")
	public Model days(Model model) {
		List<Schedule> schedules = scheduleRepository.findAll();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		GregorianCalendar cal = new GregorianCalendar();

		Date date = new Date();

		System.out.println(cal.get(Calendar.MONTH) + " month");
		System.out.println(cal.get(Calendar.DAY_OF_MONTH) + " day for month");
		System.out.println(cal.getTime());
		System.out.println(cal.get(Calendar.DAY_OF_WEEK) + " day for week");
		System.out.println(cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " day of week in month");

		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
		System.out.println(daysInMonth + " days");

		GregorianCalendar startMonth = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		System.out.println(startMonth.get(Calendar.DAY_OF_WEEK) + " day for week start month");
		System.out.println(startMonth.getTime());

		Map<Integer, Map<Integer, List<Schedule>>> all = new HashMap<>();
		int firstDay = 8 - startMonth.get(Calendar.DAY_OF_WEEK);
		for (int i = 1; i < daysInMonth; i++) {
			if (i <= firstDay) {
				createMap(all, i, 1, schedules);
			} else if (i <= firstDay + 7) {
				createMap(all, i, 2, schedules);
			} else if (i <= firstDay + 14) {
				createMap(all, i, 3, schedules);
			} else if (i <= firstDay + 21) {
				createMap(all, i, 4, schedules);
			} else if (i <= firstDay + 28) {
				createMap(all, i, 5, schedules);
			} else {
				createMap(all, i, 6, schedules);
			}

		}

		List<Integer> weeks = IntStream.range(1, 7).boxed().collect(Collectors.toList());
		model.addAttribute("weeks", weeks);
		model.addAttribute("schedules", schedules);
		model.addAttribute("isUser", true);
		model.addAttribute("mapAll", all);
		return model;
	}

	private void createMap(Map<Integer, Map<Integer, List<Schedule>>> month, int day, int week, List<Schedule> schedules) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		List<Schedule> thisDay = schedules.stream().filter(d -> d.getDate().getDate() == day)
				.collect(Collectors.toList());
			
			if (thisDay.isEmpty()) {
				Map<Integer, List<Schedule>> aboutThisDay;
				if (month.get(week) == null) {
					aboutThisDay = new HashMap<>();
				} else {
					aboutThisDay = month.get(week);
				}
				aboutThisDay.put(day, new ArrayList<>());
				month.put(week, aboutThisDay);
			} else {
				Map<Integer, List<Schedule>> aboutThisDay;
				if (month.get(week) == null) {
					aboutThisDay = new HashMap<>();
				} else {
					aboutThisDay = month.get(week);
				}
				
				aboutThisDay.put(day, thisDay);
				month.put(week, aboutThisDay);
			}

//		System.out.println(all + " size");
	}
}
