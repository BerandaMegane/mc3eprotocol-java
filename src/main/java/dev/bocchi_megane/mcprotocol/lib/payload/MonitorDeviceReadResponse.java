package dev.bocchi_megane.mcprotocol.lib.payload;

public class MonitorDeviceReadResponse extends AbstractResponse {

    /**
     * コンストラクタ
     * 応答データを解析してワード読込み応答オブジェクトを作成します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @param request 対応する読込み要求オブジェクト
     */
    public MonitorDeviceReadResponse(byte[] responseByteArray, MonitorDeviceReadRequest request) {
        parse(responseByteArray, request);
    }

    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }

    @Override
    public byte[] toBytes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toBytes'");
    }

}
