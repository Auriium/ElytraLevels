package com.elytraforce.elytracore.hooks;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import com.elytraforce.elytracore.storage.SQLStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;

import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.OfflinePlayer;

public class ElytraEconomy implements Economy {

	//TODO: Something about getting balances from Strings, it is stupid.

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
		return hasAccount(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		if (player.isOnline()) {
			return PlayerController.get().getElytraPlayer(player.getPlayer()) != null;
		} else {
			return SQLStorage.get().playerExists(player);
		}
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return hasAccount(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return hasAccount(player);
	}


	@Override
	public double getBalance(String playerName) {
		return this.getBalance(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		if (player.isOnline()) {
			ElytraPlayer p = PlayerController.get().getElytraPlayer(player);
			return p == null ? 0 : p.getMoney();
		} else {
			try {
				return SQLStorage.get().loadPlayerCached(player).getMoney();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	@Override
	public double getBalance(String playerName, String world) {
		return this.getBalance(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return this.getBalance(player);
	}

	@Override
	public boolean has(String playerName, double amount) {
		return has(Bukkit.getOfflinePlayer(playerName),amount);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		if (player.isOnline()) {
			return PlayerController.get().getElytraPlayer(player).getMoney() >= amount;
		} else {
			try {
				ElytraPlayer p = SQLStorage.get().loadPlayerCached(player);
				return p.getMoney() >= amount;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		return this.has(playerName, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		return has(player,amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		if (playerName == null) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Player name can not be null.");
		}
		return withdrawPlayer(Bukkit.getOfflinePlayer(playerName),amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {

		//TODO: is it possible to do this asynchronously?

		if (amount < 0) { return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");  }

		if (PlayerController.get().getElytraPlayer(player) == null) {
			//if this is null it means they dont exist in our cached online players, if the following is true they dont exist on database either
			if (!SQLStorage.get().playerExists(player)) { return new EconomyResponse(0, 0, ResponseType.FAILURE, "This player does not exist!"); }
			//now get the shit
			try {
				ElytraPlayer p = SQLStorage.get().loadPlayerCached(player); p.removeMoney((int) amount,false);
				SQLStorage.get().updatePlayerCached(p);
				return new EconomyResponse(amount, p.getMoney(), ResponseType.SUCCESS, null);
			} catch (Exception e) { return new EconomyResponse(0, 0, ResponseType.FAILURE, "Unknown error in ElytraEconomy (Error A)!"); }
		} else {
			ElytraPlayer p = PlayerController.get().getElytraPlayer(player);
			p.removeMoney((int)amount, false);
			return new EconomyResponse(amount, p.getMoney(), ResponseType.SUCCESS, null);
		}

	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		return this.withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		return this.withdrawPlayer(player,amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		return this.depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		if (amount < 0) { return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot deposit negative funds");  }

		if (PlayerController.get().getElytraPlayer(player) == null) {
			//if this is null it means they dont exist in our cached online players, if the following is true they dont exist on database either
			if (!SQLStorage.get().playerExists(player)) { return new EconomyResponse(0, 0, ResponseType.FAILURE, "This player does not exist!"); }
			//now get the shit
			try {
				ElytraPlayer p = SQLStorage.get().loadPlayerCached(player); p.addMoney((int) amount,false);
				SQLStorage.get().updatePlayerCached(p);
				return new EconomyResponse(amount, p.getMoney(), ResponseType.SUCCESS, null);
			} catch (Exception e) { return new EconomyResponse(0, 0, ResponseType.FAILURE, "Unknown error in ElytraEconomy (Error A)!"); }
		} else {
			ElytraPlayer p = PlayerController.get().getElytraPlayer(player);
			p.addMoney((int)amount, false);
			return new EconomyResponse(amount, p.getMoney(), ResponseType.SUCCESS, null);
		}
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return this.depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		return this.depositPlayer(player,amount);
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "What?");
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		return this.createPlayerAccount(Bukkit.getOfflinePlayer(playerName));
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		//TODO: debug testing
		PlayerController.get().playerJoined(player.getPlayer());
		return true;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return this.createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return this.createPlayerAccount(player);
	}

}
