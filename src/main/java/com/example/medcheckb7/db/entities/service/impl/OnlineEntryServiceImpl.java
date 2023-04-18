package com.example.medcheckb7.db.entities.service.impl;

import com.example.medcheckb7.converter.AppointmentsAdminResponseConverter;
import com.example.medcheckb7.converter.OnlineEntryResponseConverter;
import com.example.medcheckb7.db.entities.*;
import com.example.medcheckb7.db.entities.service.OnlineEntryService;
import com.example.medcheckb7.db.repository.*;
import com.example.medcheckb7.dto.request.OnlineEntryRequest;
import com.example.medcheckb7.dto.response.*;
import com.example.medcheckb7.exceptions.BadRequestException;
import com.example.medcheckb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnlineEntryServiceImpl implements OnlineEntryService {
    private final ExpertRepository expertRepository;
    private final ClinicServiceRepository clinicServiceRepository;
    private final UserRepository userRepository;
    private final OnlineEntryRepository onlineEntryRepository;
    private final ScheduleDateAndTimeRepository scheduleDateAndTimeRepository;
    private final OnlineEntryResponseConverter responseConverter;
    private final AppointmentsAdminResponseConverter appointmentsAdminResponseConverter;

    @Override
    public List<ClinicServiceResponse> getAllClinicService() {
        return clinicServiceRepository.findAll().stream().map(clinic -> new ClinicServiceResponse(clinic.getId(), clinic.getClinicServiceName())).collect(Collectors.toList());
    }

    @Override
    public List<ExpertResponseOnlineEntry> getAllExpertByClinicId(Long clinicServiceId) {
        ClinicService clinicService = clinicServiceRepository.findById(clinicServiceId)
                .orElseThrow(() -> new NotFoundException("Not found service!!!"));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Almaty"));
        LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Almaty"));
        List<ExpertResponseOnlineEntry> responseList = new ArrayList<>();

        for (Expert expert : clinicService.getExperts()) {
            if (expert.getExpertStatus() == false) {
                continue;
            }

            ExpertResponseOnlineEntry expertEntry = new ExpertResponseOnlineEntry();
            expertEntry.setId(expert.getId());
            expertEntry.setExpertFirstName(expert.getExpertFirstName());
            expertEntry.setExpertLastName(expert.getExpertLastName());
            expertEntry.setExpertPosition(expert.getExpertPosition());
            expertEntry.setExpertImage(expert.getExpertImage());

            if (expert.getSchedule() == null || expert.getSchedule().getDateAndTimes() == null || expert.getSchedule().getDateAndTimes().isEmpty()) {
                expertEntry.setDate(null);
                expertEntry.setStarTime(new ArrayList<LocalTime>());
            } else {
                List<LocalTime> startTimes = new ArrayList<>();
                for (ScheduleDateAndTime dateAndTime : expert.getSchedule().getDateAndTimes()) {
                    LocalDate date = dateAndTime.getDate();
                    LocalTime startTime = dateAndTime.getStartTime();
                    if (date.isEqual(today) && !dateAndTime.getTimeStatus() && (startTime.isAfter(currentTime) || startTime.equals(currentTime))) {
                        startTimes.add(startTime);
                        expertEntry.setDate(date);
                    }
                }
                expertEntry.setStarTime(startTimes);
            }
            responseList.add(expertEntry);
        }
        return responseList;
    }


    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User not found!!!"));
    }

    @Override
    public OnlineEntryResponse saveOnlineEntry(OnlineEntryRequest onlineEntryRequest) {
        User user = getAuthenticateUser();
        Expert expert = expertRepository.findById(onlineEntryRequest.getExpertId())
                .orElseThrow(() -> new NotFoundException("Expert not found"));
        ClinicService clinicService = clinicServiceRepository.findById(onlineEntryRequest.getServiceId())
                .orElseThrow(() -> new NotFoundException("Click service not found!"));
        if (!clinicService.getExperts().contains(expert)) {
            throw new BadRequestException("Expert not contains in clinic service");
        }
        LocalDate date = onlineEntryRequest.getDate();
        LocalTime time = LocalTime.parse(onlineEntryRequest.getTime());
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Almaty"));
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Almaty"));
        if (date.isBefore(today) || (date.equals(today) && time.isBefore(now))) {
            throw new BadRequestException("Selected date and time have already passed!");
        }

        ScheduleDateAndTime dateAndTime = expert.getSchedule().getDateAndTimes().stream().filter(dt -> dt.getDate().equals(date) && dt.getStartTime().equals(time)).findFirst().orElseThrow(() -> new BadRequestException("This expert does not have such schedule!!!!!!!!!"));

        String formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM"));
        if (dateAndTime.getTimeStatus()) {
            throw new BadRequestException("This schedule already exists!");
        }

        if (expert.getExpertStatus() == false) {
            throw new BadRequestException("This expert is not available for appointments");
        }

        OnlineEntry onlineEntry = new OnlineEntry();
        onlineEntry.setOnlineEntryStatus("Подвержден");
        onlineEntry.setUser(user);
        onlineEntry.setExpert(expert);
        onlineEntry.setClinicService(clinicService);
        onlineEntry.setRecordedDate(dateAndTime.getDate());
        onlineEntry.setRecordedTime(dateAndTime.getStartTime());

        if (onlineEntryRequest.getUserName().isEmpty()) {
            onlineEntry.setUserName(user.getUserFirstName() + " " + user.getUserLastName());
        } else {
            String[] fullName = onlineEntryRequest.getUserName().split(" ");
            if (fullName.length < 2) {
                throw new BadRequestException("Please enter your full name and surname separated by a space");
            } else {
                onlineEntry.setUserName(onlineEntryRequest.getUserName().isEmpty() ? user.getUserFirstName() + " " + user.getUserLastName() : onlineEntryRequest.getUserName());

            }
        }

        onlineEntry.setUserEmail(onlineEntryRequest.getUserEmail().isEmpty() ? user.getUserEmail() : onlineEntryRequest.getUserEmail());

        String phoneNumber = onlineEntryRequest.getUserPhoneNumber();
        if (phoneNumber.isEmpty()) {
            onlineEntry.setUserPhoneNumber(user.getUserPhoneNumber());
        } else {
            if (!isValid(phoneNumber, null)) {
                throw new BadRequestException("Invalid phone number format");
            }
            onlineEntry.setUserPhoneNumber(phoneNumber);
        }

        dateAndTime.setTimeStatus(true);
        onlineEntry.setIsDeleted(true);
        userRepository.save(user);
        expertRepository.save(expert);
        clinicServiceRepository.save(clinicService);
        scheduleDateAndTimeRepository.save(dateAndTime);
        onlineEntryRepository.save(onlineEntry);
        return new OnlineEntryResponse("Вы успешно записаны", formattedDate,
                dateAndTime.getStartTime(), dateAndTime.getFinishTime(),
                new ExpertAppointmentsResponse(expert.getId(), expert.getExpertFirstName(), expert.getExpertLastName(),
                        expert.getExpertPosition(), expert.getExpertImage()));
    }

    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            User user = getAuthenticateUser();
            phoneNumber = user.getUserPhoneNumber();
        }

        if (phoneNumber.length() != 13 || !phoneNumber.startsWith("+996")) {
            throw new BadRequestException("the number must start with +996 or contain 13 digits");
        }

        String mnc = phoneNumber.substring(4, 7);
        String subscriberNumber = phoneNumber.substring(7);

        if (Arrays.asList("500", "501", "502", "504", "505", "507", "508", "509", "700", "701", "702", "703", "704", "705", "706", "707", "708", "709",
                "770", "771", "772", "773", "774", "775", "776", "777", "778", "779", "220", "221", "222", "223", "224", "225", "227",
                "999", "755", "550", "551", "552", "553", "554", "555", "556", "557", "559").contains(mnc)) {
            return subscriberNumber.matches("^[0-9/-/+]{6,10}$");
        } else {
            throw new BadRequestException("The number code does not match the Kyrgyz number");
        }
    }

    @Override
    public SimpleResponse cancelEntry(Long onlineEntryId) {
        OnlineEntry onlineEntry = onlineEntryRepository.findById(onlineEntryId).orElseThrow(() -> new NotFoundException("Online entry not found: id = " + onlineEntryId));

        Expert expert = onlineEntry.getExpert();
        List<ScheduleDateAndTime> dateAndTimes = expert.getSchedule().getDateAndTimes();

        ScheduleDateAndTime scheduleDateAndTime = dateAndTimes.stream().filter(x -> x.getDate().equals(onlineEntry.getRecordedDate())
                && x.getStartTime().equals(onlineEntry.getRecordedTime())).findFirst().orElseThrow(()
                -> new NotFoundException("Schedule date and time not found for online entry: id = " + onlineEntryId));

        scheduleDateAndTime.setTimeStatus(false);
        onlineEntry.setOnlineEntryStatus("Cancel");

        scheduleDateAndTimeRepository.save(scheduleDateAndTime);
        onlineEntryRepository.save(onlineEntry);

        return new SimpleResponse("Online entry successfully canceled.");
    }


    @Override
    public List<ScheduleAndTimeResponse> getTimesToEntry(Long expertId, LocalDate localDate) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NotFoundException("Expert not found"));
        if (expert.getSchedule() == null) {
            throw new BadRequestException("Expert doesn't have schedule");
        }

        Schedule schedule = expert.getSchedule();

        if (localDate.isEqual(schedule.getStartDate()) || localDate.isAfter(schedule.getStartDate()) && localDate.isEqual(schedule.getFinishDate()) || localDate.isBefore(schedule.getFinishDate())) {
            List<ScheduleAndTimeResponse> response = new ArrayList<>();
            List<ScheduleDateAndTime> times = scheduleDateAndTimeRepository.getAllEntry(expertId, localDate);

            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
            times.forEach(x -> {
                if (x.getStartTime().atDate(localDate).isBefore(now)) {
                    x.setTimeStatus(true);
                    scheduleDateAndTimeRepository.save(x);
                }
                response.add(new ScheduleAndTimeResponse(x.getId(), x.getTimeStatus(), x.getStartTime()));
            });

            return response;
        } else {
            throw new BadRequestException("Invalid date");
        }
    }

    private List<OnlineEntry> search(String name) {
        String text = name == null ? "" : name;
        return onlineEntryRepository.searchAppointments(text.toUpperCase());
    }


    @Override
    public List<AppointmentsAdminResponse> getAll(String text) {
        List<AppointmentsAdminResponse> appointmentsAdminResponse = new ArrayList<>();
        if (text != null) {
            List<OnlineEntry> onlineEntries = search(text);
            for (OnlineEntry onlineEntry : onlineEntries) {
                if (!onlineEntry.getOnlineEntryStatus().equals("Cancel")) {
                    appointmentsAdminResponse.add(appointmentsAdminResponseConverter.viewApp(onlineEntry));
                }
            }
        } else {
            List<OnlineEntry> onlineEntries = onlineEntryRepository.findDeletedEntries();
            for (OnlineEntry onlineEntry : onlineEntries) {
                if (!onlineEntry.getOnlineEntryStatus().equals("Cancel")) {
                    appointmentsAdminResponse.add(appointmentsAdminResponseConverter.viewApp(onlineEntry));
                }
            }
        }
        return appointmentsAdminResponse;
    }

    @Override
    public SimpleResponse deleteAppointments(List<Long> ids) {
        for (Long id : ids) {
            Optional<OnlineEntry> entryOptional = onlineEntryRepository.findById(id);
            if (entryOptional.isEmpty()) {
                throw new NotFoundException("Application not found with ID: " + id);
            }
            OnlineEntry entry = entryOptional.get();
            if (!entry.getIsDeleted()) {
                throw new BadRequestException("Application with ID " + id + " has already been not deleted");
            }
            entry.setIsDeleted(false);
            onlineEntryRepository.save(entry);
        }
        return new SimpleResponse("Deleted successfully");
    }

    @Override
    public List<OnlineEntryResponseForUser> getOnlineEntriesByUserId(User authentication) {
        Long userId = authentication.getId();
        List<OnlineEntry> onlineEntries = onlineEntryRepository.findByUserId(userId);
        List<OnlineEntryResponseForUser> onlineEntryResponses = new ArrayList<>();
        for (OnlineEntry onlineEntry : onlineEntries) {
            OnlineEntryResponseForUser onlineEntryResponseDto = responseConverter.toResponse(onlineEntry);
            onlineEntryResponses.add(onlineEntryResponseDto);
        }
        return onlineEntryResponses;
    }

    @Override
    public OnlineEntryResponses getOnlineEntryWithUser(User authentication, Long id) {
        Long userId = authentication.getId();
        List<OnlineEntry> onlineEntries = onlineEntryRepository.findByUserId(userId);
        List<OnlineEntryResponses> onlineEntryResponses = new ArrayList<>();
        for (OnlineEntry onlineEntry : onlineEntries) {
            OnlineEntryResponses onlineEntryResponseDto = responseConverter.toResponses(onlineEntry);
            onlineEntryResponses.add(onlineEntryResponseDto);
        }
        if (id > onlineEntryResponses.size()) {
            throw new NotFoundException("Online Entry with this id not exists!");
        }
        return onlineEntryResponses.get(Math.toIntExact(id - 1));
    }

    @Override
    public SimpleResponse deleteAll(User authentication) {
        Long userId = authentication.getId();
        onlineEntryRepository.deleteOnlineEntriesBy(userId);
        return new SimpleResponse("Cleared successfully!");
    }
}
