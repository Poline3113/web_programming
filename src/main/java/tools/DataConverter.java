package tools;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("dataConverter")
public class DataConverter implements Converter<String> {
    @Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return prepareString(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return "";
        }
        return prepareString(value);
    }

    public static String prepareString(String s) {
        return s.replace(',', '.');
    }
}