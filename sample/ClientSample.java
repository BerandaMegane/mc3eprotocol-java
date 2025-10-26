import dev.bocchi_megane.mcprotocol.lib.Client;

public class ClientSample {
    public static void main(String[] args) {
        boolean debug = true;
        // 接続先 PLC に合わせて修正する
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
