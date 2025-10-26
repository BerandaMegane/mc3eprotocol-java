package dev.bocchi_megane.mcprotocol.lib.define;

import java.util.Arrays;

import dev.bocchi_megane.mcprotocol.lib.util.Bytes;

/**
 * デバイスコード列挙型
 * PLCの各種デバイスコードを定義します（sh080003ah p.68参照）。
 * デバイスの種別、データ型、番号の進数を管理します。
 */
public enum DeviceCodeEnum {
    /**
     * 特殊リレー
     * 記号: SM
     */
    SpecialRelay("SM", DataTypeEnum.BIT, 10, new Bytes("91")),

    /**
     * 特殊レジスター
     * 記号: SD
     */
    SpecialRegister("SD", DataTypeEnum.WORD, 10, new Bytes("A9")),

    /**
     * 入力
     * 記号: X
     */
    Input("X", DataTypeEnum.BIT, 16, new Bytes("9C")),

    /**
     * 出力
     * 記号: Y
     */
    Output("Y", DataTypeEnum.BIT, 16, new Bytes("9D")),

    /**
     * 内部リレー
     * 記号: M
     */
    InternalRelay("M", DataTypeEnum.BIT, 10, new Bytes("90")),

    /**
     * ラッチリレー
     * 記号: L
     */
    LatchRelay("L", DataTypeEnum.BIT, 10, new Bytes("92")),

    /**
     * アナンシェーター
     * 記号: F
     */
    Annunciator("F", DataTypeEnum.BIT, 10, new Bytes("93")),

    /**
     * エッジリレー
     * 記号: V
     */
    EdgeRelay("V", DataTypeEnum.BIT, 10, new Bytes("94")),

    /**
     * リンクリレー
     * 記号: B
     */
    LinkRelay("B", DataTypeEnum.BIT, 16, new Bytes("A0")),

    /**
     * データレジスター
     * 記号: D
     */
    DataRegister("D", DataTypeEnum.WORD, 10, new Bytes("A8")),

    /**
     * リンクレジスター
     * 記号: W
     */
    LinkRegister("W", DataTypeEnum.WORD, 16, new Bytes("B4")),

    /**
     * タイマー(接点)
     * 記号: TS
     */
    TimerContact("TS", DataTypeEnum.BIT, 10, new Bytes("C1")),

    /**
     * タイマー(コイル)
     * 記号: TC
     */
    TimerCoil("TC", DataTypeEnum.BIT, 10, new Bytes("C0")),

    /**
     * タイマー(現在値)
     * 記号: TN
     */
    TimerCurrentValue("TN", DataTypeEnum.WORD, 10, new Bytes("C2")),

    /**
     * ロングタイマー(接点)
     * 記号: LTS
     */
    LongTimerContact("LTS", DataTypeEnum.BIT, 10, new Bytes("51")),

    /**
     * ロングタイマー(コイル)
     * 記号: LTC
     */
    LongTimerCoil("LTC", DataTypeEnum.BIT, 10, new Bytes("50")),

    /**
     * ロングタイマー(現在値)
     * 記号: LTN
     */
    LongTimerCurrentValue("LTN", DataTypeEnum.DWORD, 10, new Bytes("52")),

    /**
     * 積算タイマー(接点)
     * 記号: STS
     */
    RetentiveTimerContact("STS", DataTypeEnum.BIT, 10, new Bytes("C7")),

    /**
     * 積算タイマー(コイル)
     * 記号: STC
     */
    RetentiveTimerCoil("STC", DataTypeEnum.BIT, 10, new Bytes("C6")),

    /**
     * 積算タイマー(現在値)
     * 記号: STN
     */
    RetentiveTimerCurrentValue("STN", DataTypeEnum.WORD, 10, new Bytes("C8")),

    /**
     * ロング積算タイマー(接点)
     * 記号: LSTS
     */
    LongRetentiveTimerContact("LSTS", DataTypeEnum.BIT, 10, new Bytes("59")),

    /**
     * ロング積算タイマー(コイル)
     * 記号: LSTC
     */
    LongRetentiveTimerCoil("LSTC", DataTypeEnum.BIT, 10, new Bytes("58")),

    /**
     * ロング積算タイマー(現在値)
     * 記号: LSTN
     */
    LongRetentiveTimerCurrentValue("LSTN", DataTypeEnum.DWORD, 10, new Bytes("5A")),

    /**
     * カウンター(接点)
     * 記号: CS
     */
    CounterContact("CS", DataTypeEnum.BIT, 10, new Bytes("C4")),

    /**
     * カウンター(コイル)
     * 記号: CC
     */
    CounterCoil("CC", DataTypeEnum.BIT, 10, new Bytes("C3")),

    /**
     * カウンター(現在値)
     * 記号: CN
     */
    CounterCurrentValue("CN", DataTypeEnum.WORD, 10, new Bytes("C5")),

    /**
     * ロングカウンター(接点)
     * 記号: LCS
     */
    LongCounterContact("LCS", DataTypeEnum.BIT, 10, new Bytes("55")),

    /**
     * ロングカウンター(コイル)
     * 記号: LCC
     */
    LongCounterCoil("LCC", DataTypeEnum.BIT, 10, new Bytes("54")),

    /**
     * ロングカウンター(現在値)
     * 記号: LCN
     */
    LongCounterCurrentValue("LCN", DataTypeEnum.DWORD, 10, new Bytes("56")),
    
    /**
     * リンク特殊リレー
     * 記号: SB
     */
    LinkSpecialRelay("SB", DataTypeEnum.BIT, 16, new Bytes("A1")),

