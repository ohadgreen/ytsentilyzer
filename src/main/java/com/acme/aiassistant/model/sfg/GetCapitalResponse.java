package com.acme.aiassistant.model.sfg;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalResponse(@JsonPropertyDescription("the city name") String answer) {
}
