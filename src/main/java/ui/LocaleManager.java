// Code source: https://stackoverflow.com/a/62992234/11466809

package ui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager extends SimpleMapProperty<String, Object> {

    private String bundleName = "Label";

    public LocaleManager() {
        super(FXCollections.observableHashMap());
        reload();
    }

    public void changeLocale(Locale newLocale) {
        Locale.setDefault(newLocale);
        reload();
    }

    private void reload() {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = bundle.getString(key);

            String[] parts = key.split("\\.");

            MapProperty<String, Object> map = this;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (i == parts.length - 1) {
                    map.put(part, value);
                } else {
                    if (!map.containsKey(part)) {
                        map.put(part, new SimpleMapProperty<>(FXCollections.observableHashMap()));
                    }
                    map = (MapProperty<String, Object>) map.get(part);
                }
            }
        }
    }

    public StringBinding bind(String key) {
        String[] parts = key.split("\\.");

        MapProperty<String, Object> map = this;

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i == parts.length - 1) {
                return Bindings.valueAt(map, part).asString();
            } else {
                if (!map.containsKey(part)) {
                    map.put(part, new SimpleMapProperty<>(FXCollections.observableHashMap()));
                }
                map = (MapProperty<String, Object>) map.get(part);
            }
        }
        throw new NullPointerException("Unknown key : " + key);
    }
}