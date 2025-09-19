package com.minagri.stats.screen.zone.boundary;


import com.minagri.stats.layout.boundary.ViewerMainLayout;
import com.minagri.stats.navigation.entity.ViewerRoute;
import com.minagri.stats.screen.zone.control.ZoneController;
import com.minagri.stats.screen.zone.entity.ZoneDTO;
import com.minagri.stats.vaadin.component.grid.LCMGrid;
import com.minagri.stats.vaadin.component.layout.LCMCollapsibleFieldset;
import com.minagri.stats.vaadin.component.layout.LCMGridLayout;
import com.minagri.stats.vaadin.route.RouteView;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Slf4j
@Route(value = ViewerRoute.HOME, layout = ViewerMainLayout.class)
public class ZoneView extends RouteView<ZoneController> implements BeforeEnterObserver {

	LCMCollapsibleFieldset zoneTimelineFieldSet;
	LCMCollapsibleFieldset quarterTableFieldSet;
	LCMGrid<ZoneDTO> zoneQuarterGrid;

	ZoneTableContent zoneTableContent;

	@Override
	public void buildView() {
		initzoneTimeline();

	}

	private void initzoneTimeline() {

		zoneTimelineFieldSet = new LCMCollapsibleFieldset()
				.setLegendText("Name")
				.addTo(this)
				.withWidthFull()
				.withVisible(false);

		zoneTimelineFieldSet
				.withAdd(new LCMGridLayout()
						.withAlignSelfCenter()
						.withAdd(

						));
	}



	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		controller.populateFromPathParam(beforeEnterEvent);
	}
}
