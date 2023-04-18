package com.example.medcheckb7.converter;

import com.example.medcheckb7.db.entities.Expert;
import com.example.medcheckb7.dto.request.ExpertRequest;
import com.example.medcheckb7.dto.request.ExpertRequests;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class
ExpertRequestConverter {

    public Expert addExperts(ExpertRequest expertRequest) {

        if (expertRequest == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setExpertFirstName(expertRequest.getExpertFirstName());
        expert.setExpertLastName(expertRequest.getExpertLastName());
        expert.setExpertPosition(expertRequest.getExpertPosition());
        expert.setExpertImage(expertRequest.getExpertImage());
        expert.setExpertInformation(expertRequest.getExpertInformation());
        return expert;
    }

    @Transactional
    public Expert update(ExpertRequests expertRequest, Expert expert) {
        if (expertRequest == null) {
            return null;
        }
        if (expertRequest.getExpertFirstName() != null) {
            expert.setExpertFirstName(expertRequest.getExpertFirstName());
        }
        if (expertRequest.getExpertLastName() != null) {
            expert.setExpertLastName(expertRequest.getExpertLastName());
        }
        if (expertRequest.getExpertPosition() != null) {
            expert.setExpertPosition(expertRequest.getExpertPosition());
        }
        if (expertRequest.getExpertImage() != null) {
            expert.setExpertImage(expertRequest.getExpertImage());
        }
        if (expertRequest.getExpertInformation() != null) {
            expert.setExpertInformation(expertRequest.getExpertInformation());
        }
        return expert;
    }

    public Expert updateStatus(@NotNull Expert expert) {
        if (!expert.getExpertStatus()) {
            expert.setExpertStatus(true);
        } else if (expert.getExpertStatus()) {
            expert.setExpertStatus(false);
        }
        return expert;
    }
}