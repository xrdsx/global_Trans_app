package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.*;
import com.managment.global_Trans_App.repository.*;
import com.managment.global_Trans_App.service.ReportedIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
public class ReportedIssueController {

    @Autowired
    private ReportedIssueRepository reportedIssueRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DayWorkRepository dayWorkRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ForwarderRepository forwarderRepository;

    @Autowired
    private ReportedIssueService reportedIssueService;



    @GetMapping("/reportedIssues")
    public String showReportedIssues(Model model) {
        List<ReportedIssue> reportedIssues = reportedIssueService.findAll();
        model.addAttribute("reportedIssues", reportedIssues);
        return "reportedIssues";
    }

    @GetMapping("/issue_details")
    public String showIssueDetails(@RequestParam("id") Long issueId, Model model) {
        ReportedIssue issue = reportedIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("ReportedIssue not found!"));

        model.addAttribute("issue", issue);
        return "issue-details";
    }



    @GetMapping("/reportIssue")
    public String showReportIssueForm(Model model) {

        model.addAttribute("issue", new ReportedIssue());
        return "report_issue";
    }

    @GetMapping("/reportIssueForDayWork")
    public String showReportIssueFormForDayWork(@RequestParam("dayWorkId") Integer dayWorkId, Model model) {
        DayWork dayWork = dayWorkRepository.findById(dayWorkId).orElseThrow(() -> new RuntimeException("DayWork not found!"));
        ReportedIssue issue = new ReportedIssue();
        issue.setRoute(dayWork.getRoute());
        issue.setDriver(dayWork.getDriver());
        issue.setVehicle(dayWork.getVehicle());

        model.addAttribute("issue", issue);
        model.addAttribute("dayWork", dayWork);
        return "report_issue";
    }

    @PostMapping("/submitIssue")
    public String submitIssue(@ModelAttribute ReportedIssue issue, @RequestParam("dayWorkId") Integer dayWorkId) {
        issue.setCreatedAt(LocalDateTime.now());

        DayWork dayWork = dayWorkRepository.findById(dayWorkId).orElseThrow(() -> new RuntimeException("DayWork not found!"));

        // Zaktualizuj pola driver, driverPhoneNumber, vehicle, route na podstawie przypisanego DayWork
        Driver driver = dayWork.getDriver();
        issue.setDriver(driver);
        issue.setDriverPhoneNumber(driver.getPhoneNumber());
        issue.setVehicle(dayWork.getVehicle());
        issue.setVehicleRegistrationNumber(dayWork.getVehicle().getRegistrationNumber());
        issue.setRoute(dayWork.getRoute());



        // Zapisz zgłoszenie do bazy danych
        reportedIssueRepository.save(issue);
        sendEmailToAllForwarders(issue);

        // Przekieruj użytkownika do strony potwierdzającej wysłanie zgłoszenia
        return "issue_submitted";
    }

    private void sendEmailToUser(ReportedIssue issue, Forwarder forwarder) {
        String subject = "ZGŁOSZENIE #" + issue.getId() + " - " + issue.getDescription();
        String message = "\n\n=========================================\n" +
                "ID ZGŁOSZENIA: " + issue.getId() + "\n\n" +
                "OPIS: " + issue.getDescription() + "\n\n" +
                "KIEROWCA: " + issue.getDriver().getFirstName() +" "+issue.getDriver().getLastName() + "\n\n" +
                "NUMER REJESTRACYJNY: " + issue.getVehicleRegistrationNumber() + "\n\n" +
                "TRASA: " + issue.getRoute().getNameRoute() + "\n\n" +
                "DATA: " + issue.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + "\n\n" +
                "TELEFON KIEROWCY: " + issue.getDriverPhoneNumber() + "\n\n" +
                "=========================================\n\n" +
                "LINK DO MAPY: " + issue.getLocation() + "\n\n";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(forwarder.getEmail());


        mail.setSubject(subject);
        mail.setText(message);
        mailSender.send(mail);
    }

    private void sendEmailToAllForwarders(ReportedIssue issue) {
        List<Forwarder> forwarders = forwarderRepository.findAll();
        for (Forwarder forwarder : forwarders) {
            sendEmailToUser(issue, forwarder);
        }
    }

    @PostMapping("/updateIssue")
    public String updateIssue(@RequestParam("issueId") Long issueId, @ModelAttribute ReportedIssue updatedIssue) {
        ReportedIssue issue = reportedIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("ReportedIssue not found!"));

        // Aktualizuj pola na podstawie updatedIssue

        issue.setType(updatedIssue.getType());
        issue.setPriority(updatedIssue.getPriority());

        // Zapisz zaktualizowane zgłoszenie do bazy danych
        reportedIssueRepository.save(issue);

        // Przekieruj użytkownika do strony potwierdzającej aktualizację zgłoszenia
        return "redirect:/reportedIssues";
    }



}