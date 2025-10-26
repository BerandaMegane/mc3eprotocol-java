package dev.bocchi_megane.mcprotocol.lib.define;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SubHeaderEnumクラスのテストクラス
 */
class SubHeaderEnumTest {

    @Test
    @DisplayName("REQUESTサブヘッダの値確認")
    void testRequestSubHeader() {
        SubHeaderEnum request = SubHeaderEnum.REQUEST;
        byte[] expectedBytes = {0x50, 0x00}; // リトルエンディアン
        
        assertArrayEquals(expectedBytes, request.getBytes().getByteArray());
        assertTrue(request.equals(expectedBytes));
    }

    @Test
    @DisplayName("RESPONSEサブヘッダの値確認")
    void testResponseSubHeader() {
        SubHeaderEnum response = SubHeaderEnum.RESPONSE;
        byte[] expectedBytes = {(byte)0xD0, 0x00}; // リトルエンディアン
        
        assertArrayEquals(expectedBytes, response.getBytes().getByteArray());
        assertTrue(response.equals(expectedBytes));
    }

    @Test
    @DisplayName("build()メソッドの正常ケース - REQUEST")
    void testBuildWithRequestBytes() {
        byte[] requestBytes = {0x50, 0x00};
        SubHeaderEnum result = SubHeaderEnum.build(requestBytes);
        
        assertEquals(SubHeaderEnum.REQUEST, result);
    }

    @Test
    @DisplayName("build()メソッドの正常ケース - RESPONSE")
    void testBuildWithResponseBytes() {
        byte[] responseBytes = {(byte)0xD0, 0x00};
        SubHeaderEnum result = SubHeaderEnum.build(responseBytes);
        
        assertEquals(SubHeaderEnum.RESPONSE, result);
    }

    @Test
    @DisplayName("build()メソッドの異常ケース - 不正なバイト配列")
    void testBuildWithInvalidBytes() {
        byte[] invalidBytes = {0x12, 0x34};
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> SubHeaderEnum.build(invalidBytes)
        );
        
        assertEquals("サブヘッダが正しくありません。", exception.getMessage());
    }

    @Test
    @DisplayName("build()メソッドの異常ケース - nullバイト配列")
    void testBuildWithNullBytes() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> SubHeaderEnum.build(null)
        );
        
        assertEquals("バイト配列がnullです。", exception.getMessage());
    }

    @Test
    @DisplayName("build()メソッドの異常ケース - 長さが異なるバイト配列")
    void testBuildWithWrongLengthBytes() {
        byte[] shortBytes = {0x50};
        byte[] longBytes = {0x50, 0x00, 0x01};
        
        assertThrows(IllegalArgumentException.class, () -> SubHeaderEnum.build(shortBytes));
        assertThrows(IllegalArgumentException.class, () -> SubHeaderEnum.build(longBytes));
    }

    @Test
    @DisplayName("equals()メソッドの正常ケース")
    void testEqualsWithValidBytes() {
        byte[] requestBytes = {0x50, 0x00};
        byte[] responseBytes = {(byte)0xD0, 0x00};
        
        assertTrue(SubHeaderEnum.REQUEST.equals(requestBytes));
        assertFalse(SubHeaderEnum.REQUEST.equals(responseBytes));
        
        assertTrue(SubHeaderEnum.RESPONSE.equals(responseBytes));
        assertFalse(SubHeaderEnum.RESPONSE.equals(requestBytes));
    }

    @Test
    @DisplayName("equals()メソッドの異常ケース - nullバイト配列")
    void testEqualsWithNullBytes() {
        assertFalse(SubHeaderEnum.REQUEST.equals(null));
        assertFalse(SubHeaderEnum.RESPONSE.equals(null));
    }

    @Test
    @DisplayName("equals()メソッドの異常ケース - 不正なバイト配列")
    void testEqualsWithInvalidBytes() {
        byte[] invalidBytes = {0x12, 0x34};
        
        assertFalse(SubHeaderEnum.REQUEST.equals(invalidBytes));
        assertFalse(SubHeaderEnum.RESPONSE.equals(invalidBytes));
    }

    @Test
    @DisplayName("getBytes()メソッドの戻り値確認")
    void testGetBytes() {
        assertNotNull(SubHeaderEnum.REQUEST.getBytes());
        assertNotNull(SubHeaderEnum.RESPONSE.getBytes());
        
        assertEquals(2, SubHeaderEnum.REQUEST.getBytes().getLength());
        assertEquals(2, SubHeaderEnum.RESPONSE.getBytes().getLength());
    }

    @Test
    @DisplayName("列挙値の数確認")
    void testEnumValues() {
        SubHeaderEnum[] values = SubHeaderEnum.values();
        assertEquals(2, values.length);
        
        assertTrue(java.util.Arrays.asList(values).contains(SubHeaderEnum.REQUEST));
        assertTrue(java.util.Arrays.asList(values).contains(SubHeaderEnum.RESPONSE));
    }

    @Test
    @DisplayName("列挙値の一意性確認")
    void testEnumUniqueness() {
        byte[] requestBytes = SubHeaderEnum.REQUEST.getBytes().getByteArray();
        byte[] responseBytes = SubHeaderEnum.RESPONSE.getBytes().getByteArray();
        
        assertFalse(java.util.Arrays.equals(requestBytes, responseBytes));
    }

    @Test
    @DisplayName("境界値テスト - バイト値の範囲")
    void testByteValueRanges() {
        // REQUEST: 0x0050 = 80
        byte[] requestBytes = SubHeaderEnum.REQUEST.getBytes().getByteArray();
        assertEquals(0x50, requestBytes[0] & 0xFF);
        assertEquals(0x00, requestBytes[1] & 0xFF);
        
        // RESPONSE: 0x00D0 = 208  
        byte[] responseBytes = SubHeaderEnum.RESPONSE.getBytes().getByteArray();
        assertEquals(0xD0, responseBytes[0] & 0xFF);
        assertEquals(0x00, responseBytes[1] & 0xFF);
    }
}
