package com.javachip.floodguard.service;

import com.javachip.floodguard.api.Survey;
import com.javachip.floodguard.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository repository;

    public List<Survey> getAllSurveys() {
        return repository.findAll();
    }
}
