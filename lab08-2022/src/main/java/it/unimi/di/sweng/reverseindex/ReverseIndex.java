package it.unimi.di.sweng.reverseindex;

public interface ReverseIndex {

	int getWordsCount();

	void setOrderingStrategy(OrderStrategy orderStrategy);

	void setPrintBeautify(PrintBeautify printBeautify);
}
