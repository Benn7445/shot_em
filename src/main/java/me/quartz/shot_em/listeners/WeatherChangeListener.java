package me.quartz.shot_em.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
