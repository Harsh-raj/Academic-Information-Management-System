package frontend;

import java.util.HashMap;

public class MessagePasser {
    private static MessagePasser instance;
    private HashMap<String, Object> hmap;
    private MessagePasser()
    {
        hmap = new HashMap<>();
    }

    public static MessagePasser getInstance()
    {
        if(instance == null)
            instance = new MessagePasser();
        return instance;
    }

    public HashMap<String, Object> getMessages() {
        return hmap;
    }

}