package it.unimi.di.sweng.facebuk;

public interface ObservableFacebuk {
	void addObserver(FriendsObserver friendsObserver);
	void removeObserver(FriendsObserver friendsObserver);

	void notifyObservers();
}
