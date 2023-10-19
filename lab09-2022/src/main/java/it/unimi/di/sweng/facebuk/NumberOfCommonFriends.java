package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class NumberOfCommonFriends implements FriendsInCommonStrategy {
	@Override
	public String commonFriends(@NotNull Set<Utente> commonFriends) {
		return String.valueOf(commonFriends.size());
	}
}
