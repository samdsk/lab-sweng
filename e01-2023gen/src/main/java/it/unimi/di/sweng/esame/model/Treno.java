package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record Treno(@NotNull String id) {
	@Override
	public String toString() {
		return String.format("Treno: [%s]",id);
	}
}
