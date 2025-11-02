package dev.bocchi_megane.mcprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dev.bocchi_megane.mcprotocol.lib.define.CommandEnum;
import dev.bocchi_megane.mcprotocol.lib.define.SubCommandEnum;

/**
 * モニタデバイス読み取り要求クラス
 * MonitorDeviceRegisterRequestで登録したデバイスの値を読み取るクラスです。
 */
public class MonitorDeviceReadRequest extends AbstractRequest {

    /** 対応する登録要求オブジェクト */
    private MonitorDeviceRegisterRequest _registerRequest;

    public MonitorDeviceReadRequest(MonitorDeviceRegisterRequest registerRequest) {
        
        _registerRequest = registerRequest;

        // コマンド、サブコマンド設定
        _command = CommandEnum.MONITOR_READ;
        _subCommand = SubCommandEnum.NONE;
    }
    /**
     * バイト配列から要求オブジェクトを復元するコンストラクタ
     *
     * @param request 要求データ（監視タイマーより後ろ）
     */
    public MonitorDeviceReadRequest(byte[] request, MonitorDeviceRegisterRequest registerRequest) {
        _registerRequest = registerRequest;
        parse(request);
    }

    /**
     * 応答データを解析して応答オブジェクトを生成します。
     * (参考: sh080003ah p.119)
     * @return 応答データ（終了コードより後ろ）
     */
    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new MonitorDeviceReadResponse(response, this, _registerRequest);
    }

    /**
     * 要求データをバイト配列に変換します。
     * (参考: sh080003ah p.119)
     * @return 要求データのバイト配列（監視タイマーより後ろ）
     */
    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }

    /**
     * 要求データを解析してメンバ変数にセットします。
     * (参考: sh080003ah p.119)
     * @param request 要求データ（監視タイマーより後ろ）
     */
    @Override
    public void parse(byte[] request) {
        // バッファ
        ByteBuffer buffer = ByteBuffer.wrap(request);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] data;

        // コマンド
        data = new byte[2];
        buffer.get(data);
        _command = CommandEnum.build(data);

        // サブコマンド
        data = new byte[2];
        buffer.get(data);
        _subCommand = SubCommandEnum.build(data);

        // バッファが残っている場合は例外
        if (buffer.remaining() > 0) {
            throw new IllegalArgumentException("要求データの解析に失敗しました: 不明な余剰データがあります");
        }
    }
}
