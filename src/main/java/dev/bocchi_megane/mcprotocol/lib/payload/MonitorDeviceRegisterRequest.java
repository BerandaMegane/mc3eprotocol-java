package dev.bocchi_megane.mcprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dev.bocchi_megane.mcprotocol.lib.define.CommandEnum;
import dev.bocchi_megane.mcprotocol.lib.define.DeviceSpec;
import dev.bocchi_megane.mcprotocol.lib.define.SubCommandEnum;
import dev.bocchi_megane.mcprotocol.lib.util.Converter;

/**
 * モニタデバイス登録要求クラス
 * PLCのモニタデバイス登録を要求するクラスです。
 */
public class MonitorDeviceRegisterRequest extends AbstractRequest {
    /** 登録デバイス点数（ワード単位） */
    private short _wordDevicePoint;
    /** 登録デバイス点数（ダブルワード単位） */
    private short _dwordDevicePoint;

    /** 登録デバイス（ワード単位） */
    private DeviceSpec[] _wordDeviceSpecs;
    /** 登録デバイス（ダブルワード単位） */
    private DeviceSpec[] _dwordDeviceSpecs;

    /**
     * デフォルトコンストラクタ
     * モニタデバイス登録要求を初期化します。
     */
    public MonitorDeviceRegisterRequest(byte[] request) {
        parse(request);
    }

    public MonitorDeviceRegisterRequest(DeviceSpec[] wordDeviceSpecs, DeviceSpec[] dwordDeviceSpecs) {
        // コマンド、サブコマンド設定
        _command = CommandEnum.MONITOR_REGISTER;
        _subCommand = SubCommandEnum.NONE;

        // デバイス仕様設定
        if (wordDeviceSpecs != null) {
            _wordDeviceSpecs = wordDeviceSpecs;
            _wordDevicePoint = (short)wordDeviceSpecs.length;
        } else {
            _wordDeviceSpecs = new DeviceSpec[0];
            _wordDevicePoint = 0;
        }
        if (dwordDeviceSpecs != null) {
            _dwordDeviceSpecs = dwordDeviceSpecs;
            _dwordDevicePoint = (short)dwordDeviceSpecs.length;
        } else {
            _dwordDeviceSpecs = new DeviceSpec[0];
            _dwordDevicePoint = 0;
        }

    }

    /**
     * 応答データを解析して応答オブジェクトを生成します。
     * 応答データは不要なため、NoneResponseオブジェクトを返します。
     * (参考: sh080003ah p.118)
     */
    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new NoneResponse(response);
    }

    /**
     * 要求データをバイト配列に変換します。
     *
     * @return 要求データのバイト配列（監視タイマーより後ろ）
     */
    @Override
    public byte[] toByteArray() {
        byte[] wordDeviceSpecs;
        byte[] dwordDeviceSpecs;
        // ワードデバイス指定
        if (_wordDevicePoint > 0) {
            wordDeviceSpecs = new byte[_wordDevicePoint * DeviceSpec.getByteArrayLength()];
            for (int i = 0; i < _wordDevicePoint; i++) {
                byte[] deviceSpecBytes = _wordDeviceSpecs[i].toByteArray();
                System.arraycopy(deviceSpecBytes, 0, wordDeviceSpecs, i * DeviceSpec.getByteArrayLength(), DeviceSpec.getByteArrayLength());
            }
        } else {
            wordDeviceSpecs = new byte[0];
        }
        
        // ダブルワードデバイス指定
        if (_dwordDevicePoint > 0) {
            dwordDeviceSpecs = new byte[_dwordDevicePoint * DeviceSpec.getByteArrayLength()];
            for (int i = 0; i < _dwordDevicePoint; i++) {
                byte[] deviceSpecBytes = _dwordDeviceSpecs[i].toByteArray();
                System.arraycopy(deviceSpecBytes, 0, dwordDeviceSpecs, i * DeviceSpec.getByteArrayLength(), DeviceSpec.getByteArrayLength());
            }
        } else {
            dwordDeviceSpecs = new byte[0];
        }

        return Converter.concatByteArrays(
            // コマンド
            _command.getBytes().getByteArray(),
            // サブコマンド
            _subCommand.getBytes().getByteArray(),
            // ワードアクセス点数
            Converter.fromIntToByteArray(_wordDevicePoint, 2),
            // ダブルワードアクセス点数
            Converter.fromIntToByteArray(_dwordDevicePoint, 2),
            // ワードデバイス指定
            wordDeviceSpecs,
            // ダブルワードデバイス指定
            dwordDeviceSpecs
        );
    }

    /**
     * バイト配列から要求データを解析してメンバ変数にセットします。
     * (参考: sh080003ah p.118)
     *
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

        // モニタ条件指定はなし

        // ワードアクセス点数
        data = new byte[2];
        buffer.get(data);
        _wordDevicePoint = (short)Converter.fromBytesToInt(data);

        // ダブルワードアクセス点数
        data = new byte[2];
        buffer.get(data);
        _dwordDevicePoint = (short)Converter.fromBytesToInt(data);

        // ワードデバイス指定
        for (int i = 0; i < _wordDevicePoint; i++) {
            data = new byte[DeviceSpec.getByteArrayLength()];
            buffer.get(data);
            if (_wordDeviceSpecs == null) {
                _wordDeviceSpecs = new DeviceSpec[_wordDevicePoint];
            }
            _wordDeviceSpecs[i] = new DeviceSpec(data);
        }
        
        // ダブルワードデバイス指定
        for (int i = 0; i < _dwordDevicePoint; i++) {
            data = new byte[DeviceSpec.getByteArrayLength()];
            buffer.get(data);
            if (_dwordDeviceSpecs == null) {
                _dwordDeviceSpecs = new DeviceSpec[_dwordDevicePoint];
            }
            _dwordDeviceSpecs[i] = new DeviceSpec(data);
        }
        
        // バッファが残っている場合は例外
        if (buffer.remaining() > 0) {
            throw new IllegalArgumentException("要求データの解析に失敗しました: 不明な余剰データがあります");
        }
    }

    public int getWordDevicePoint() {
        return _wordDevicePoint;
    }

    public int getDwordDevicePoint() {
        return _dwordDevicePoint;
    }

    public DeviceSpec[] getWordDeviceSpecs() {
        return _wordDeviceSpecs;
    }

    public DeviceSpec[] getDwordDeviceSpecs() {
        return _dwordDeviceSpecs;
    }
}
