import asyncio
import cv2
import zmq
from emotions import detect_emotions, init_model

# prevents openCL usage and unnecessary logging messages
cv2.ocl.setUseOpenCL(False)

# start the webcam feed
cap = cv2.VideoCapture(1)

 
init_model()


context = zmq.Context()
socket = context.socket(zmq.REP)
socket.bind("tcp://*:3333")
while True:
    message = socket.recv_string()
    if message == "Emotion":
        emotions = detect_emotions(cap)
        socket.send_string(emotions[0])
    else:
        socket.send_string("Unknown command")
