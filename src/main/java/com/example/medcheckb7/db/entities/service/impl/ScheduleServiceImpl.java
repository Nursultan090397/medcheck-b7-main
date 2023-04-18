package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.converter.ScheduleRequestConverter;
import com.example.medcheckb7.converter.ScheduleResponseConverter;
import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.db.entities.Schedule;
import com.example.medcheckb7.db.repository.ExpertRepository;
import com.example.medcheckb7.db.entities.service.ScheduleService;
import com.example.medcheckb7.db.entities.ScheduleDateAndTime;
import com.example.medcheckb7.db.repository.ScheduleDateAndTimeRepository;
import com.example.medcheckb7.db.repository.ScheduleRepository;
import com.example.medcheckb7.dto.request.ScheduleRequest;
import com.example.medcheckb7.dto.request.ScheduleUpdateRequest;
import com.example.medcheckb7.dto.response.*;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ExpertRepository expertRepository;
    private final ScheduleResponseConverter scheduleResponseConverter;
    private final ScheduleRequestConverter scheduleRequestConverter;
    private final ScheduleDateAndTimeRepository scheduleDateAndTimeRepository;

    @Override
    public ScheduleResponse addSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRequestConverter.addSchedule(scheduleRequest);
        scheduleRepository.save(schedule);
        return scheduleResponseConverter.viewForAdmin(schedule);
    }

    @Override
    public List<ScheduleResponseForSearch> searchAndFetchAllSchedules(String expertFName) throws NotFoundException {
        List<Expert> schedules;
        if (expertFName == null || expertFName.isEmpty()) {
            schedules = expertRepository.findAll();
        } else {
            schedules = expertRepository.search(expertFName);
            if (schedules == null || schedules.isEmpty()) {
                throw new NotFoundException("Experts not found");
            }
        }
        List<ScheduleResponseForSearch> scheduleResponses = new ArrayList<>();
        for (Expert schedule : schedules) {
            ScheduleResponseForSearch scheduleResponse = scheduleResponseConverter.viewScheduleSearch(schedule);
            scheduleResponses.add(scheduleResponse);
        }
        return scheduleResponses;
    }

    @Transactional
    @Override
    public byte[] patientExportExcel(String expertFName) throws IOException {

        List<ScheduleResponseForSearch> schedules = searchAndFetchAllSchedules(expertFName);

        Workbook sheets = getExcelFilePatient(schedules);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        sheets.write(stream);

        return stream.toByteArray();
    }

    private Workbook getExcelFilePatient(List<ScheduleResponseForSearch> schedules) {
        try {
            Workbook workbook = new XSSFWorkbook();
            LocalDate date = LocalDate.now();
            LocalDate beforeMonth = LocalDate.now();
            LocalDate afterMonth = date.plusMonths(1);
            String formatDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            Sheet sheet = workbook.createSheet(formatDate);
            sheet.setColumnWidth(0, 20 * 450);
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Специалисты");
            for (int i = 1; beforeMonth.isBefore(afterMonth); beforeMonth = beforeMonth.plusDays(1), i++) {
                sheet.setColumnWidth(i, 20 * 170);
                header.createCell(i).setCellValue(beforeMonth.getDayOfMonth() + " " + beforeMonth.getMonth());
            }
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeight((short) 14);
            headerFont.setColor(IndexedColors.GREEN.getIndex());
            int rowNum = 1;
            for (ScheduleResponseForSearch schedule : schedules) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(schedule.getExpert().getExpertFirstName() + " " + schedule.getExpert().getExpertLastName() + " (" + schedule.getExpert().getExpertPosition().toUpperCase() + ")");
                for (ScheduleDateAndTimeResponse time : schedule.getScheduleDateAndTimeResponse()) {
                    if (time.getDate().isAfter(date) || time.getDate().isEqual(date) && time.getDate().isBefore(afterMonth) || time.getDate().isEqual(date)) {
                        for (int i = 1; i < header.getLastCellNum(); i++) {
                            if (header.getCell(i).getStringCellValue().equals(time.getDate().getDayOfMonth() + " " + time.getDate().getMonth())) {
                                row.createCell(i).setCellValue(time.toString());
                            }
                        }
                    }
                }
            }
            return workbook;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public SimpleResponse updateSchedule(ScheduleUpdateRequest scheduleUpdateRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleUpdateRequest.getId()).orElseThrow(() -> new NotFoundException("Schedule not Found"));

        List<ScheduleDateAndTime> scheduleDateAndTimeList = new ArrayList<>();

        for (int i = 0, j = 1; j <= scheduleUpdateRequest.getIntervals().size() - 1; i += 2, j += 2) {
            ScheduleDateAndTime scheduleDateAndTime = new ScheduleDateAndTime(schedule.getStartDate(), scheduleUpdateRequest.getIntervals().get(i), scheduleUpdateRequest.getIntervals().get(j), false, schedule);
            scheduleDateAndTimeList.add(scheduleDateAndTime);
        }
        schedule.setDateAndTimes(scheduleDateAndTimeList);
        scheduleRepository.save(schedule);
        return new SimpleResponse("schedule successfully updated");
    }

    @Override
    public SimpleResponse delete(List<Long> ids) {
        for (Long id : ids) {
            if (scheduleDateAndTimeRepository.findById(id).isEmpty()) {
                throw new NotFoundException("Dates not found with ID: " + id);
            }
        }
        scheduleDateAndTimeRepository.deleteSelectedSchedules(ids);
        return new SimpleResponse("Deleted successfully!");
    }
}






