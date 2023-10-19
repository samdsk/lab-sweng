package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Utente implements Iterable<Utente>{
	@NotNull protected final String username;

	@Nullable protected String name = null;
	@Nullable protected String surname = null;

	@NotNull
	private final Set<Utente> friends = new LinkedHashSet<>();

	protected Utente(@NotNull String username) {
		this.username = username;
	}

	@NotNull
	String getUsername() {
		return username;
	}
	public @Nullable String getName() {
		return name;
	}

	public @Nullable String getSurname() {
		return surname;
	}

	public void addFriend(@NotNull Utente friend) {
		if(friend.equals(this)) return;

		friends.add(friend);
		if(!friend.isFriend(this))
			friend.addFriend(this);
	}

	public String friendsToString() {
		return this + " : " + friends;
	}

	public int friendsCount() {
		return friends.size();
	}

	public boolean isFriend(Utente friend) {
		return friends.contains(friend);
	}

	@NotNull
	@Override
	public Iterator<Utente> iterator() {
		return Collections.unmodifiableCollection(friends).iterator();
	}

	public static String createUsername(String name, String surname) {
		return name + "-" + surname;
	}

}
