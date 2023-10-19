package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsObserver {
	private final @NotNull Facebuk facebuk;
	private @NotNull Map<String,String> commonFriends = new HashMap<>();
	private PrintOrderSrategy printOrderSrategy = PrintOrderSrategy.NO_REORDER;

	public FriendsObserver(@NotNull Facebuk facebuk) {
		this.facebuk = facebuk;
		this.facebuk.addObserver(this);
	}

	public void update(){
		commonFriends = this.facebuk.generateCommonFriends();
	}

	public String printCommonFriends() {
		StringBuilder sb = new StringBuilder();

		List<Map.Entry<String, String>> list = new ArrayList<>(commonFriends.entrySet());
		printOrderSrategy.order(list);

		for(Map.Entry<String,String> e: list){
			sb.append(e.getKey()).append(' ').append(e.getValue());
			sb.append('\n');
		}

		return sb.toString();
	}

	public void setPrintOrderStrategy(PrintOrderSrategy printOrderSrategy) {
		this.printOrderSrategy = printOrderSrategy;
	}
}
