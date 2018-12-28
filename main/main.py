from opencv_pkg.c_seg import tcp_link
import opencv_pkg.z_seg
import ocr.ocr
import threading
import socket
if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('172.16.252.130', 8000))
    s.listen(100)
    print("Waiting for connection.")
    while True:
        sock, addr = s.accept()
        t = threading.Thread(target=tcp_link, args=(sock, addr))
        t.start()