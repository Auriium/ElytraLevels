package com.elytraforce.elytracore.events;

public enum ChangeEnum
{
	INCREASE, DECREASE, SET;

	private static ChangeEnum state;

	public static void setState(ChangeEnum state)
		{
		ChangeEnum.state = state;
		}

	public static boolean isState(ChangeEnum state)
		{
			return ChangeEnum.state == state;
		}

	public static ChangeEnum getState()
		{
			return state;
		}

}
