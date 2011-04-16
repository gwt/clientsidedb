package gwtdb.client;

import gwtdb.client.CachingLocalDataStore.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ChannelLogView extends Composite implements Display {
	private static ChannelLogUiBinder uiBinder = GWT.create(ChannelLogUiBinder.class);
	interface ChannelLogUiBinder extends UiBinder<Widget, ChannelLogView> {
	}

	public ChannelLogView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	TextArea log;
	@UiField
	Button modify;
	
	@Override
	public void append(String message) {
		log.setText(log.getText() + "\n" + message);
	}
	
	@Override
	public HasClickHandlers getModifyButton() {
		return modify;
	}
}
