package com.minagri.stats.vaadin.component;

import com.vaadin.flow.router.HasDynamicTitle;

/**
 *
 */
public interface View extends HasDynamicTitle {

	String getViewId();

	@Override
	default String getPageTitle() {
		return getViewId();
	}
}
