package com.furkanbegen.conferencescheduler.controller;

import com.furkanbegen.conferencescheduler.domain.Schedule;
import com.furkanbegen.conferencescheduler.domain.Talk;
import com.furkanbegen.conferencescheduler.service.TalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TalkController {
    private final TalkService talkService;

    @PostMapping("/generate-schedule")
    public Schedule generateSchedule(@RequestBody List<Talk> talks) {
        return talkService.generateSchedule(talks);
    }
}
