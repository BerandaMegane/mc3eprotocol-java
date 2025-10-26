package dev.bocchi_megane.mcprotocol.lib.payload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import dev.bocchi_megane.mcprotocol.lib.define.DeviceSpec;

class BlockWordReadResponseTest {

    @Test
    @DisplayName("正常なワードデバイス応答データの解析テスト")
    void testParseValidWordResponse() {
        // Arrange: ワードデバイス（D200）の読込み要求を作成
        DeviceSpec deviceSpec = new DeviceSpec("D200");
        short devicePoint = 2;  // 2点読込み
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        // リトルエンディアン形式: 0x0001 (1) と 0x0002 (2)
        byte[] responseData = {0x01, 0x00, 0x02, 0x00};
        
        // Act: 応答オブジェクトを作成
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        // Assert: 期待値と実際の値を比較
        short[] expectedData = {1, 2};
        assertArrayEquals(expectedData, response.getReadData(), "読込みデータが期待値と一致しません");
    }

    @Test
    @DisplayName("単一ワードデバイス応答データの解析テスト")
    void testParseSingleWordResponse() {
        // Arrange: 単一ワードデバイスの読込み要求
        DeviceSpec deviceSpec = new DeviceSpec("D100");
        short devicePoint = 1;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        // リトルエンディアン形式: 0x1234 (4660)
        byte[] responseData = {0x34, 0x12};
        
        // Act
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        // Assert
        short[] expectedData = {4660};
        assertArrayEquals(expectedData, response.getReadData(), "単一ワードの読込みデータが期待値と一致しません");
    }

    @Test
    @DisplayName("複数ワードデバイス応答データの解析テスト")
    void testParseMultipleWordResponse() {
        // Arrange: 複数ワードデバイスの読込み要求
        DeviceSpec deviceSpec = new DeviceSpec("D0");
        short devicePoint = 4;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        // リトルエンディアン形式: 0x0100, 0x0200, 0x0300, 0x0400
        byte[] responseData = {
            0x00, 0x01,  // 256
            0x00, 0x02,  // 512
            0x00, 0x03,  // 768
            0x00, 0x04   // 1024
        };
        
        // Act
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        // Assert
        short[] expectedData = {256, 512, 768, 1024};
        assertArrayEquals(expectedData, response.getReadData(), "複数ワードの読込みデータが期待値と一致しません");
    }

    @Test
    @DisplayName("負の値を含むワードデバイス応答データの解析テスト")
    void testParseNegativeWordResponse() {
        // Arrange: 負の値を含むワードデバイスの読込み要求
        DeviceSpec deviceSpec = new DeviceSpec("D500");
        short devicePoint = 2;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        // リトルエンディアン形式: 0xFFFF (-1) と 0x8000 (-32768)
        byte[] responseData = {(byte)0xFF, (byte)0xFF, 0x00, (byte)0x80};
        
        // Act
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        // Assert
        short[] expectedData = {-1, -32768};
        assertArrayEquals(expectedData, response.getReadData(), "負の値を含む読込みデータが期待値と一致しません");
    }

    @Test
    @DisplayName("ビットデバイス要求に対する例外発生テスト")
    void testThrowExceptionForBitDeviceRequest() {
        // Arrange: ビットデバイス（M0）の読込み要求を作成
        DeviceSpec deviceSpec = new DeviceSpec("M0");
        short devicePoint = 1;
        boolean isBitDevice = true;  // ビットデバイス
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x00};

        // Act & Assert: ビットデバイス要求に対して例外が発生することを確認
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new BlockWordReadResponse(responseData, request);
        }, "ビットデバイス要求に対して例外が発生しませんでした");
        
        assertEquals("ビットデバイスの読み込み要求に対しては、別のクラスを使ってください", 
                     exception.getMessage(), "例外メッセージが期待値と一致しません");
    }

    @Test
    @DisplayName("空の応答データの解析テスト")
    void testParseEmptyResponse() {
        // Arrange: 空の応答データ
        DeviceSpec deviceSpec = new DeviceSpec("D300");
        short devicePoint = 0;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {};
        
        // Act
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        // Assert
        short[] expectedData = {};
        assertArrayEquals(expectedData, response.getReadData(), "空の応答データの処理が正しくありません");
    }

    @Test
    @DisplayName("toBytesメソッドのテスト")
    void testToBytes() {
        // Arrange
        DeviceSpec deviceSpec = new DeviceSpec("D400");
        short devicePoint = 2;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] originalData = {0x34, 0x12, (byte)0xCD, (byte)0xAB};
        BlockWordReadResponse response = new BlockWordReadResponse(originalData, request);

        // Act: バイト配列に変換
        byte[] resultBytes = response.toBytes();

        // Assert: 元のデータと一致することを確認
        assertArrayEquals(originalData, resultBytes, "toBytesメソッドが正しいバイト配列を返しません");
    }

    @Test
    @DisplayName("getReadDataメソッドの一貫性テスト")
    void testGetReadDataConsistency() {
        // Arrange
        DeviceSpec deviceSpec = new DeviceSpec("D600");
        short devicePoint = 2;
        boolean isBitDevice = false;
        BlockReadRequest request = new BlockReadRequest(deviceSpec, devicePoint, isBitDevice);
        
        byte[] responseData = {0x01, 0x00, 0x02, 0x00};
        BlockWordReadResponse response = new BlockWordReadResponse(responseData, request);

        // Act: データを複数回取得
        short[] readData1 = response.getReadData();
        short[] readData2 = response.getReadData();

        // Assert: 複数回取得しても同じ内容が返されることを確認
        assertArrayEquals(readData1, readData2, "getReadDataメソッドが一貫した結果を返しません");
        assertEquals(2, readData1.length, "読込みデータの長さが期待値と一致しません");
        assertEquals(1, readData1[0], "1番目のデータが期待値と一致しません");
        assertEquals(2, readData1[1], "2番目のデータが期待値と一致しません");
    }
}