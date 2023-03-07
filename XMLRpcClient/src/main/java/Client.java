import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.apache.xmlrpc.client.*;

public class Client {
  public static void main(String[] args) throws Throwable {
    var config = new XmlRpcClientConfigImpl();
    config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
    config.setEnabledForExtensions(true);
    config.setConnectionTimeout(60 * 1000);
    config.setReplyTimeout(60 * 1000);

    var client = new XmlRpcClient();
    client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
    client.setConfig(config);

    // synchronous call
    logMessage("Executing synchronous call...");
    var result = client.execute("messagesService.sendMessage", List.of("m1")); //
    logMessage(result);

    // call with custom type
    result =
        client.execute("messagesService.sendMessage", List.of(new Message("custom type", "me")));
    logMessage(result);

    // asynchronous calls
    var timeoutCallback = new TimingOutCallback(1000);
    var correctCallback = new TimingOutCallback(5 * 1000);

    logMessage("Executing asynchronous call...");
    client.executeAsync("messagesService.sendMessageWithDelay", List.of("m2"), timeoutCallback);

    logMessage("Executing asynchronous call...");
    client.executeAsync("messagesService.sendMessageWithDelay", List.of("m3"), correctCallback);

    logMessage(getAsyncResponse(timeoutCallback));
    logMessage(getAsyncResponse(correctCallback));
  }

  public static Optional<Object> getAsyncResponse(TimingOutCallback callback) {
    try {
      return Optional.ofNullable(callback.waitForResponse());
    } catch (TimingOutCallback.TimeoutException e) {
      logMessage("No response from server before timeout.");
    } catch (Throwable e) {
      logMessage("Server returned an error message.");
    }

    return Optional.empty();
  }

  public static <T> void logMessage(T message) {
    System.out.println(Instant.now() + " " + message);
  }
}
