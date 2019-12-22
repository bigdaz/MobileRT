package puscas.mobilertapp;

import java.util.logging.Logger;

import static puscas.mobilertapp.ConstantsMethods.GET_NAMES;

/**
 * The available scenes for the Ray Tracer engine.
 */
enum Scene {

    /**
     * The Cornell Box scene.
     */
    CORNELL("Cornell"),

    /**
     * The Spheres scene.
     */
    SPHERES("Spheres"),

    /**
     * The 2nd Cornell Box scene.
     */
    CORNELL2("Cornell2"),

    /**
     * The 2nd Spheres scene.
     */
    SPHERES2("Spheres2"),

    /**
     * The Scene of an OBJ file.
     */
    OBJ("OBJ"),

    /**
     * A test scene.
     */
    TEST("Test"),

    /**
     * A scene of an OBJ file which doesn't exist.
     */
    WRONGFILE("Wrong file");

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(Scene.class.getName());

    /**
     * @see Scene#getName()
     */
    private final String name;

    /**
     * The constructor for this {@link Enum}.
     *
     * @param name The new scene for the Ray Tracer engine.
     */
    Scene(final String name) {
        this.name = name;
    }

    /**
     * Gets the name of the scene for the Ray Tracer engine.
     */
    String getName() {
        return this.name;
    }

    /**
     * Gets the names of all available scenes.
     */
    static String[] getNames() {
        LOGGER.info(GET_NAMES);

        final Scene[] scenes = values();
        final int lengthScenes = scenes.length;
        final String[] namesScenes = new String[lengthScenes];

        for (int i = 0; i < lengthScenes; i++) {
            namesScenes[i] = scenes[i].getName();
        }

        return namesScenes;
    }
}