package com.example.medcheckb7.dto.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import com.example.medcheckb7.dto.response.ExpertResponseForUser;
import com.example.medcheckb7.dto.response.ScheduleDateAndTimeResponse;
import com.example.medcheckb7.dto.response.ScheduleResponseForSearch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;

public class ExcelWriter {

    public static void writeExpertsSchedule(List<ScheduleResponseForSearch> schedules, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Expert Schedule");

            // Create header row
            Row headerRow = sheet.createRow(0);
            Cell expertHeader = headerRow.createCell(0);
            expertHeader.setCellValue("Experts");
            expertHeader.setCellStyle(createHeaderStyle(workbook));
            for (int i = 1; i < 8; i++) {
                Cell dayHeader = headerRow.createCell(i);
                dayHeader.setCellValue(DayOfWeek.of(i).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()));
                dayHeader.setCellStyle(createHeaderStyle(workbook));
            }

            // Populate expert rows
            int rowNum = 1;
            for (ScheduleResponseForSearch schedule : schedules) {
                Row row = sheet.createRow(rowNum++);
                Cell expertCell = row.createCell(0);
                ExpertResponseForUser expert = schedule.getExpert();
                expertCell.setCellValue(expert.getExpertFirstName() + " " + expert.getExpertLastName() + " - " + expert.getExpertPosition());
                expertCell.setCellStyle(createCellStyle(workbook));
                List<ScheduleDateAndTimeResponse> dateAndTimeResponses = schedule.getScheduleDateAndTimeResponse();
                int colNum = 1;
                for (int i = 1; i < 8; i++) {
                    LocalDate date = LocalDate.now().with(DayOfWeek.of(i));
                    String dateString = date.format(ScheduleDateAndTimeResponse.DATE_FORMATTER);
                    Cell cell = row.createCell(colNum++);
                    for (ScheduleDateAndTimeResponse dateAndTimeResponse : dateAndTimeResponses) {
                        if (dateString.equals(dateAndTimeResponse.getDate())) {
                            cell.setCellValue(dateAndTimeResponse.getDate());
                            break;
                        }
                    }
                    cell.setCellStyle(createCellStyle(workbook));
                }
            }

            // Adjust column widths
            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}

