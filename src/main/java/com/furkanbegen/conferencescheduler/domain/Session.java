package com.furkanbegen.conferencescheduler.domain;

import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Session {
    private List<Talk> talks = new ArrayList<>();
    private LocalTime startTime;
    private LocalTime endTime;

    public Session(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