    /**
     * リンク特殊レジスター
     * 記号: SW
     */
    LinkSpecialRegister("SW", DataTypeEnum.WORD, 16, new Bytes("B5")),

    /**
     * ステップリレー
     * 記号: S
     */
    StepRelay("S", DataTypeEnum.BIT, 10, new Bytes("98")),

    /**
     * ダイレクトアクセス入力
     * 記号: DX
     */
    DirectAccessInput("DX", DataTypeEnum.BIT, 16, new Bytes("A2")),

    /**
     * ダイレクトアクセス出力
     * 記号: DY
     */
    DirectAccessOutput("DY", DataTypeEnum.BIT, 16, new Bytes("A3")),

    /**
     * インデックスレジスター
     * 記号: Z
     */
    IndexRegister("Z", DataTypeEnum.WORD, 10, new Bytes("CC")),

    /**
     * ロングインデックスレジスター
     * 記号: LZ
     */
    LongIndexRegister("LZ", DataTypeEnum.DWORD, 10, new Bytes("62")),

    /**
     * ファイルレジスター(ブロック切換え方式)
     * 記号: R
     */
    FileRegisterBlockSwitchingMethod("R", DataTypeEnum.WORD, 10, new Bytes("AF")),

    /**
     * ファイルレジスター(連番アクセス方式)
     * 記号: ZR
     */
    FileRegisterSerialNumberAccessMethod("ZR", DataTypeEnum.WORD, 16, new Bytes("B0")),

    /**
     * 拡張データレジスター
     * 記号: D
     */
    // ExtendedDataRegister("D", DataTypeEnum.WORD, 10, new Bytes("A8")),

    /**
     * 拡張リンクレジスター
     * 記号: W
     */
    // ExtendedLinkRegister("W", DataTypeEnum.WORD, 16, new Bytes("B4")),

    /** リフレッシュデータレジスタ
     * 記号: RD
     */
    RefreshDataRegister("RD", DataTypeEnum.WORD, 10, new Bytes("2C")),
    ;

    /** デバイス記号 */
    private final String _symbol;
    /** データ型 */
    private final DataTypeEnum _dataType;
    /** デバイス番号の基数（10進数または16進数） */
    private final int _deviceNumberBase;
    /** デバイスコードのバイトデータ */
    private final Bytes _deviceCodeBytes;

    /**
     * コンストラクタ
     * 
     * @param symbol デバイス記号
     * @param dataType データ型
     * @param deviceNumberBase デバイス番号の基数
     */
    DeviceCodeEnum(String symbol, DataTypeEnum dataType, int deviceNumberBase, Bytes deviceCodeBytes) {
        this._symbol = symbol;
        this._dataType = dataType;
        this._deviceNumberBase = deviceNumberBase;
        this._deviceCodeBytes = deviceCodeBytes;
    }

    /**
     * バイト配列からデバイスコードを構築します。
     * 
     * @param bytes デバイスコードのバイト配列
     * @return 対応するデバイスコード列挙値
     * @throws IllegalArgumentException 指定されたデバイスコードが存在しない場合
     */
    public static DeviceCodeEnum build(byte[] bytes) {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values()) {
            if (Arrays.equals(deviceCode.getBytes().getByteArray(), bytes)) {
                return deviceCode;
            }
        }
        throw new IllegalArgumentException("指定のデバイスコードは存在しません。");
    }
    
    /**
     * デバイス記号からデバイスコードを構築します。
     * 
     * @param deviceSymbol デバイス記号文字列
     * @return 対応するデバイスコード列挙値
     * @throws IllegalArgumentException 指定されたデバイスコードが存在しない場合
     */
    public static DeviceCodeEnum build(String deviceSymbol) {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values()) {
            if (deviceCode.getDeviceSymbol().equals(deviceSymbol)) {
                return deviceCode;
            }
        }
        throw new IllegalArgumentException("指定のデバイスコードは存在しません。");
    }

    /**
     * 指定された記号のデバイスコードが存在するかを確認します。
     * 
     * @param target 確認する記号文字列
     * @return 存在する場合はtrue、それ以外はfalse
     */
    public static boolean containsCodeString(String target) {
        return Arrays.stream(DeviceCodeEnum.values()).anyMatch(r -> r.getDeviceSymbol().equals(target));
    }

    /**
     * デバイス指定の全体バイト長を取得します。
     * 
     * @return デバイス番号長 + デバイスコード長
     */
    public static int getLength() {
        return getDeviceNumberLength() + getDeviceCodeLength();
    }

    /**
     * デバイス番号のバイト長を取得します。
     * 
     * @return デバイス番号のバイト長
     */
    public static int getDeviceNumberLength() {
        return 3;
    }
    
    /**
     * デバイスコードのバイト長を取得します。
     * 
     * @return デバイスコードのバイト長
     */
    public static int getDeviceCodeLength() {
        return 1;
    }

    /**
     * デバイス記号を取得します。
     * 
     * @return デバイス記号文字列
     */
    public String getDeviceSymbol() {
        return _symbol;
    }

    /**
     * データ型を取得します。
     * 
     * @return データ型
     */
    public DataTypeEnum getDataType() {
        return _dataType;
    }

    /**
     * デバイスコードのバイトデータを取得します。
     * 
     * @return デバイスコードのBytesオブジェクト
     */
    public Bytes getBytes() {
        return _deviceCodeBytes;
    }

    /**
     * デバイス番号の基数を取得します。
     * 例：入力リレー X は16進数、データレジスタ D は10進数
     * 
     * @return デバイス番号の基数（10または16）
     */
    public int getDeviceNumberBase() {
        return _deviceNumberBase;
    }
}
