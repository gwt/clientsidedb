package gwtdb.client;

import gwtdb.client.ClientSideDB.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class SampleView extends Composite implements Display {
	private static SampleUiBinder uiBinder = GWT.create(SampleUiBinder.class);

	interface SampleUiBinder extends UiBinder<Widget, SampleView> {
	}

	public SampleView() {
		initWidget(uiBinder.createAndBindUi(this));
		configureTable();
	}

	private void configureTable() {
		final TextColumn<ClientEntity> id = new TextColumn<ClientEntity>() {
			@Override
			public String getValue(ClientEntity object) {
				return String.valueOf(object.getId());
			}
		};
		final TextColumn<ClientEntity> name = new TextColumn<ClientEntity>() {
			@Override
			public String getValue(ClientEntity object) {
				return String.valueOf(object.get("Name"));
			}
		};
		
		id.setSortable(true);
		name.setSortable(true);

		table.addColumn(name, "Name");
		table.addColumn(id, "ID");
		
		tableDataProvider.addDataDisplay(table);
	}

	@UiField
	Label log;
	@UiField
	Button newBtn;
	@UiField
	Button cancelBtn;
	@UiField
	Button saveBtn;
	@UiField
	Button deleteBtn;

	@UiField
	CellTable<ClientEntity> table;
	private ListDataProvider<ClientEntity> tableDataProvider = new ListDataProvider<ClientEntity>();

	@Override
	public void append(String message) {
		log.setText(log.getText() + "\n" + message);
	}

	@Override
	public HasClickHandlers getNewButton() {
		return newBtn;
	}

	@Override
	public HasClickHandlers getCancelBtn() {
		return cancelBtn;
	}

	@Override
	public HasClickHandlers getSaveBtn() {
		return saveBtn;
	}

	@Override
	public HasClickHandlers getDeleteBtn() {
		return deleteBtn;
	}

	@Override
	public void insert(final ClientEntity entity) {
		tableDataProvider.getList().add(entity);
	}
}
