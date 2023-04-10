package com.furkanbegen.conferencescheduler.service;

import com.furkanbegen.conferencescheduler.domain.Schedule;
import com.furkanbegen.conferencescheduler.domain.Talk;

import java.util.List;

public interface TalkService {

    Schedule generateSchedule(List<Talk> talks);
    List<Talk> parseTalks(List<String> talkLines);
}
