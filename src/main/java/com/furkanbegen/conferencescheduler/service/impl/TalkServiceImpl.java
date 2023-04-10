package com.furkanbegen.conferencescheduler.service.impl;

import com.furkanbegen.conferencescheduler.domain.Schedule;
import com.furkanbegen.conferencescheduler.domain.Session;
import com.furkanbegen.conferencescheduler.domain.Talk;
import com.furkanbegen.conferencescheduler.domain.Track;
import com.furkanbegen.conferencescheduler.service.TalkService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class TalkServiceImpl implements TalkService{

    @Override
    public Schedule generateSchedule(List<Talk> talks) {
        Schedule schedule = new Schedule();

        talks.sort(Comparator.comparingInt(Talk::getDuration).reversed());

        List<Track> tracks = new ArrayList<>();
        while (!talks.isEmpty()) {
            Track track = new Track();

            Session morningSession = new Session(LocalTime.of(9, 0), LocalTime.of(12, 0));
            fillSessionWithTalks(morningSession, talks);

            Session lunchSession = new Session(LocalTime.of(12, 0), LocalTime.of(13, 0));

            Session afternoonSession = new Session(LocalTime.of(13, 0), LocalTime.of(17, 0));
            fillSessionWithTalks(afternoonSession, talks);

            track.setMorningSession(morningSession);
            track.setLunchSession(lunchSession);
            track.setAfternoonSession(afternoonSession);

            tracks.add(track);
        }

        schedule.setTracks(tracks);
        return schedule;
    }

    public List<Talk> parseTalks(List<String> talkLines) {
        List<Talk> talks = new ArrayList<>();

        for (String line : talkLines) {
            String title = line.substring(0, line.lastIndexOf(" "));
            String durationStr = line.substring(line.lastIndexOf(" ") + 1);

            int duration;
            boolean isLightning;

            if ("lightning".equals(durationStr)) {
                duration = 5;
                isLightning = true;
            } else {
                duration = Integer.parseInt(durationStr.replace("min", ""));
                isLightning = false;
            }

            talks.add(new Talk(title, duration, isLightning));
        }

        return talks;
    }


    private void fillSessionWithTalks(Session session, List<Talk> talks) {
        LocalTime currentTime = session.getStartTime();
        Iterator<Talk> talkIterator = talks.iterator();

        while (talkIterator.hasNext()) {
            Talk talk = talkIterator.next();

            if (currentTime.plusMinutes(talk.getDuration()).isBefore(session.getEndTime())
                    || currentTime.plusMinutes(talk.getDuration()).equals(session.getEndTime())) {
                session.getTalks().add(talk);
                currentTime = currentTime.plusMinutes(talk.getDuration());
                talkIterator.remove();
            }
        }
    }
}
