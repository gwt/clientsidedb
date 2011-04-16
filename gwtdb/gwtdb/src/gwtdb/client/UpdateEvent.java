package gwtdb.client;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateEvent extends GwtEvent<UpdateEventHandler> {
	public static final Type<UpdateEventHandler> TYPE = new Type<UpdateEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateEventHandler handler) {
		handler.handle(this);
	}
}
