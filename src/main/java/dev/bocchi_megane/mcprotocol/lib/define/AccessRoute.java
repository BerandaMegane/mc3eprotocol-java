package dev.bocchi_megane.mcprotocol.lib.define;

import dev.bocchi_megane.mcprotocol.lib.util.Converter;

/**
 * アクセス経路クラス
 * MC3Eプロトコルのアクセス経路設定を管理します（sh080003ah p.48参照）。
 * ネットワーク構成に応じてPLCへの通信経路を指定します。
 */
public class AccessRoute {

    /** ネットワーク番号 */
    private byte[] _networkNumberByteArray;
    /** PLC局番号 */
    private byte[] _plcStationNumberByteArray;
    /** 要求先ユニットI/O番号 */
    private byte[] _unitIoNumberByteArray;
    /** 要求先ユニット局番号 */
    private byte[] _unitStationNumberByteArray;

    /**
     * 自局接続時のアクセス経路（デフォルト）
     */
    public static final AccessRoute DEFAULT = new AccessRoute(new byte[] {
        (byte)0x00,  // ネットワーク番号
        (byte)0xFF,  // PLC番号
        (byte)0xFF, 0x03,  // 要求先ユニットI/O番号
        (byte)0x00   // 要求先ユニット局番号
    });

    /**
     * コンストラクタ
     * 
     * @param networkNumber ネットワーク番号
     * @param plcStationNumber PLC局番号
     * @param unitIoNumber 要求先ユニットI/O番号
     * @param unitStationNumber 要求先ユニット局番号
     */
    public AccessRoute(byte networkNumber, byte plcStationNumber, short unitIoNumber, byte unitStationNumber) {
        _networkNumberByteArray     = new byte[] {networkNumber};
        _plcStationNumberByteArray  = new byte[] {plcStationNumber};
        _unitIoNumberByteArray      = Converter.fromIntToByteArray((int)unitIoNumber, 2);
        _unitStationNumberByteArray = new byte[] {unitStationNumber};
    }

    /**
     * コンストラクタ
     * 
     * @param byteArray アクセス経路の5バイトデータ
     * @throws IllegalArgumentException バイト数が5でない場合
     */
    public AccessRoute(byte[] byteArray) {
        if (byteArray.length != 5) {
            throw new IllegalArgumentException("アクセス経路のバイト数が正しくありません。正しくは 5 バイトです。");
        }
        _networkNumberByteArray     = new byte[] {byteArray[0]};
        _plcStationNumberByteArray  = new byte[] {byteArray[1]};
        _unitIoNumberByteArray      = new byte[] {byteArray[2], byteArray[3]};
        _unitStationNumberByteArray = new byte[] {byteArray[4]};
    }

    /**
     * アクセス経路をバイト配列に変換します。
     * 
     * @return アクセス経路のバイト配列
     */
    public byte[] toByteArray() {
        return Converter.concatByteArrays(
            _networkNumberByteArray,
            _plcStationNumberByteArray,
            _unitIoNumberByteArray,
            _unitStationNumberByteArray
        );
    }

    /**
     * アクセス経路の詳細情報を文字列形式で返します。
     * 
     * @return アクセス経路情報の文字列表現
     */
    @Override
    public String toString() {
        return Converter.hereDoc(s->s.trim(), System.lineSeparator(),
            "AccessRoute: {",
            "- networkNumber: "     + Converter.fromBytesToHexString(_networkNumberByteArray),
            "- plcStationNumber: "  + Converter.fromBytesToHexString(_plcStationNumberByteArray),
            "- unitIoNumber: "      + Converter.fromBytesToHexString(_unitIoNumberByteArray),
            "- unitStationNumber: " + Converter.fromBytesToHexString(_unitStationNumberByteArray),
            "}"
        );
    }

    /**
     * ネットワーク番号のバイトデータを取得します。
     * 
     * @return ネットワーク番号
     */
    public byte[] getNetworkNumberByteArray() {
        return _networkNumberByteArray;
    }

    /**
     * PLC局番号のバイトデータを取得します。
     * 
     * @return PLC局番号
     */
    public byte[] getPlcStationNumberByteArray() {
        return _plcStationNumberByteArray;
    }

    /**
     * 要求先ユニットI/O番号のバイトデータを取得します。
     * 
     * @return 要求先ユニットI/O番号
     */
    public byte[] getUnitIoNumberByteArray() {
        return _unitIoNumberByteArray;
    }

    /**
     * 要求先ユニット局番号のバイトデータを取得します。
     * 
     * @return 要求先ユニット局番号
     */
    public byte[] getUnitStationNumberByteArray() {
        return _unitStationNumberByteArray;
    }
}
