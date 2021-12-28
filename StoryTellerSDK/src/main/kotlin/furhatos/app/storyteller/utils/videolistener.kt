package furhatos.app.storyteller.flow

import org.zeromq.ZMQ

val context: ZMQ.Context = ZMQ.context(1)

fun getConnectedSocket(socketType: Int, port: String, receiveTimeout: Int = -1): ZMQ.Socket {
    val socket = context.socket(socketType)
    if (receiveTimeout >= 0) socket.receiveTimeOut = receiveTimeout
    socket.connect(port)
    socket.subscribe("emotion")
    return socket
}