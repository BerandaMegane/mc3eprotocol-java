package dev.bocchi_megane.mcprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dev.bocchi_megane.mcprotocol.lib.define.CommandEnum;
import dev.bocchi_megane.mcprotocol.lib.define.DataTypeEnum;
import dev.bocchi_megane.mcprotocol.lib.define.DeviceCodeEnum;
import dev.bocchi_megane.mcprotocol.lib.define.DeviceSpec;
import dev.bocchi_megane.mcprotocol.lib.define.SubCommandEnum;
import dev.bocchi_megane.mcprotocol.lib.util.Converter;

/**
 * 一括読込み要求クラス
 * ワード単位またはビット単位でのデバイス一括読込みを要求するクラスです。
 * デバイス仕様と読込み点数を指定して、対応する応答を受信します。
 */
public class BlockReadRequest extends AbstractRequest {
    /** 読込み対象のデバイス仕様 */
    protected DeviceSpec _deviceSpec;
    /** 読込みデバイス点数 */
    protected short _devicePoint;
    /** ビットデバイスかどうかのフラグ */
    public boolean _isBitDevice;

    /**
     * バイト配列から要求オブジェクトを復元するコンストラクタ
     *
     * @param request 要求データ（監視タイマーより後ろ）
     */
    public BlockReadRequest(byte[] request) {
        parse(request);
    }

    /**
     * パラメータ指定コンストラクタ
     *
     * @param deviceSpec 読込み対象のデバイス仕様
     * @param devicePoint 読込みデバイス点数
     * @param isBitDevice ビット単位読込みの場合はtrue、ワード単位読込みの場合はfalse
     */
    public BlockReadRequest(DeviceSpec deviceSpec, short devicePoint, boolean isBitDevice) {

        this._deviceSpec = deviceSpec;
        this._devicePoint = devicePoint;
        this._isBitDevice = isBitDevice;

        _command = CommandEnum.BLOCK_READ;
        DataTypeEnum dataType = deviceSpec.getDeviceCode().getDataType();
        switch (dataType) {
            case BIT:
                _isBitDevice = isBitDevice;
                break;
            case WORD:
            case DWORD:
                if (isBitDevice) {
                    throw new IllegalArgumentException("ワードデバイスでビット読込みはできません");
                }
                _isBitDevice = false;
                break;
            default:
                throw new IllegalArgumentException("不明なデータ型です: " + dataType);
        }
        
        if (_isBitDevice) {
            _subCommand = SubCommandEnum.Q_BIT;
        } else {
            _subCommand = SubCommandEnum.Q_WORD;
        }
    }

    /**
     * デバイス仕様を取得します。
     *
     * @return デバイス仕様
     */
    public DeviceSpec getDeviceSpec() {
        return _deviceSpec;
    }

    /**
     * 応答データを解析して適切な応答オブジェクトを生成します。
     * ビットデバイスかワードデバイスかによって異なる応答クラスを返します。
     *
     * @param response 応答データ（終了コードより後ろ）
     * @return 解析された応答オブジェクト
     */
    @Override
    public AbstractResponse parseResponse(byte[] response) {
        if (_isBitDevice) {
            return new BlockBitReadResponse(response, this);
        } else {
            return new BlockWordReadResponse(response, this);
        }
    }

    /**
     * 要求データをバイト配列に変換します。
     *
     * @return 要求データのバイト配列（監視タイマーより後ろ）
     */
    @Override
    public byte[] toByteArray() {
        return Converter.concatByteArrays(
            _command.getBytes().getByteArray(),
            _subCommand.getBytes().getByteArray(),
            _deviceSpec.toByteArray(),
            Converter.fromIntToByteArray(_devicePoint, 2)
        );
    }
    
    /**
     * バイト配列から要求データを解析してメンバ変数にセットします。
     *
     * @param byteArray 要求データ（監視タイマーより後ろ）
     */
    @Override
    public void parse(byte[] byteArray) {
        // バッファ
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
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

        // デバイス指定
        data = new byte[DeviceCodeEnum.getLength()];
        buffer.get(data);
        _deviceSpec = new DeviceSpec(data);

        // ポイント数
        data = new byte[2];
        buffer.get(data);
        _devicePoint = (short)Converter.fromBytesToInt(data);
    }

    /**
     * リクエストの詳細情報をコンソールに表示します。
     * デバッグ用途で要求内容を確認するために使用します。
     */
    @Override
    public void printInfo() {
        System.out.println(Converter.hereDoc(s->s, System.lineSeparator(), 
        //   Request payload: 00019000000100000401
            "Request payload: " + Converter.fromBytesToHexStringBigEndian(toByteArray()),
            "                 1   2   3     4 5",
            "                 1: コマンド: " + _command.toString(),
            "                 2: サブコマンド: " + _subCommand.toString(),
            "                 3: デバイス番号",
            "                 4: デバイスコード",
            "                 5: デバイス点数"
        ));
    }
}
