package com.elytraforce.elytracore.player.redis;

import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;

import java.util.UUID;

public class Delta {

    private final DeltaEnum change;
    private final ValueEnum type;
    private final int amount;
    private final UUID target;

    public Delta(UUID target, int amount, DeltaEnum change, ValueEnum type) {
        this.amount = amount;
        this.change = change;
        this.type = type;
        this.target = target;
    }

    public ValueEnum getType() { return type; }
    public DeltaEnum getChange() { return change; }
    public int getAmount() { return amount; }
    public UUID getTarget() { return target; }
}
