package com.projet.core.impl;

import java.io.InputStream;
import java.util.Map;

import org.hibernate.boot.cfgxml.internal.ConfigLoader;
import org.yaml.snakeyaml.Yaml;

import com.projet.core.IServiceYaml;

public class ServiceYamlImpl implements IServiceYaml {

    private static final String PATH = "config.yaml";
    private Map<String, Object> config;

    @Override
    public Map<String, Object> load() {
        return load(PATH);
    }

    @Override
    public Map<String, Object> load(String filename) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Fichier de configuration non trouvé : " + filename);
            }
            config = yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public String getProperty(String key) {
        return (String) config.get(key);
    }

}
