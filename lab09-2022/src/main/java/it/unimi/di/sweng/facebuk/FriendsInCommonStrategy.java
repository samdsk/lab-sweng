package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface FriendsInCommonStrategy {
	String commonFriends(@NotNull Set<Utente> commonFriends);
}
