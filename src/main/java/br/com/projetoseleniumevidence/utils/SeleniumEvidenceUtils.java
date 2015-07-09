package br.com.projetoseleniumevidence.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SeleniumEvidenceUtils {
	
	static Properties properties;
	
    public static Properties carregarPropriedades() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("init.properties"));

        return properties;
    }
}
