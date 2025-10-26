package dev.bocchi_megane.mcprotocol.lib.payload;

/**
 * モニタデバイス読み取り要求クラス
 * MonitorDeviceRegisterRequestで登録したデバイスの値を読み取るクラスです。
 */
public class MonitorDeviceReadRequest extends AbstractRequest {

    /**
     * バイト配列から要求オブジェクトを復元するコンストラクタ
     *
     * @param request 要求データ（監視タイマーより後ろ）
     */
    public MonitorDeviceReadRequest(byte[] request) {
        parse(request);
    }

    /**
     * パラメータ指定コンストラクタ
     * パラメータは存在しない。sh080003ah p.119 参照
     */
    public MonitorDeviceReadRequest() { }

    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new MonitorDeviceReadResponse(response, this);
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }

    @Override
    public void parse(byte[] request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }
}
