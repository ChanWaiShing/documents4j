package no.kantega.pdf.conversion.msoffice;

import com.google.common.base.Objects;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import no.kantega.pdf.throwables.FileSystemInteractionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;

enum MicrosoftWordScript {

    WORD_PDF_CONVERSION_SCRIPT("/word_convert.vbs"),
    WORD_STARTUP_SCRIPT("/word_start.vbs"),
    WORD_SHUTDOWN_SCRIPT("/word_shutdown.vbs"),
    WORD_ASSERT_SCRIPT("/word_assert.vbs");

    private static final Logger LOGGER = LoggerFactory.getLogger(MicrosoftWordBridge.class);
    private static final Random RANDOM = new Random();

    private final String path;

    private MicrosoftWordScript(String path) {
        this.path = path;
    }

    public String getName() {
        return path.substring(1);
    }

    public String getRandomizedName() {
        String name = getName();
        int extensionIndex = name.lastIndexOf('.');
        if (extensionIndex < 0) {
            return String.format("%s%d", name, RANDOM.nextInt());
        } else {
            return String.format("%s%d.%s", name.substring(0, extensionIndex), Math.abs(RANDOM.nextInt()), name.substring(extensionIndex + 1));
        }
    }

    public File materializeIn(File folder) {
        File script = new File(folder, getRandomizedName());
        try {
            if (!script.createNewFile()) {
                throw new IOException(String.format("Could not create file %s", script));
            }
            ByteStreams.copy(
                    Resources.newInputStreamSupplier(Resources.getResource(getClass(), path)),
                    Files.newOutputStreamSupplier(script));
        } catch (IOException e) {
            String message = String.format("Could not copy script resource '%s' to local file system at '%s'", path, folder);
            LOGGER.error(message, e);
            throw new FileSystemInteractionException(message, e);
        }
        return script;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(MicrosoftWordScript.class)
                .add("resource", path)
                .toString();
    }
}
