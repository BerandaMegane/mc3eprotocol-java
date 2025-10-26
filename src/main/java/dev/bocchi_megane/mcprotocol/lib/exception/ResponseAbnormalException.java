package dev.bocchi_megane.mcprotocol.lib.exception;

/**
 * 応答異常例外クラス
 * PLCからの応答で異常が検出された時に発生する例外です。
 * エラーコードが正常でない場合やデータ形式が不正な場合に発生します。
 */
public class ResponseAbnormalException extends RuntimeException {

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     */
    public ResponseAbnormalException(String message) {
        super(message);
    }
}