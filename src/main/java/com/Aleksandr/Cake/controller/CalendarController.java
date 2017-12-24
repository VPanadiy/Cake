package com.Aleksandr.Cake.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Aleksandr.Cake.model.Schedule;
import com.Aleksandr.Cake.repository.ScheduleRepository;

@Controller
public class CalendarController {

	private final Logger LOGGER = LoggerFactory.getLogger(CalendarController.class);

	Map<Integer, Map<Integer, List<Schedule>>> allMonth;
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping(value = "/calendar")
	public String calendar(Model model) {
		createCalendar(model, true, 0);

		return "calendar";
	}

	@RequestMapping(value = "/calendar", method=RequestMethod.POST)
	public String save(@ModelAttribute("schedule") Schedule schedule, BindingResult result) {
		LOGGER.info("-- Save Schedule: " + schedule.toString()+ "......................................" + result.toString());
		if (result.hasErrors()) {
            return "calendar";
        }
		scheduleRepository.save(schedule);
		return "redirect:/calendar";
	}
	
	
	@RequestMapping(value = "/calendar/month={month}", method=RequestMethod.GET)
	public String calendar(@PathVariable("month") int month,  Model model) {
		LOGGER.info("------ Open with month: " + month + ".....................................................................");
		createCalendar(model, false, month);
		return "calendar";
	}
	
	@RequestMapping(value = "/calendar/informationByDay={day}", method=RequestMethod.GET)
	public String calendarForDay(@ModelAttribute(value = "thisMonth") int month, @PathVariable("day") String day, Model model) {
		LOGGER.info("------ Open with month: " + day + ".....................................................................");
		LOGGER.info("------ Open with month: " + month + ".....................................................................");
		LOGGER.info("------ Open with month: " + model + ".....................................................................");
//		createCalendar(model, false, month);
		return "calendar";
	}
	
	
	
	private void createCalendar(Model model, boolean current, int monthFromWeb) {
		LOGGER.info("-- Open page calendar");
		List<Schedule> schedules = scheduleRepository.findAll();
		GregorianCalendar cal = new GregorianCalendar();

		int month = current ? cal.get(Calendar.MONTH) : monthFromWeb;

		GregorianCalendar startMonth = new GregorianCalendar(cal.get(Calendar.YEAR), month, 1);

		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		LOGGER.info("-- The current date: " + cal.getTime() + ". This month: " + month + ". Days for month: "
				+ daysInMonth + ". Start month day of week number: " + startMonth.get(Calendar.DAY_OF_WEEK));
		LOGGER.info("-- The satrt current month: " + startMonth.getTime());

		Map<Integer, Map<Integer, List<Schedule>>> allMonth = new HashMap<>();

		int firstDay = 8 - startMonth.get(Calendar.DAY_OF_WEEK); // get number day of week

		for (int i = 1; i < daysInMonth + 1; i++) {
			if (i <= firstDay) {
				createMap(allMonth, i, 1, schedules, startMonth);
			} else if (i <= firstDay + 7) {
				createMap(allMonth, i, 2, schedules, startMonth);
			} else if (i <= firstDay + 14) {
				createMap(allMonth, i, 3, schedules, startMonth);
			} else if (i <= firstDay + 21) {
				createMap(allMonth, i, 4, schedules, startMonth);
			} else if (i <= firstDay + 28) {
				createMap(allMonth, i, 5, schedules, startMonth);
			} else {
				createMap(allMonth, i, 6, schedules, startMonth);
			}
		}

		Map<String, Integer> nameWeek = cal.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.SHORT_STANDALONE, Locale.US);
		List<String> result = nameWeek.entrySet().stream()
		        .sorted(Comparator.comparing(Map.Entry::getValue)).sequential()
		        .map(Map.Entry::getKey)
		        .collect(Collectors.toList());
		List<Integer> weeks = IntStream.range(1, 7).boxed().collect(Collectors.toList());
		System.out.println(nameWeek);
		System.out.println(result);
		
		model.addAttribute("thisMonth", startMonth.get(Calendar.MONTH));
		model.addAttribute("thisYear", startMonth.get(Calendar.YEAR));
		model.addAttribute("previous", (month - 1));
		model.addAttribute("next", (month + 1));
		model.addAttribute("nameWeek", result);
		model.addAttribute("weeks", weeks);
		model.addAttribute("schedules", schedules);
		model.addAttribute("isUser", true);
		model.addAttribute("allMonth", allMonth);
	}

	private void createMap(Map<Integer, Map<Integer, List<Schedule>>> allMonth, int stepDay, int week,
			List<Schedule> schedules, GregorianCalendar cal) {
		int thisYear = cal.get(Calendar.YEAR);
		int thisMonth = cal.get(Calendar.MONTH);

		Calendar calendar = new GregorianCalendar();

		List<Schedule> thisDay = schedules.stream().filter(d -> {
			calendar.setTime(d.getDate_order());
			if (calendar.get(Calendar.DAY_OF_MONTH) == stepDay && calendar.get(Calendar.MONTH) == thisMonth
					&& calendar.get(Calendar.YEAR) == thisYear) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());

		Map<Integer, List<Schedule>> aboutThisDay = (allMonth.get(week) == null) ? new HashMap<>() : allMonth.get(week);

		if (thisDay.isEmpty()) {
			aboutThisDay.put(stepDay, new ArrayList<>());
		} else {
			aboutThisDay.put(stepDay, thisDay);
		}

		allMonth.put(week, aboutThisDay);
	}
}
