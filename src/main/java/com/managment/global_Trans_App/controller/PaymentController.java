package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.WorkDayBilling;
import com.managment.global_Trans_App.repository.DriverRepository;
import com.managment.global_Trans_App.repository.WorkDayBillingRepository;
import com.managment.global_Trans_App.service.DriverService;
import com.managment.global_Trans_App.service.WorkDayBillingService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.http.HttpHeaders;

@Controller
public class PaymentController {

    @Autowired
    private WorkDayBillingService workDayBillingService;

    @Autowired
    private DriverService driverService;

    @GetMapping("/payment-management")
    public String showPaymentManagement(Model model) {
        model.addAttribute("workDayBillings", workDayBillingService.findAll());
        return "payment-management";
    }
    @PostMapping("/payment-management")
    public String submitPayment(@ModelAttribute WorkDayBilling workDayBilling) {
        workDayBillingService.save(workDayBilling);
        return "redirect:/payment-management";
    }
    @GetMapping("/generate-billing")
    public String showGenerateBilling(Model model) {
        // Pobierz listę kierowców i dodaj ją do modelu (zakładając, że masz już DriverService)
        model.addAttribute("drivers", driverService.findAll());
        return "generate-billing";
    }

    @PostMapping("/generate-billing")
    public String processGenerateBilling(@RequestParam("driverId") Long driverId, @RequestParam("dateFrom") LocalDate dateFrom, @RequestParam("dateTo") LocalDate dateTo) {
        // Przetwórz żądanie generowania rachunków, na przykład zapisując dane w bazie danych lub generując plik PDF

        // Przekieruj na stronę z potwierdzeniem lub inną stronę po zakończeniu generowania rachunków
        return "redirect:/payment-management";
    }

    @GetMapping("/generate-billing-report")
    public ResponseEntity<?> generateBillingReport(
            @RequestParam("driverId") Long driverId,
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            HttpServletResponse response) throws IOException {



        List<Driver> drivers;
        String zipFileName;
        if (driverId == -1) {
            drivers = driverService.findAll();
            zipFileName = "Wypłaty_" + dateFrom.toString() + "_" + dateTo.toString() + ".zip";
        } else {
            Driver driver = driverService.findById(driverId);
            drivers = Collections.singletonList(driver);
            zipFileName = "Rachunek_" + driver.getFirstName() + "_" + driver.getLastName() + "_" + dateFrom.toString() + "_" + dateTo.toString() + ".zip";
        }

        List<File> files = new ArrayList<>();

        try (XWPFDocument document = new XWPFDocument()) {
            // Dodaj tytuł
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = title.createRun();
            run.setBold(true);
            run.setFontSize(20);
            run.setText("Raport wypłat");

            // Dodaj rubryczkę numeru konta bankowego
            XWPFParagraph accountNumber = document.createParagraph();
            accountNumber.setAlignment(ParagraphAlignment.RIGHT);
            run = accountNumber.createRun();
            run.setText("Numer konta bankowego:                   ");

            for (Driver driver : drivers) {
                BigDecimal totalKm = workDayBillingService.getTotalKm(driver.getId(), dateFrom, dateTo);
                BigDecimal bonusPayment = BigDecimal.ZERO;

                if (driver.getHourlyRate() != null) {
                    bonusPayment = driver.getHourlyRate().multiply(totalKm);
                }

                BigDecimal payout = driver.getBasicPayout().add(bonusPayment);

                // Dodaj dane o kierowcy
                XWPFParagraph driverInfo = document.createParagraph();
                run = driverInfo.createRun();
                run.setText("Kierowca: " + driver.getFirstName() + " " + driver.getLastName());

                // Dodaj daty
                XWPFParagraph dates = document.createParagraph();
                run = dates.createRun();
                run.setText("Od: " + dateFrom + "   Do: " + dateTo);

                // Dodaj sumę przejechanych kilometrów
                XWPFParagraph km = document.createParagraph();
                run = km.createRun();
                run.setText("Przejechane kilometry: " + totalKm);

                // Dodaj wypłatę podstawową
                XWPFParagraph basicPayout = document.createParagraph();
                run = basicPayout.createRun();
                run.setText("Wypłata podstawowa: " + driver.getBasicPayout());

                // Dodaj premię za przejechane kilometry
                XWPFParagraph bonus = document.createParagraph();
                run = bonus.createRun();
                run.setText("Premia za przejechane kilometry: " + bonusPayment);

                // Dodaj całkowitą wypłatę
                XWPFParagraph totalPayout = document.createParagraph();
                run = totalPayout.createRun();
                run.setText("Całkowita wypłata: " + payout);

                // Dodaj miejsce na podpisy
                XWPFParagraph signatures = document.createParagraph();
                signatures.setAlignment(ParagraphAlignment.CENTER);
                run = signatures.createRun();
                run.setText("..........................................  ..........................................");
                run.addBreak();
                run.setText("Podpis kierowcy                                     Podpis przełożonego");

                // Zapisz dokument do pliku
                String fileName = "Rachunek_" + driver.getFirstName() + "_" + driver.getLastName() + "_" + dateFrom.toString() + "_" + dateTo.toString() + ".docx";
                File file = new File(fileName);
                document.write(new FileOutputStream(file));
                files.add(file);
            }
        }
        // Tworzenie archiwum ZIP
        String zipFilePath = "C:/TEST/" + zipFileName;
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
        byte[] buffer = new byte[1024];

        for (File file : files) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fileInputStream = new FileInputStream(file);
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            fileInputStream.close();
            file.delete(); // Usuń plik docx po dodaniu do archiwum
        }

        zipOutputStream.close();

        // Utwórz odpowiedź z plikiem ZIP
        File zipFile = new File(zipFilePath);
        FileInputStream zipInputStream = new FileInputStream(zipFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(zipInputStream));


    }


}