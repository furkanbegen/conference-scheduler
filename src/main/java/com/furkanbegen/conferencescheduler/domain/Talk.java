package com.furkanbegen.conferencescheduler.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Talk {
    private String title;
    private int duration;
    private boolean isLightning;

}