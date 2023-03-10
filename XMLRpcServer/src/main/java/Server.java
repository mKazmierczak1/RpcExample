import java.time.Instant;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class Server {
  private static final int port = 8080;

  public static void main(String[] args) {
    var webServer = new WebServer(port);
    var xmlRpcServer = webServer.getXmlRpcServer();
    var handler = new PropertyHandlerMapping();
    var serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();

    try {
      handler.addHandler("messagesService", MessageService.class);
      xmlRpcServer.setHandlerMapping(handler);
      serverConfig.setEnabledForExtensions(true);

      webServer.start();
      System.out.println(Instant.now() + " Server started...");
    } catch (Exception e) {
      System.out.println("Something went wrong!");
    }
  }
}
