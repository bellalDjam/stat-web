/*
 * Copyright 2003-2009 LCM-ANMC, Inc. All rights reserved.
 * This source code is the property of LCM-ANMC, Direction
 * Informatique and cannot be copied or distributed without
 * the formal permission of LCM-ANMC.
 */
package com.minagri.stats.screen.zone.boundary;


import com.minagri.stats.screen.zone.entity.ZoneDTO;
import com.minagri.stats.vaadin.component.grid.LCMGrid;
import com.vaadin.flow.data.provider.ListDataProvider;


public class ZoneTableContent extends LCMGrid<ZoneDTO> {


	ListDataProvider<ZoneDTO> dataProvider;


	public ZoneTableContent() {
		this.withAllRowsVisibleAndMaxHeightInPixels(300).setMinHeight("150px");

		initColumns();
	}


	private void initColumns() {
		this.withEmptyStateText()
				.withNoBorder()
				.withPaperClass()
				.withColumn(ZoneDTO::getName, "Name")
				.withColumn(ZoneDTO::getCode, "Code")
				.withFooter();
	}
}
