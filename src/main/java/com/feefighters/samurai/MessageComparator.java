package com.feefighters.samurai;

import org.apache.commons.lang.ArrayUtils;

import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {
    private String[] keyPriority = { "is_blank", "not_numeric", "too_short", "too_long", "failed_checksum" };

    public int compare(Message msg1, Message msg2) {
        return ArrayUtils.indexOf(keyPriority, msg1.key) - ArrayUtils.indexOf(keyPriority, msg2.key);
    }
}
