package com.minagri.stats.vaadin.component.audio;

import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

@Tag("audio")
public class LCMAudio extends Component implements ComponentFluent<LCMAudio> {

    Element sourceElement = new Element("source");

    public LCMAudio() {
        this.withSourceType("audio/ogg")
                .getElement().appendChild(sourceElement);
    }

    public void play() {
        getUI().ifPresent(ui -> ui.getPage().executeJs("$0.play()", this.getElement()));
    }

    public void pause() {
        getUI().ifPresent(ui -> ui.getPage().executeJs("$0.pause()", this.getElement()));
    }

    public String getSourceSrc() {
        return sourceElement.getAttribute("src");
    }

    public String getSourceType() {
        return sourceElement.getAttribute("type");
    }

    public LCMAudio withSourceSrc(String src) {
        sourceElement.setAttribute("src", src);
        return getFluent();
    }

    public LCMAudio withSourceType(String type) {
        sourceElement.setAttribute("type", type);
        return getFluent();
    }

    @Override
    public LCMAudio getFluent() {
        return this;
    }
}
