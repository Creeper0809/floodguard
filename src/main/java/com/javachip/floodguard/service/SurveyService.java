package com.javachip.floodguard.service;

import com.javachip.floodguard.api.survey.Survey;
import com.javachip.floodguard.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {
    @Autowired
    private SurveyRepository
            surveyRepository;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }
}
