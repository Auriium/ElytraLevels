package com.elytraforce.elytracore.hooks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class ElytraEconomy extends AbstractEconomy{

	private static String name = "ElytraEconomy";
	private static DecimalFormat currencyFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));
	
	@Override
	public boolean isEnabled() {
		if (Main.get() == null) {
			return false;
		} else {
			return Main.get().isEnabled();
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int fractionalDigits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String format(double amount) {
		
		
		currencyFormat.setRoundingMode(RoundingMode.FLOOR);
		String str = currencyFormat.format(amount);
		if (str.endsWith(".00"))
		{
			str = str.substring(0, str.length() - 3);
		}
		
		return str;
	}

	@Override
	public String currencyNamePlural() {
		// TODO Auto-generated method stub
		return "⛃";
	}

	@Override
	public String currencyNameSingular() {
		// TODO Auto-generated method stub
		return "⛃";
	}

	@Override
	public boolean hasAccount(String playerName) {
		// TODO Auto-generated method stub
		if (PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName)) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		// TODO Auto-generated method stub
		return this.hasAccount(playerName);
	}

	@Override
	public double getBalance(String playerName) {
		try {
			return PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName)).getMoney();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public double getBalance(String playerName, String world) {
		return this.getBalance(playerName);
	}

	@Override
	public boolean has(String playerName, double amount) {
		// TODO Auto-generated method stub
		if (PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName)).getMoney() < amount) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return this.has(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		// TODO Auto-generated method stub
		if (playerName == null) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Player name can not be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");
        }
        
        if (PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName)) == null) {
        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "This player does not exist!");
        }
        
        try {
        	ElytraPlayer player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName));
        	player.removeMoney((int)amount, false);
        	return new EconomyResponse(amount, player.getMoney(), ResponseType.SUCCESS, null);
        } catch (Exception e) {
        	e.printStackTrace();
        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "Something went wrong");
        }
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return this.withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		if (playerName == null) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Player name can not be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot desposit negative funds");
        }
        
        if (PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName)) == null) {
        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "This player does not exist!");
        }
        
        try {
        	ElytraPlayer player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName));
        	player.addMoney((int)amount, false);
        	return new EconomyResponse(amount, player.getMoney(), ResponseType.SUCCESS, null);
        } catch (Exception e) {
        	e.printStackTrace();
        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "Something went wrong");
        }
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return this.depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		// TODO Auto-generated method stub
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public List<String> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		if (PlayerController.get().getLevelPlayer(Bukkit.getPlayer(playerName)) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		// TODO Auto-generated method stub
		return this.createPlayerAccount(playerName);
	}

}
