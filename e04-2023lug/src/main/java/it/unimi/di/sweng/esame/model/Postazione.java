package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Postazione {
	Postazione EmptyPostazione = new Postazione() {
		@Override
		public @NotNull String formatPostazione() {
			return "postazione non presidiata";
		}

		@Override
		public @NotNull String formatBagnini() {
			return "";
		}

		@Override
		public @NotNull Bagnino bagnino() {
			return new Bagnino("postazione non presidiata");
		}

		@Override
		public int zona() {
			return -1;
		}

		@Override
		public @NotNull Flag flag() {
			return Flag.NONE;
		}

		@Override
		public void changeFlag(@NotNull Flag flag){}

	};

	@NotNull String formatPostazione();

	@NotNull String formatBagnini();

	@NotNull Bagnino bagnino();

	int zona();

	@NotNull Flag flag();

	void changeFlag(@NotNull Flag flag);
}

