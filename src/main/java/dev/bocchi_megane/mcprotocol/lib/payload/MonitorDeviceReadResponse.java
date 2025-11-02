package dev.bocchi_megane.mcprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import dev.bocchi_megane.mcprotocol.lib.define.DeviceSpec;
import dev.bocchi_megane.mcprotocol.lib.util.Converter;;

/**
 * モニタデバイス読み取り応答クラス
 * MonitorDeviceReadRequest に対する応答を処理するクラスです。
 */
public class MonitorDeviceReadResponse extends AbstractResponse {
    /** ワードデータ点数 */
    private int _wordDataPoint;
    /** ダブルワードデータ点数 */
    private int _dwordDataPoint;
    /** ワードデータ配列 */
    private short[] _wordData;
    /** ダブルワードデータ配列 */
    private int[] _dwordData;
    
    /** ワードデバイス値取得 */
    private Map<DeviceSpec, Short> _wordDataMap;
    /** ダブルワードデバイス値取得 */
    private Map<DeviceSpec, Integer> _dwordDataMap;
    /** 対応する登録要求オブジェクト */
    private MonitorDeviceRegisterRequest _registerRequest;

    /**
     * コンストラクタ
     * 応答データを解析してワード読込み応答オブジェクトを作成します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する読込み要求オブジェクト
     * @param registerRequest 対応する登録要求オブジェクト
     */
    public MonitorDeviceReadResponse(
        byte[] responseData,
        MonitorDeviceReadRequest readRequest,
        MonitorDeviceRegisterRequest registerRequest
    ) {
        _registerRequest = registerRequest;
        _wordDataPoint  = registerRequest.getWordDevicePoint();
        _dwordDataPoint = registerRequest.getDwordDevicePoint();
        parse(responseData, readRequest);
    }

    @Override
    public void parse(byte[] responseData, AbstractRequest request) {
        // バッファ
        ByteBuffer buffer = ByteBuffer.wrap(responseData);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // ワードデータ読み込み
        _wordData = new short[_wordDataPoint];
        for (int i = 0; i < _wordDataPoint; i++) {
            _wordData[i] = buffer.getShort();
        }

        // ダブルワードデータ読み込み
        _dwordData = new int[_dwordDataPoint];
        for (int i = 0; i < _dwordDataPoint; i++) {
            _dwordData[i] = buffer.getInt();
        }

        // バッファが残っている場合は例外
        if (buffer.remaining() > 0) {
            throw new IllegalArgumentException("応答データの解析に失敗しました: 不明な余剰データがあります");
        }

        // ワードデバイス値マップ作成
        for (int i = 0; i < _wordDataPoint; i++) {
            DeviceSpec deviceSpec = _registerRequest.getWordDeviceSpecs()[i];
            if (_wordDataMap == null) {
                _wordDataMap = new java.util.HashMap<>();
            }
            _wordDataMap.put(deviceSpec, _wordData[i]);
        }

        // ダブルワードデバイス値マップ作成
        for (int i = 0; i < _dwordDataPoint; i++) {
            DeviceSpec deviceSpec = _registerRequest.getDwordDeviceSpecs()[i];
            if (_dwordDataMap == null) {
                _dwordDataMap = new java.util.HashMap<>();
            }
            _dwordDataMap.put(deviceSpec, _dwordData[i]);
        }
    }

    /**
     * 要求データをバイト配列に変換します。
     *
     * @return 要求データのバイト配列（監視タイマーより後ろ）
     */
    @Override
    public byte[] toByteArray() {
        return Converter.concatByteArrays(
            Converter.fromShortArrayToBytes(_wordData),
            Converter.fromIntArrayToBytes(_dwordData)
        );
    }

    /**
     * ワードデバイス値マップを取得します。
     *
     * @return ワードデバイス値マップ
     */
    public short getWordData(DeviceSpec deviceSpec) {
        return _wordDataMap.get(deviceSpec);
    }
    /**
     * ダブルワードデバイス値マップを取得します。
     *
     * @return ダブルワードデバイス値マップ
     */
    public int getDwordData(DeviceSpec deviceSpec) {
        return _dwordDataMap.get(deviceSpec);
    }
}
