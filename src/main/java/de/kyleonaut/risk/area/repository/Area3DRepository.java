package de.kyleonaut.risk.area.repository;

import de.kyleonaut.risk.RiskPlugin;
import de.kyleonaut.risk.area.holder.Area3DHolder;
import de.kyleonaut.risk.area.model.Area3D;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Area3DRepository {
    private final RiskPlugin plugin;
    private final Area3DHolder area3DHolder;

    public void load() {
        final File areaFile = new File(new File(plugin.getDataFolder(), "data"), "game_area.area3d");
        if (!areaFile.exists()) {
            area3DHolder.setArea3D(null);
            return;
        }
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(areaFile));
        ) {
            final StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            byte[] bytes = Base64Coder.decodeLines(stringBuilder.toString());
            try (
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ) {
                objectInputStream.readInt();
                Area3D area3D = (Area3D) objectInputStream.readObject();
                area3DHolder.setArea3D(area3D);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Area3D area3D) {
        area3DHolder.setArea3D(area3D);
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeInt(7);
            objectOutputStream.writeObject(area3D);
            objectOutputStream.close();
            String serializedArea3D = Base64Coder.encodeLines(outputStream.toByteArray());
            final File parentDir = plugin.getDataFolder();
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }
            final File dataFolder = new File(parentDir, "data");
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }
            final File areaFile = new File(dataFolder, "game_area.area3d");
            if (areaFile.exists()) {
                areaFile.createNewFile();
            }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(areaFile), StandardCharsets.UTF_8))) {
                writer.write(serializedArea3D);
                Bukkit.getLogger().log(Level.INFO, "[Risk] The game area was updated.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

