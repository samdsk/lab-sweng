package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Partenza;
import it.unimi.di.sweng.esame.model.Treno;
import it.unimi.di.sweng.esame.view.DepartureView;
import it.unimi.di.sweng.esame.view.SetDelayView;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class DisplayPresenterTest {
	Partenza p1 = new Partenza(
			new Treno("TI 3029"),
			"ALBENGA",
			LocalTime.parse("14:32"),
			0
	);
	Partenza p2 = new Partenza(
			new Treno("TI 4329"),
			"SONDRIO",
			LocalTime.parse("14:22"),
			23
	);
	Partenza p3 = new Partenza(
			new Treno("TI 5642"),
			"TORINO",
			LocalTime.parse("14:21"),
			12
	);
	Partenza p4 = new Partenza(
			new Treno("TI 7842"),
			"ROMA",
			LocalTime.parse("15:21"),
			12
	);

	@Test
	void testConnectView() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel model = mock(ObservableModel.class);

		List<Partenza> list = new ArrayList<>();
		list.add(p1);

		when(model.getState()).thenReturn(list);

		DisplayPresenter SUT = new DisplayPresenter(model,view, 0);

		SUT.update(model,model.getState());

		verify(view,atLeastOnce()).set(anyInt(),anyString());
	}
	@Test
	void testDisplayEmptyRowsForMissingTrains() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel model = mock(ObservableModel.class);

		List<Partenza> list = new ArrayList<>();
		list.add(p1);

		when(model.getState()).thenReturn(list);

		DisplayPresenter SUT = new DisplayPresenter(model,view, 0);

		SUT.update(model,model.getState());

		verify(view,times(1)).set(0,p1.viewFormat());
		verify(view,times(7)).set(anyInt(),eq(""));
	}

	@Test
	void testDisplayPresenterOrder() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel model = mock(ObservableModel.class);

		List<Partenza> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		list.add(p3);

		when(model.getState()).thenReturn(list);

		DisplayPresenter SUT = new DisplayPresenter(model,view, 0);

		SUT.update(model,model.getState());

		verify(view,atLeastOnce()).set(0,p1.viewFormat());
		verify(view,atLeastOnce()).set(1,p3.viewFormat());
		verify(view,atLeastOnce()).set(2,p2.viewFormat());
	}
	@Test
	void testDisplayPresenterLeft() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel model = mock(ObservableModel.class);

		List<Partenza> list = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			list.add(p1);
			list.add(p3);
		}

		for (int i = 0; i < 12; i++) {
			list.add(p2);
		}

		when(model.getState()).thenReturn(list);

		DisplayPresenter SUT = new DisplayPresenter(model,view,0);

		SUT.update(model,model.getState());

		verify(view,times(4)).set(anyInt(),eq(p1.viewFormat()));
		verify(view,times(4)).set(anyInt(),eq(p3.viewFormat()));
		verify(view,never()).set(8,p2.viewFormat());
	}
	@Test
	void testDisplayPresenterRight() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel model = mock(ObservableModel.class);

		List<Partenza> list = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			list.add(p1);
			list.add(p3);
			list.add(p2);
			list.add(p4);
		}

		when(model.getState()).thenReturn(list);

		DisplayPresenter SUT = new DisplayPresenter(model,view,8);

		SUT.update(model,model.getState());

		verify(view,times(4)).set(anyInt(),eq(p2.viewFormat()));
		verify(view,times(4)).set(anyInt(),eq(p4.viewFormat()));
		verify(view,never()).set(anyInt(),eq(p3.viewFormat()));
		verify(view,never()).set(anyInt(),eq(p1.viewFormat()));
	}

	@Test
	void testSetAsDeparted() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel model = mock(ObservableModel.class);
		DisplayPresenter SUT = new DisplayPresenter(model,view,0);

		SUT.action(p1.viewFormat(),null);

		verify(model).removeTrain(p1.treno());
	}
	@Test
	void testCallNotifyObserversAfterTrainRemove() {
		ObservableModel SUT = new ObservableModel();
		DisplayPresenter presenter = mock(DisplayPresenter.class);

		SUT.readFile();
		SUT.addObserver(presenter);

		SUT.removeTrain(p1.treno());

		verify(presenter).update(eq(SUT),any());
	}

	@Test
	void testSetDelay() {
		SetDelayView view = mock(SetDelayView.class);
		ObservableModel model = mock(ObservableModel.class);
		SetDelayPresenter SUT = new SetDelayPresenter(model,view);

		SUT.action("TI 3029","4");

		verify(model).setDelay(p1.treno(),4);
	}
	@Test
	void testSetDelayEmptyTrainCode() {
		SetDelayView view = mock(SetDelayView.class);
		ObservableModel model = mock(ObservableModel.class);
		SetDelayPresenter SUT = new SetDelayPresenter(model,view);

		SUT.action("","4");

		verify(model,never()).setDelay(any(),anyInt());
	}
	@Test
	void testSetDelayInvalidDelay() {
		SetDelayView view = mock(SetDelayView.class);
		ObservableModel model = mock(ObservableModel.class);
		SetDelayPresenter SUT = new SetDelayPresenter(model,view);

		SUT.action("TI 3029","");

		verify(model,never()).setDelay(any(),anyInt());
	}
	@Test
	void testSetDelayAndUpdate() {
		DepartureView view = mock(DepartureView.class);
		ObservableModel SUT = new ObservableModel();
		DisplayPresenter presenter = mock(DisplayPresenter.class);

		SUT.addObserver(presenter);
		SUT.readFile();
		SUT.setDelay(p1.treno(),34);

		verify(presenter).update(SUT,SUT.getState());
	}
}