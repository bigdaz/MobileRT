package puscas.mobilertapp.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Logger;
import javax.annotation.Nonnull;

public final class UtilsBuffer {

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(UtilsBuffer.class.getName());

    /**
     * A private constructor in order to prevent instantiating this helper class.
     */
    private UtilsBuffer() {
        LOGGER.info("UtilsBuffer");
    }

    /**
     * Helper method that resets the position to read the {@link ByteBuffer}.
     * It also checks if the system has at least 1 mega byte free in the main
     * memory.
     *
     * @param byteBuffer The {@link ByteBuffer} to reset.
     */
    public static void resetByteBuffer(@Nonnull final ByteBuffer byteBuffer) {
        LOGGER.info("calculateSceneSize");
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.position(0);
    }

    /**
     * Helper method that allocates a {@link FloatBuffer} with values from a
     * float array received via parameters.
     *
     * @param arrayValues The array containing the values to put in the new
     *                    {@link FloatBuffer}.
     * @return A new {@link FloatBuffer} with the values.
     */
    @Nonnull
    public static FloatBuffer allocateBuffer(@Nonnull final float[] arrayValues) {
        LOGGER.info("allocateBuffer");
        final int byteBufferSize = arrayValues.length * Constants.BYTES_IN_FLOAT;
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(byteBufferSize);
        resetByteBuffer(byteBuffer);
        final FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(arrayValues);
        floatBuffer.position(0);
        return floatBuffer;
    }

}