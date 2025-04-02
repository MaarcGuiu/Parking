package persistence.ConfigDao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class ConfigJsonDao implements ConfigDao{

    @Override
    public Config loadAllConfig() {
        File file = new File("src/config.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Type configType = new TypeToken<Config>() {}.getType();
                return new Gson().fromJson(reader, configType);
            } catch (IOException e) {
                throw new RuntimeException("Error al leer config.json", e);
            }
        } else {
            throw new RuntimeException("El archivo config.json no existe");
        }
    }
}
