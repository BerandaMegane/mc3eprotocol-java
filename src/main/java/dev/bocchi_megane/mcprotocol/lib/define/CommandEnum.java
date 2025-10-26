package dev.bocchi_megane.mcprotocol.lib.define;

import dev.bocchi_megane.mcprotocol.lib.util.Bytes;

/**
 * コマンドコード列挙型
 * MC3Eプロトコルのコマンドコードを定義します（sh080003ah p.61参照）。
 * デバイスアクセスやユニット制御などの各種コマンドを提供します。
 */
public enum CommandEnum {
    // *** デバイスアクセス ***
    /**
     * 一括読出し（ブロック読出し）
     */
    BLOCK_READ (new Bytes(0x0401, 2)),
    /**
     * 一括書込み（ブロック書込み）
     */
    BLOCK_WRITE(new Bytes(0x1401, 2)),

    /**
     * ランダム読出し
     */
    RANDOM_READ (new Bytes(0x0403, 2)),
    /**
     * ランダム書込み
     */
    RANDOM_WRITE(new Bytes(0x1402, 2)),

    /**
     * 複数ブロック一括読出し
     */
    MULTI_BLOCK_READ (new Bytes(0x0406, 2)),
    /**
     * 複数ブロック一括書込み
     */
    MULTI_BLOCK_WRITE(new Bytes(0x1406, 2)),
    
    /**
     * モニタデータ登録
     */
    MONITOR_REGISTER(new Bytes(0x0801, 2)),
    /**
     * モニタデータ読出し
     */
    MONITOR_READ    (new Bytes(0x0802, 2)),

    // *** ユニット制御 ***
    /**
     * リモートRUN
     */
    REMOTE_RUN  (new Bytes(0x1001, 2)),
    /**
     * リモートSTOP
     */
    REMOTE_STOP (new Bytes(0x1002, 2)),
    /**
     * リモートPAUSE
     */
    REMOTE_PAUSE(new Bytes(0x1003, 2)),
    /**
     * リモートラッチクリア
     */
    REMOTE_LATCH_CLEAR(new Bytes(0x1005, 2)),
    /**
     * リモートリセット
     */
    REMOTE_RESET      (new Bytes(0x1006, 2)),
    /**
     * CPU形名読出し
     */
    CPU_TYPE_READ(new Bytes(0x0101, 2)),
    /**
     * 折返しテスト
     */
    ECHO_TEST(new Bytes(0x0619, 2)),
    ;

    /** コマンドのバイトデータ */
    private final Bytes _bytes;

    /**
     * コンストラクタ
     * @param bytes コマンドコードのBytesオブジェクト
     */
    CommandEnum(Bytes bytes) {
        this._bytes = bytes;
    }
    
    /**
     * コンストラクタ
     * 
     * @param hexString 16進文字列形式のコマンドコード
     */
    CommandEnum(String hexString) {
        this._bytes = new Bytes(hexString);
    }

    /**
     * バイト配列からコマンドを構築します。
     * 
     * @param byteArray コマンドコードのバイト配列
     * @return 対応するコマンド列挙値
     * @throws IllegalArgumentException バイト配列がnullまたは指定されたコードのコマンドが存在しない場合
     */
    public static CommandEnum build(byte[] byteArray) {
        if (byteArray == null) {
            throw new IllegalArgumentException("バイト配列がnullです。");
        }
        for (CommandEnum command : CommandEnum.values()) {
            if (command.equals(byteArray)) {
                return command;
            }
        }
        throw new IllegalArgumentException("指定されたコードのコマンドは存在しません");
    }

    /**
     * バイト配列とコマンドが一致するかを判定します。
     * 
     * @param byteArray 比較対象のバイト配列
     * @return 一致する場合はtrue、それ以外はfalse（nullの場合もfalse）
     */
    public boolean equals(byte[] byteArray) {
        if (byteArray == null) {
            return false;
        }
        return _bytes.equals(byteArray);
    }
    
    /**
     * コマンドのバイトデータを取得します。
     * 
     * @return コマンドのBytesオブジェクト
     */
    public Bytes getBytes() {
        return _bytes;
    }
}
