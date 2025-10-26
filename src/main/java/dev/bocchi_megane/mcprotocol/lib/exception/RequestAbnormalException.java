package dev.bocchi_megane.mcprotocol.lib.exception;

/**
 * 要求送信異常例外クラス
 * PLCへの要求送信時に発生する異常を表す例外です。
 * ネットワークエラーやソケット接続エラーなどが原因で発生します。
 */
public class RequestAbnormalException extends RuntimeException {

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     */
    public RequestAbnormalException(String message) {
        super(message);
    }
}