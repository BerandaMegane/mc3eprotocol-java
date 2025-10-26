package dev.bocchi_megane.mcprotocol.lib.define;

import dev.bocchi_megane.mcprotocol.lib.util.Bytes;

/**
 * サブヘッダ列挙型
 * MC3Eプロトコルのサブヘッダを定義します（sh080003ah p.42参照）。
 * 要求フレームと応答フレームの識別に使用されます。
 */
public enum SubHeaderEnum {
    /** 要求フレーム用サブヘッダ */
    REQUEST (new Bytes(0x0050, 2)),
    /** 応答フレーム用サブヘッダ */
    RESPONSE(new Bytes(0x00D0, 2)),
    ;

    /** サブヘッダのバイトデータ */
    private final Bytes _bytes;

    /**
     * コンストラクタ
     * 
     * @param bytes サブヘッダのBytesオブジェクト
     */
    SubHeaderEnum(Bytes bytes) {
        _bytes = bytes;
    }

    /**
     * コンストラクタ
     * 
     * @param hexString 16進文字列形式のサブヘッダ値
     */
    SubHeaderEnum(String hexString) {
        _bytes = new Bytes(hexString);
    }

    /**
     * バイト配列からサブヘッダを構築します。
     * 
     * @param bytes サブヘッダのバイト配列
     * @return 対応するサブヘッダ列挙値
     * @throws IllegalArgumentException 不正なサブヘッダまたはnullが指定された場合
     */
    public static SubHeaderEnum build(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("バイト配列がnullです。");
        }
        for (SubHeaderEnum subHeader : values()) {
            if (subHeader.equals(bytes)) {
                return subHeader;
            }
        }
        throw new IllegalArgumentException("サブヘッダが正しくありません。");
    }

    /**
     * サブヘッダのバイトデータを取得します。
     * 
     * @return サブヘッダのBytesオブジェクト
     */
    public Bytes getBytes() {
        return this._bytes;
    }

    /**
     * バイト配列とサブヘッダが一致するかを判定します。
     * 
     * @param bytes 比較対象のバイト配列
     * @return 一致する場合はtrue、それ以外はfalse
     */
    public boolean equals(byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        return java.util.Arrays.equals(this._bytes.getByteArray(), bytes);
    }
}
