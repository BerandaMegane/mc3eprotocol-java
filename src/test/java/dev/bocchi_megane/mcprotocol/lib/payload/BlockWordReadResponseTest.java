package dev.bocchi_megane.mcprotocol.lib.payload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dev.bocchi_megane.mcprotocol.lib.define.DeviceSpec;

class BlockWordReadResponseTest {

    @Test
    void testParseValidResponse() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x00, 0x02, 0x00};
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        short[] expectedData = {1, 2};
        assertArrayEquals(expectedData, response.getReadData());
    }

    @Test
    void testParseInvalidResponse() {
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 1;
        boolean isBitDevice = true;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x00, 0x02, 0x00};

        assertThrows(IllegalArgumentException.class, () -> {
            new BlockWordReadResponse(responseData, request);
        });
    }
}