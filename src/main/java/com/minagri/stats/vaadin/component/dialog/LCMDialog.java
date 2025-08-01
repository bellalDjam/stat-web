package com.minagri.stats.vaadin.component.dialog;

import com.minagri.stats.vaadin.fluent.ComponentFluent;
import com.minagri.stats.vaadin.fluent.HasComponentsFluent;
import com.minagri.stats.vaadin.fluent.HasEnabledFluent;
import com.minagri.stats.vaadin.fluent.HasSizeFluent;
import com.vaadin.flow.component.dialog.Dialog;

public class LCMDialog extends Dialog implements ComponentFluent<LCMDialog>,
        HasSizeFluent<LCMDialog>,
        HasEnabledFluent<LCMDialog>,
        HasComponentsFluent<LCMDialog>,
        IsDialogFluent<LCMDialog> {

    public LCMDialog() {
        setDraggable(true);
    }

    @Override
    public LCMDialog getFluent() {
        return this;
    }
}
