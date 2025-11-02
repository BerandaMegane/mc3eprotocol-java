package dev.bocchi_megane.mcprotocol.sample;

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
        
        // デバイス情報を取得し、コンソール出力する (ビット/ワードデバイス対応)
        client.printDevice2("SM412");
        client.printDevice2("D100");
        
        client.disconnect();
    }
}
