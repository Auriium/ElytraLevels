package com.elytraforce.elytracore.player.redis.enums;


public enum DeltaEnum {
    INCREASE, DECREASE;

    private static DeltaEnum state;

    public static void setState(DeltaEnum state)
    {
        DeltaEnum.state = state;
    }

    public static boolean isState(DeltaEnum state)
    {
        return DeltaEnum.state == state;
    }

    public static DeltaEnum getState()
    {
        return state;
    }
}
