package com.email.emailService.providers;

import com.email.emailService.model.StatsUser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsServiceTest {

    @Test
    void getStatsTest(){
        List<StatsUser> list = new ArrayList<StatsUser>();
        list.add(new StatsUser("federicoamartucci@gmail.com", 1));
        assertEquals(list, new StatsService().getStats());
    }
}