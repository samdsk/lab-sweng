package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ConcretePostazione implements Postazione {
	private final @NotNull Bagnino bagnino;
	private final int zona;
	private @NotNull Flag flag;

	public ConcretePostazione(@NotNull Bagnino bagnino, int zona, @NotNull Flag flag) {
		this.bagnino = bagnino;
		this.zona = zona;
		this.flag = flag;
	}

	public ConcretePostazione(@NotNull Postazione postazione) {
		this.bagnino = postazione.bagnino();
		this.flag = postazione.flag();
		this.zona = postazione.zona();
	}

	public @NotNull String formatPostazione() {
		return String.format("[%d] %s segnala %s", zona, bagnino.name(), flag.getDescription());
	}

	public @NotNull String formatBagnini() {
		return String.format("%s è alla postazione %d", bagnino.name(), zona);
	}

	@Override
	public void changeFlag(@NotNull Flag flag) {
		this.flag = flag;
	}

	@Override
	public @NotNull Bagnino bagnino() {
		return bagnino;
	}

	@Override
	public int zona() {
		return zona;
	}

	@Override
	public @NotNull Flag flag() {
		return flag;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ConcretePostazione) obj;
		return Objects.equals(this.bagnino, that.bagnino) &&
				this.zona == that.zona &&
				Objects.equals(this.flag, that.flag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bagnino, zona, flag);
	}

	@Override
	public String toString() {
		return "ConcretePostazione[" +
				"bagnino=" + bagnino + ", " +
				"zona=" + zona + ", " +
				"flag=" + flag + ']';
	}

}
