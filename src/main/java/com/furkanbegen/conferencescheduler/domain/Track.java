package com.furkanbegen.conferencescheduler.domain;

import lombok.Data;

@Data
public class Track {
    private Session morningSession;
    private Session lunchSession;
    private Session afternoonSession;

}
