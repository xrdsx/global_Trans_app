package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.repository.DayWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayWorkService {

    @Autowired
    private DayWorkRepository dayWorkRepository;

    public List<DayWork> getAllDayWorks() {
        return dayWorkRepository.findAll();
    }

    public Optional<DayWork> getDayWorkById(int id) {
        return dayWorkRepository.findById(id);
    }

    public void saveDayWork(DayWork dayWork) {
        dayWorkRepository.save(dayWork);
    }

    public void deleteDayWork(int id) {
        dayWorkRepository.deleteById(id);
    }
}