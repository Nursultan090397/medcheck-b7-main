package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.ClinicService;
import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.Schedule;
import com.example.medcheckb7.db.entities.ScheduleDateAndTime;
import com.example.medcheckb7.db.repository.ClinicServiceRepository;
import com.example.medcheckb7.db.repository.ExpertRepository;
import com.example.medcheckb7.db.repository.ScheduleRepository;
import com.example.medcheckb7.dto.request.ScheduleRequest;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleRequestConverter {
    private final ClinicServiceRepository clinicServiceRepository;
    private final ExpertRepository expertRepository;
    private final ScheduleRepository scheduleRepository;

    public Schedule addSchedule(ScheduleRequest scheduleRequest) {
        if (scheduleRequest == null) {
            return null;
        }

        List<Schedule> schedules = scheduleRepository.findAll();

        schedules.forEach(x -> {
            if (x.getExpert().getId() == scheduleRequest.getExpertId() && x.getClinicService().getId() == scheduleRequest.getClinicId()) {
                throw new BadRequestException("This expert already have schedule");
            }
        });

        Schedule schedule = new Schedule();
        schedule.setRecurrenceDays(scheduleRequest.getRecurrenceDays());

        LocalDate startDate = scheduleRequest.getStartDate();
        LocalDate finishDate = scheduleRequest.getFinishDate();
        List<LocalDate> dates = new ArrayList<>();
        while (!startDate.isAfter(finishDate)) {
            dates.add(startDate);
            startDate = startDate.plusDays(1);
        }

        List<ScheduleDateAndTime> scheduleDateAndTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.parse(scheduleRequest.getStartTime());
        LocalTime finishTime = LocalTime.parse(scheduleRequest.getFinishTime());
        long clockInterval = scheduleRequest.getClockInterval();
        for (LocalDate date : dates) {
            schedule.getRecurrenceDays().forEach((key, value) -> {
                int indexOfWeek = 0;
                switch (key) {
                    case "пн" -> indexOfWeek = 1;
                    case "вт" -> indexOfWeek = 2;
                    case "ср" -> indexOfWeek = 3;
                    case "чт" -> indexOfWeek = 4;
                    case "пт" -> indexOfWeek = 5;
                    case "сб" -> indexOfWeek = 6;
                    case "вс" -> indexOfWeek = 7;
                }
                if (date.getDayOfWeek().getValue() == indexOfWeek) {
                    if (value) {
                        LocalTime time = startTime;
                        while (!time.plusMinutes(clockInterval).isAfter(finishTime)) {
                            ScheduleDateAndTime scheduleDateAndTime = new ScheduleDateAndTime();
                            scheduleDateAndTime.setStartTime(time);
                            time = time.plusMinutes(clockInterval);
                            scheduleDateAndTime.setFinishTime(time);
                            scheduleDateAndTime.setTimeStatus(false);
                            scheduleDateAndTime.setSchedule(schedule);
                            scheduleDateAndTime.setDate(date);
                            scheduleDateAndTimes.add(scheduleDateAndTime);
                        }
                    }
                }
            });
        }

        schedule.setDateAndTimes(scheduleDateAndTimes);
        schedule.setStartDate(scheduleRequest.getStartDate());
        schedule.setFinishDate(scheduleRequest.getFinishDate());
        schedule.setClockInterval(scheduleRequest.getClockInterval());
        schedule.setRepeatType(scheduleRequest.getRepeatType());


        Expert expert = expertRepository.findById(scheduleRequest.getExpertId())
                .orElseThrow(() -> new NotFoundException("Expert not found"));
        ClinicService clinicService = clinicServiceRepository.findById(scheduleRequest.getClinicId())
                .orElseThrow(() -> new NotFoundException("Click service not found!"));
        if (!clinicService.getExperts().contains(expert)) {
            throw new BadRequestException("Expert not contains in clinic service");
        }
        expert.setExpertTimeTable(String.valueOf((scheduleRequest.getFinishDate())));
        schedule.setExpert(expert);
        schedule.setClinicService(clinicService);
        return schedule;
    }
}