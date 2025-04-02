package com.acme.aiassistant.model.sfg;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCountryInfoResponse(@JsonPropertyDescription("capital city") String capitalCity,
                                     @JsonPropertyDescription("language") String language,
                                     @JsonPropertyDescription("currency") String currency
                                     ) {
}
