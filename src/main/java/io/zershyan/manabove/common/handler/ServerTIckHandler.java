package io.zershyan.manabove.common.handler;

import io.zershyan.manabove.ManAbove;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = ManAbove.MODID)
public class ServerTIckHandler {
    private static final Map<Runnable, Integer> DelayRunnable =  new HashMap<>();

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Pre event) {
        if(DelayRunnable.isEmpty()) return;
        for (Runnable runnable : Set.copyOf(DelayRunnable.keySet())) {
            Integer tick = DelayRunnable.get(runnable);
            if(tick == 0) {
                DelayRunnable.remove(runnable);
                runnable.run();
            } else {
                DelayRunnable.put(runnable, tick - 1);
            }
        }
    }

    public static void addDelayRunnable(Runnable runnable, int delay) {
        if(delay <= 0) return;
        DelayRunnable.put(runnable, delay);
    }
}
