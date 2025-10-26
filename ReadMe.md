# mcprotocol-java

## はじめに

Java で PLC と MC プロトコルで通信するクライアントプログラムを作りました。  
対応する規格は MC プロトコル (3E フレーム) の一部のコマンドです。

* Javadoc [docs/apidocs/index.html]

### 動作環境

* パソコン
  * Windows 11 Pro
* PLC
  * 三菱電機 Q03UDV CPU

### ビルド環境

* Oracle OpenJDK 25
* Maven

## 使い方

### PLC の IP アドレス設定

Q03UDV の場合は、次の記事を参考に、PLC に IP アドレスなどを設定します。他の機種を利用している場合は、別途情報収集をお願いします。

Qiita - [@satosisotas Works2でSLMP通信を試すための内蔵Ethernet設定](https://qiita.com/satosisotas/items/38f64c872d161b612071)

やっておかなければならない設定は次の通りです。
* IP アドレス
* ポート番号 (TCP)
* RUN中書込みを許可する
* CPU のリセットをかけておく

### サンプルプログラムの作成

[sample] ディレクトリの [sample/ClientSample.java] を作業ディレクトリに作成します。  
接続先の PLC の IP アドレスとポート番号へ変更してください。

```
import dev.bocchi_megane.mcprotocol.lib.Client;

public class ClientSample {
    public static void main(String[] args) {
        boolean debug = true;
        Client client = new Client("127.0.0.1", 5000, debug);
        System.out.println("接続先: " + client.getHost() + ":" + client.getPort());
        client.connect();
        if (client.isConnected()) {
            System.out.println("接続できました");
        } else {
            System.out.println("接続できませんでした");
            return;
        } 
        
        // ここに書く
        
        client.disconnect();
    }
}
```

### JAR ファイルのダウンロード

GitHub ページの Release ページより JAR ファイルをダウンロードし、作業ディレクトリに配置します。

### 実行する

```powershell
# コンパイル
javac -cp ./mc3eprotocol-java-1.0.0.jar ./ClientSample.jav
# 実行 (Windows)
java -cp "./;./mc3eprotocol-java-1.0.0.jar" ClientSample
# 実行 (Windows以外)
java -cp "./:./mc3eprotocol-java-1.0.0.jar" ClientSample
```

実行結果
```
接続先: 127.0.0.1:5000
接続できました
```

## 運用

### JAR

```powershell
mvn package
```

### javadoc

docs ディレクトリへ日本語 Javadoc を生成しています。

```powershell
mvn javadoc:javadoc
```

出力結果: [index.html](docs/apidocs/index.html)

