package com.minagri.stats.vaadin.component;

public abstract class Controller<VIEW extends View> {

	private VIEW view;

	public VIEW getView() {
		return view;
	}

	public void initWithView(VIEW view) {
		this.initBeforeViewIsSet();
		this.view = view;
		this.initAfterViewIsSet();
	}

	public void initBeforeViewIsSet() {
		// By default nothing to do
	}

	public abstract void initAfterViewIsSet();
}
