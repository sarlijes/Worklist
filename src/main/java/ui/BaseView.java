// Code source: https://stackoverflow.com/a/62992234/11466809

package ui;

import javafx.beans.property.MapProperty;
import javafx.collections.ObservableMap;

public class BaseView {

    private LocaleManager lang;

    public BaseView() {
        lang = new LocaleManager();
    }

    public LocaleManager langProperty() {
        return lang;
    }

    public ObservableMap<String, Object> getLang() {
        return lang.get();
    }

    public void setLang(MapProperty<String, Object> resource) {
        this.lang.set(resource);
    }

}