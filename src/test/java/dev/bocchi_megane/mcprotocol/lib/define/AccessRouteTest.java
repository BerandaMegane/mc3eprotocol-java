package dev.bocchi_megane.mcprotocol.lib.define;
import org.junit.jupiter.api.Test;

import dev.bocchi_megane.mcprotocol.lib.util.Converter;

import static org.junit.jupiter.api.Assertions.*;

public class AccessRouteTest {

    @Test
    public void testAccessRouteWithBytes() {
        byte[] bytes = {0x00, (byte)0xff, (byte)0xff, 0x03, 0x00};

        AccessRoute route = new AccessRoute(bytes);

        assertArrayEquals(new byte[] {0x00}, route.getNetworkNumberByteArray());
        assertArrayEquals(new byte[] {(byte)0xff}, route.getPlcStationNumberByteArray());
        assertArrayEquals(new byte[] {(byte)0xff, 0x03}, route.getUnitIoNumberByteArray());
        assertArrayEquals(new byte[] {0x00}, route.getUnitStationNumberByteArray());
    }

    @Test
    public void testToBytes() {
        byte[] bytes = {0x00, (byte)0xff, (byte)0xff, 0x03, 0x00};

        AccessRoute route = new AccessRoute(bytes);

        byte[] expectedBytes = Converter.concatByteArrays(
            new byte[] {0x00},
            new byte[] {(byte)0xff},
            new byte[] {(byte)0xff, 0x03},
            new byte[] {0x00}
        );

        assertArrayEquals(expectedBytes, route.toByteArray());
    }

    @Test
    public void testInvalidByteArrayLength() {
        byte[] invalidBytes = {0x01, 0x02, 0x03, 0x04};

        assertThrows(IllegalArgumentException.class, () -> {
            new AccessRoute(invalidBytes);
        });
    }

    @Test
    public void testDefaultAccessRoute() {
        AccessRoute route = AccessRoute.DEFAULT;

        assertArrayEquals(new byte[]{(byte)0x00}, route.getNetworkNumberByteArray());
        assertArrayEquals(new byte[]{(byte)0xFF}, route.getPlcStationNumberByteArray());
        assertArrayEquals(new byte[]{(byte)0xFF, 0x03}, route.getUnitIoNumberByteArray());
        assertArrayEquals(new byte[]{(byte)0x00}, route.getUnitStationNumberByteArray());
    }
}
