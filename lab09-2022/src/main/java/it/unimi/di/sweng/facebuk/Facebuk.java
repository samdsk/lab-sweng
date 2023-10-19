package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.*;

public class Facebuk implements ObservableFacebuk {
	private final @NotNull Map<String,Utente> users = new HashMap<>();
	private FriendsInCommonStrategy friendsInCommonStrategy;
	private ReaderStrategy readerStrategy = new usernameReader();

	private final Set<FriendsObserver> observers = new HashSet<>();

	public void addUser(Utente user) {
		users.putIfAbsent(user.toString(),user);
	}

	public int userCount() {
		return users.size();
	}

	public void leggi(@NotNull Reader reader) {
		readerStrategy.read(reader,users);
	}

	public void setReaderStrategy(ReaderStrategy readerStrategy) {
		this.readerStrategy = readerStrategy;
	}

	public String userFriendsToString(String user) {

		if(users.containsKey(user)) return users.get(user).friendsToString();

		return "Unknown user";
	}

	public String friendsInCommon(@NotNull String user1,@NotNull String user2) {
		assert users.containsKey(user1) && users.containsKey(user2) : "Users not found!";

		Set<Utente> commonFriends = new LinkedHashSet<>();

		Utente second = users.get(user2);

		for(Utente friend : users.get(user1)){
			if(second.isFriend(friend)) commonFriends.add(friend);
		}

		return friendsInCommonStrategy.commonFriends(commonFriends);
	}

	public void setFriendsInCommonStrategy(FriendsInCommonStrategy friendsInCommonStrategy) {
		this.friendsInCommonStrategy = friendsInCommonStrategy;
	}

	@Override
	public void addObserver(FriendsObserver friendsObserver) {
		observers.add(friendsObserver);
	}

	@Override
	public void removeObserver(FriendsObserver friendsObserver) {
		observers.remove(friendsObserver);
	}

	@Override
	public void notifyObservers() {
		for(FriendsObserver f : observers){
			f.update();
		}
	}

	public Map<String,String> generateCommonFriends(){
		Map<String,String> commonFriends = new LinkedHashMap<>();
		for(String a : users.keySet()){
			for(String b  : users.keySet()){
				String ab = a+" "+b;
				String ba = b+" "+a;
				if(!a.equals(b) && !commonFriends.containsKey(ab) && !commonFriends.containsKey(ba)){
					commonFriends.put(ab, friendsInCommon(a,b));
				}
			}
		}

		return commonFriends;
	}
}
