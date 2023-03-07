import java.time.Instant;

public class MessageService {

  private static final int DELAY_SEC = 3;

  public String sendMessage(String message) {
    System.out.println(String.join(" ", Instant.now().toString(), "Message received:", message));
    return "Message sent: " + message;
  }

  public String sendMessage(Message message) {
    System.out.println(
        String.join(
            " ",
            Instant.now().toString(),
            "Message received:",
            message.content(),
            "from",
            message.sender()));

    return "Message sent: " + message;
  }

  public String sendMessageWithDelay(String message) throws InterruptedException {
    System.out.println(String.join(" ", Instant.now().toString(), "Message received:", message));
    System.out.printf("Waiting for %d seconds before sending response...%n", DELAY_SEC);
    Thread.sleep(DELAY_SEC * 1000);

    return "Message sent after delay: " + message;
  }
}
