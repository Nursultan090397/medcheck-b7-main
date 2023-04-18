package com.example.medcheckb7.converter;


import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.Schedule;
import com.example.medcheckb7.db.entities.ScheduleDateAndTime;
import com.example.medcheckb7.dto.response.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleResponseConverter {
    static ExpertResponseForUser getExpertResponsesForUser(Expert expert) {
        ExpertResponseForUser response = new ExpertResponseForUser();
        response.setId(expert.getId());
        response.setExpertFirstName(expert.getExpertFirstName());
        response.setExpertLastName(expert.getExpertLastName());
        response.setExpertPosition(expert.getExpertPosition());
        response.setExpertImage(expert.getExpertImage());
        return response;
    }

    static ScheduleDateAndTimeResponse getDateAndTime(ScheduleDateAndTime scheduleDateAndTime) {
        ScheduleDateAndTimeResponse response = new ScheduleDateAndTimeResponse();
        response.setStartTime(scheduleDateAndTime.getStartTime());
        response.setFinishTime(scheduleDateAndTime.getFinishTime());
        response.setDate(scheduleDateAndTime.getDate());
        return response;
    }

    public ScheduleResponseForExpert viewScheduleByExpertId(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        ScheduleResponseForExpert scheduleResponseForExpert = new ScheduleResponseForExpert();
        scheduleResponseForExpert.setDate(schedule.getFinishDate().atStartOfDay());
        return scheduleResponseForExpert;
    }

    public List<ScheduleResponseForExpert> viewByExpertId(List<Schedule> schedules) {
        List<ScheduleResponseForExpert> scheduleResponseForExperts = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleResponseForExperts.add(viewScheduleByExpertId(schedule));
        }
        return scheduleResponseForExperts;
    }

    public ScheduleResponse viewForAdmin(Schedule schedule) {
        if (schedule == null) {
            return null;
        }

        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setStartDate(schedule.getStartDate());
        scheduleResponse.setFinishDate(schedule.getFinishDate());
        scheduleResponse.setClockInterval(schedule.getClockInterval());
        scheduleResponse.setRecurrenceDays(schedule.getRecurrenceDays());
        scheduleResponse.setRepeatType(schedule.getRepeatType());

        List<ScheduleDateAndTime> scheduleDateAndTimes = new ArrayList<>(schedule.getDateAndTimes());

        if (!scheduleDateAndTimes.isEmpty()) {
            ScheduleDateAndTime firstDateAndTime = scheduleDateAndTimes.get(0);
            ScheduleDateAndTime lastDateAndTime = scheduleDateAndTimes.get(scheduleDateAndTimes.size() - 1);

            scheduleResponse.setStartTime(firstDateAndTime.getStartTime());
            scheduleResponse.setFinishTime(lastDateAndTime.getFinishTime());
            scheduleResponse.setTimeStatus(false);
        }
        scheduleResponse.setClinicId(schedule.getClinicService().getId());
        scheduleResponse.setExpertId(schedule.getExpert().getId());

        return scheduleResponse;
    }


    public ScheduleResponseForSearch viewScheduleSearch(Expert expert) {
        Schedule schedule = expert.getSchedule();
        if (schedule == null) {
            ScheduleResponseForSearch scheduleResponseForSearch = new ScheduleResponseForSearch();
            scheduleResponseForSearch.setExpert(getExpertResponsesForUser(expert));
            return scheduleResponseForSearch;
        }
        ScheduleResponseForSearch scheduleResponseForSearch = new ScheduleResponseForSearch();
        scheduleResponseForSearch.setId(schedule.getId());
        scheduleResponseForSearch.setExpert(getExpertResponsesForUser(schedule.getExpert()));

        List<ScheduleDateAndTimeResponse> dateAndTimeResponses = new ArrayList<>();
        for (ScheduleDateAndTime scheduleDateAndTime : schedule.getDateAndTimes()) {
            dateAndTimeResponses.add(getDateAndTime(scheduleDateAndTime));
        }

        Map<LocalDate, ScheduleDateAndTimeResponse> map = new HashMap<>();
        for (ScheduleDateAndTimeResponse element : dateAndTimeResponses) {
            LocalDate date = element.getDate();
            if (map.containsKey(date)) {
                ScheduleDateAndTimeResponse existing = map.get(date);
                LocalTime start = element.getStartTime().isBefore(existing.getStartTime())
                        ? element.getStartTime() : existing.getStartTime();
                LocalTime finish = element.getFinishTime().isAfter(existing.getFinishTime())
                        ? element.getFinishTime() : existing.getFinishTime();
                existing.setStartTime(start);
                existing.setFinishTime(finish);
            } else {
                ScheduleDateAndTimeResponse newElement = new ScheduleDateAndTimeResponse();
                newElement.setDate(date);
                newElement.setStartTime(element.getStartTime());
                newElement.setFinishTime(element.getFinishTime());
                map.put(date, newElement);
            }
        }

        List<ScheduleDateAndTimeResponse> uniqueDates = new ArrayList<>(map.values());
        scheduleResponseForSearch.setScheduleDateAndTimeResponse(uniqueDates);
        return scheduleResponseForSearch;
    }


}

