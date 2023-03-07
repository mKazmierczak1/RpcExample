import java.io.Serializable;

// XML-RPC supports types which includes Serializable interface and JAXB object (Java XML library)
public record Message(String content, String sender) implements Serializable {}
