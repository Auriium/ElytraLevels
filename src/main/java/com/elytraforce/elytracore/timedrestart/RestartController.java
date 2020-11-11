package com.elytraforce.elytracore.timedrestart;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.elytraforce.elytracore.Main;

public class RestartController {
	private LocalDateTime nextRestartTime;
    private ScheduledExecutorService executor;
    
    private Main main;
    private static RestartController instance;
    
    public RestartController(Main plugin) {
        this.main = plugin;
    }
    
    public void onEnable() {
        nextSchedule();
    }
    
    public void onDisable() {
        stopSchedule();
    }
    
    public void onReload() {
        nextSchedule();
    }
    
    // getters
    
    public LocalDateTime getNextRestartTime() {
        return nextRestartTime;
    }
    
    // func
    
    public void schedule(long millisToRestart) {
        nextRestartTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + millisToRestart),
                ZoneId.systemDefault());
    
        executor = Executors.newSingleThreadScheduledExecutor();

            long millisToTask = millisToRestart - 0 * 1000;
            if (millisToTask < 0) return;
            
            executor.schedule(() -> {
                Main.get().getServer().spigot().restart();
            }, millisToTask, TimeUnit.MILLISECONDS);

        
        executor.schedule(() -> runSync(this::nextSchedule),
                millisToRestart, TimeUnit.MILLISECONDS);
    }
    
    public void scheduleSkipped() {
        stopSchedule();
        if (nextRestartTime != null) {
            ZonedDateTime timeToSkip = nextRestartTime
                    .atZone(ZoneId.systemDefault());
            nextRestartTime = null;
            getNextDelayMillis(timeToSkip).ifPresent(this::schedule);
        }
    }
    
    public void nextSchedule() {
        stopSchedule();
        nextRestartTime = null;
        getNextDelayMillis().ifPresent(this::schedule);
    }
    
    public void stopSchedule() {
        if (executor != null)
            executor.shutdownNow();
    }
    
    // internal
    
    private Optional<Long> getNextDelayMillis() {
        return getNextDelayMillis(ZonedDateTime.now());
    }
    
    private Optional<Long> getNextDelayMillis(ZonedDateTime timeAfter) {
        long currentMillis = System.currentTimeMillis();
        
        // select next nearest time
        long minDelayMillis = -1;
        
        CronDefinition definition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        Cron cron = new CronParser(definition).parse("0 00 2 * * ?");
            Optional<ZonedDateTime> time = ExecutionTime.forCron(cron).nextExecution(timeAfter);
            if (time.isPresent()) {
                long delayMillis = time.get().toInstant().toEpochMilli() - currentMillis;
                if (delayMillis < minDelayMillis || minDelayMillis == -1) minDelayMillis = delayMillis;
            }
         
        
        return minDelayMillis == -1 ? Optional.empty() : Optional.of(minDelayMillis + 1);
    }
    
    private void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(Main.get(), runnable);
    }
    
    public static RestartController get() {
    	if (instance == null)  {
    		return instance = new RestartController(Main.get());
    	} else {
    		return instance;
    	}
    }

}
