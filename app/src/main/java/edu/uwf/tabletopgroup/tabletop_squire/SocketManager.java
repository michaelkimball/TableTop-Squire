package edu.uwf.tabletopgroup.tabletop_squire;

import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by Quinn on 4/16/2016.
 * Keep socket out of android life cycle so we don't
 * have to keep reconnecting. Also makes easy for
 * multiple activities/fragments to access connection
 * without having to reconnect
 */
public class SocketManager {
    public static Socket connection = null;

    // No instances only static
    private SocketManager() {}
}
