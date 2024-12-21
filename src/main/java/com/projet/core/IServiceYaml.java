package com.projet.core;

import java.util.Map;

public interface IServiceYaml {
    Map<String, Object> load();
    Map<String, Object> load(String filename);
    String getProperty(String key);
}
