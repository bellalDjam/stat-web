package com.minagri.stats.vaadin.enums;

import com.vaadin.flow.server.VaadinSession;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Locale;

@Getter
@AllArgsConstructor
public class GroupSite {

    private String descriptionFr;

    private String descriptionNl;

    private List<String> siteCodes;

    public String getIdentifier(){
        if(descriptionFr != null){
            return descriptionFr;
        }
        return "";
    }

    public String getDescription(){
        if (VaadinSession.getCurrent().getLocale().equals(Locale.of("nl", "BE"))){
            return getDescriptionNl();
        }
        else{
            return getDescriptionFr();
        }
    }
}
