package com.furkanbegen.conferencescheduler.service.impl;

import com.furkanbegen.conferencescheduler.domain.Schedule;
import com.furkanbegen.conferencescheduler.domain.Session;
import com.furkanbegen.conferencescheduler.domain.Talk;
import com.furkanbegen.conferencescheduler.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TalkServiceImplTest {


    private TalkServiceImpl talkService;

    @BeforeEach
    public void setup() {
        talkService = new TalkServiceImpl();
    }

    @Test
    void testGenerateSchedule() {
        List<String> talkLines = Arrays.asList(
                "Architecting Your Codebase 60min",
                "Overdoing it in Python 45min",
                "Flavors of Concurrency in Java 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "JUnit 5 - Shaping the Future of Testing on the JVM 45min",
                "Cloud Native Java lightning",
                "Communicating Over Distance 60min",
                "AWS Technical Essentials 45min",
                "Continuous Delivery 30min",
                "Monitoring Reactive Applications 30min",
                "Pair Programming vs Noise 45min",
                "Rails Magic 60min",
                "Microservices \"Just Right\" 60min",
                "Clojure Ate Scala (on my project) 45min",
                "Perfect Scalability 30min",
                "Apache Spark 30min",
                "Async Testing on JVM 60min",
                "A World Without HackerNews 30min",
                "User Interface CSS in Apps 30min"
        );

        List<Talk> talks = talkService.parseTalks(talkLines);
        Schedule schedule = talkService.generateSchedule(talks);

        assertEquals(2, schedule.getTracks().size());

        for (Track track : schedule.getTracks()) {
            Session morningSession = track.getMorningSession();
            Session afternoonSession = track.getAfternoonSession();

            assertEquals(morningSession.getStartTime(), LocalTime.of(9, 0));
            assertEquals(morningSession.getEndTime(), LocalTime.of(12, 0));

            assertEquals(afternoonSession.getStartTime(), LocalTime.of(13, 0));
            assertEquals(afternoonSession.getEndTime(), LocalTime.of(17, 0));

            // Validate that the total duration of talks is within the session limits
            int totalMorningDuration = morningSession.getTalks().stream().mapToInt(Talk::getDuration).sum();
            int totalAfternoonDuration = afternoonSession.getTalks().stream().mapToInt(Talk::getDuration).sum();

            assertTrue(totalMorningDuration <= 180);
            assertTrue(totalAfternoonDuration <= 240);
        }
    }

    @Test
    void testParseTalks() {
        List<String> talkLines = Arrays.asList(
                "Architecting Your Codebase 60min",
                "Overdoing it in Python 45min",
                "Cloud Native Java lightning"
        );

        List<Talk> talks = talkService.parseTalks(talkLines);

        assertEquals(3, talks.size());
        assertEquals("Architecting Your Codebase", talks.get(0).getTitle());
        assertEquals(60, talks.get(0).getDuration());
        assertEquals("Overdoing it in Python", talks.get(1).getTitle());
        assertEquals(45, talks.get(1).getDuration());
        assertEquals("Cloud Native Java", talks.get(2).getTitle());
        assertEquals(5, talks.get(2).getDuration());
    }

    @Test
    void testGenerateScheduleWithMinimalTalks() {
        List<String> talkLines = Arrays.asList(
                "Architecting Your Codebase 60min",
                "Overdoing it in Python 45min"
        );

        List<Talk> talks = talkService.parseTalks(talkLines);
        Schedule schedule = talkService.generateSchedule(talks);

        assertEquals(1, schedule.getTracks().size());

        Track track = schedule.getTracks().get(0);
        Session morningSession = track.getMorningSession();
        Session afternoonSession = track.getAfternoonSession();

        assertNotNull(morningSession.getTalks());
        assertNotNull(afternoonSession.getTalks());

        assertEquals(morningSession.getStartTime(), LocalTime.of(9, 0));
        assertEquals(morningSession.getEndTime(), LocalTime.of(12, 0));

        assertEquals(afternoonSession.getStartTime(), LocalTime.of(13, 0));
        assertEquals(afternoonSession.getEndTime(), LocalTime.of(17, 0));

        assertEquals(2, morningSession.getTalks().size());
        assertEquals(0, afternoonSession.getTalks().size());
    }
}