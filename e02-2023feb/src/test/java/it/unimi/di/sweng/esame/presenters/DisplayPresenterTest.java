package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.model.Canzone;
import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import it.unimi.di.sweng.esame.presenters.PodioStrategy;
import it.unimi.di.sweng.esame.views.DisplayView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DisplayPresenterTest {
	@Test
	void testConnectView() {
		DisplayView view = mock(DisplayView.class);
		Model model = mock(Model.class);
		DisplayPresenter SUT = new DisplayPresenter(model,view);

		Canzone c1 = mock(Canzone.class);
		when(c1.formatClassifica()).thenReturn("Australia 0");
		List<Canzone> list = new ArrayList<>();

		list.add(c1);

		SUT.update(model,list);
		verify(view).set(0,c1.formatClassifica());
	}

	@Test
	void testClassificaRight() {
		DisplayView view = mock(DisplayView.class);
		Model model = mock(Model.class);
		int offset = Main.PANEL_SIZE;
		DisplayPresenter SUT = new DisplayPresenter(model,view,offset);

		Canzone c1 = mock(Canzone.class);
		when(c1.formatClassifica()).thenReturn("Australia 0");
		when(c1.getCountry()).thenReturn("Australia");
		Canzone c2 = mock(Canzone.class);

		when(c2.formatClassifica()).thenReturn("Paesi Bassi 0");
		when(c2.getCountry()).thenReturn("Paesi Bassi");
		List<Canzone> list = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			list.add(c1);
		}
		for (int i = 0; i < 8; i++) {
			list.add(c2);
		}

		SUT.update(model,list);
		verify(view,never()).set(0,c1.formatClassifica());
		verify(view).set(0,c2.formatClassifica());
	}
	@Test
	void testPodioWithPrintStrategy() {
		DisplayView view = mock(DisplayView.class);
		Model model = mock(Model.class);
		int limit = Main.PANEL_B_SIZE;
		DisplayPresenter SUT = new DisplayPresenter(model,view,0,limit);
		SUT.setPrintStrategy(new PodioStrategy());

		Canzone c2 = mock(Canzone.class);

		when(c2.formatPodio()).thenReturn("---");
		when(c2.getCountry()).thenReturn("Australia");
		List<Canzone> list = new ArrayList<>();


		for (int i = 0; i < 8; i++) {
			list.add(c2);
		}

		SUT.update(model,list);
		verify(view).set(0,c2.formatPodio());
		verify(view).set(1,c2.formatPodio());
		verify(view).set(2,c2.formatPodio());
	}

	@Test
	void testPodioPrintStrategy() {
		PrintStrategy SUT = new PodioStrategy();

		Canzone c1 = mock(Canzone.class);
		when(c1.formatPodio()).thenReturn("---");

		assertThat(SUT.format(c1)).isEqualTo("---");

	}
	@Test
	void testClassificaPrintStrategy() {
		PrintStrategy SUT = new ClassificaPrintStrategy();

		Canzone c1 = mock(Canzone.class);
		when(c1.formatClassifica()).thenReturn("Australia 0");

		assertThat(SUT.format(c1)).isEqualTo("Australia 0");

	}

	@Test
	void testOrderAlphabetically() {
		DisplayView view = mock(DisplayView.class);
		Model model = mock(Model.class);
		int offset = Main.PANEL_SIZE;
		DisplayPresenter SUT = new DisplayPresenter(model,view,offset);

		SUT.setOrderStrategy(new ClassificaOrder());

		Canzone c1 = mock(Canzone.class);
		when(c1.getCountry()).thenReturn("Australia");
		when(c1.totalPoints()).thenReturn(9);
		when(c1.formatClassifica()).thenReturn("Australia 9");

		Canzone c2 = mock(Canzone.class);
		when(c2.getCountry()).thenReturn("Paesi Bassi");
		when(c2.totalPoints()).thenReturn(4);
		when(c2.formatClassifica()).thenReturn("Paesi Bassi 4");


		Canzone c3 = mock(Canzone.class);
		when(c3.getCountry()).thenReturn("Italia");
		when(c3.totalPoints()).thenReturn(4);
		when(c3.formatClassifica()).thenReturn("Italia 4");

		List<Canzone> list = new ArrayList<>();
		list.add(c2);
		list.add(c3);

		for (int i = 0; i < 8; i++) {
			list.add(c1);
		}

		SUT.update(model,list);

		verify(view).set(0,c3.formatClassifica());
		verify(view).set(1,c2.formatClassifica());
	}
	@Test
	void testOrderClassifica() {
		OrderStrategy SUT = new ClassificaOrder();

		Canzone c1 = mock(Canzone.class);
		when(c1.getCountry()).thenReturn("Australia");
		when(c1.totalPoints()).thenReturn(9);

		Canzone c2 = mock(Canzone.class);
		when(c2.getCountry()).thenReturn("Paesi Bassi");
		when(c2.totalPoints()).thenReturn(4);

		Canzone c3 = mock(Canzone.class);
		when(c3.getCountry()).thenReturn("Italia");
		when(c3.totalPoints()).thenReturn(4);


		List<Canzone> list = new ArrayList<>();

		list.add(c1);
		list.add(c2);
		list.add(c3);


		SUT.sort(list);

		assertThat(list).containsExactly(c1,c3,c2);
	}
	@Test
	void testOrderPodio() {
		DisplayView view = mock(DisplayView.class);
		Model model = mock(Model.class);
		int limit = Main.PANEL_B_SIZE;
		DisplayPresenter SUT = new DisplayPresenter(model,view,0,limit);
		SUT.setPrintStrategy(new PodioStrategy());
		SUT.setOrderStrategy(new PodioOrderStrategy());

		Canzone c1 = mock(Canzone.class);
		when(c1.getCountry()).thenReturn("Australia");
		when(c1.totalPoints()).thenReturn(12);
		when(c1.getFirstCount()).thenReturn(2);

		Canzone c2 = mock(Canzone.class);
		when(c2.getCountry()).thenReturn("Paesi Bassi");
		when(c2.totalPoints()).thenReturn(12);
		when(c2.getFirstCount()).thenReturn(2);

		Canzone c3 = mock(Canzone.class);
		when(c3.getCountry()).thenReturn("Italia");
		when(c3.totalPoints()).thenReturn(15);
		when(c3.getFirstCount()).thenReturn(3);

		List<Canzone> list = new ArrayList<>();

		list.add(c1);
		list.add(c2);
		list.add(c3);

		SUT.update(model,list);

		verify(view).set(0,c3.formatPodio());
		verify(view).set(1,c1.formatPodio());
		verify(view).set(2,c2.formatPodio());
	}
}