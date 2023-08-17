package com.javachip.floodguard.controller;

import com.javachip.floodguard.api.Survey;
import com.javachip.floodguard.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {
    @Autowired
    private SurveyRepository repository;

    public List<Survey> getAllSurveys() {
        return repository.findAll();
    }

    public Survey saveSurvey(Survey survey) {
        return repository.save(survey);
    }
}
