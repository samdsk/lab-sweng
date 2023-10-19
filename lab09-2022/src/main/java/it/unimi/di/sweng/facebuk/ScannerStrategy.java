package it.unimi.di.sweng.facebuk;

import java.io.Reader;
import java.util.Scanner;

public interface ScannerStrategy {
	Scanner scanner (Reader reader);
}
