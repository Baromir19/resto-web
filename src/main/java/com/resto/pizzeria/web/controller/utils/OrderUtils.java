package com.resto.pizzeria.web.controller.utils;

import java.time.LocalDate;

public class OrderUtils {
    private static LocalDate currentDate = LocalDate.now();
    private static Integer dailyId = 1;

    /**
     * Génération d'un id pour la commande
     * (recommence par 1 chaque journée) pour un restaurant.
     * */
    public synchronized static Integer generateDailyId(final LocalDate date) {
        dailyId = dailyId + 1;

        if (!currentDate.isEqual(date)) {
            currentDate = date;
            dailyId = 1;
        }

        return dailyId;
    }
}
