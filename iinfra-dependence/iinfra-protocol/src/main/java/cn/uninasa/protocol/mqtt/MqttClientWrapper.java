package cn.uninasa.protocol.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT客户端封装
 */
@Slf4j
public class MqttClientWrapper {

    private MqttClient client;
    private final String broker;
    private final String clientId;

    public MqttClientWrapper(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
    }

    /**
     * 连接MQTT服务器
     */
    public void connect(String username, String password) throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(60);
        options.setAutomaticReconnect(true);
        client.connect(options);
        log.info("MQTT连接成功: broker={}, clientId={}", broker, clientId);
    }

    /**
     * 发布消息
     */
    public void publish(String topic, String message, int qos) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        client.publish(topic, mqttMessage);
    }

    /**
     * 订阅主题
     */
    public void subscribe(String topic, int qos, IMqttMessageListener listener) throws MqttException {
        client.subscribe(topic, qos, listener);
        log.info("MQTT订阅成功: topic={}", topic);
    }

    /**
     * 断开连接
     */
    public void disconnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            log.info("MQTT断开连接");
        }
    }

    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}
