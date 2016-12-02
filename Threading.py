
import threading
import time
import requests

exitFlag = 0

class myThread (threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter
    def run(self):
        #print ("Starting " + self.name)
        r = requests.post('http://localhost:13061/read_firebase', json={"start":"0","end":"10"})
        data = r.json() 
        print(str(data['execCount']) + " : " + str(data['execTime']) + "\n")
        #print_time(self.name, self.counter, 1)
        #print ("Exiting " + self.name)

def print_time(threadName, delay, counter):
    while counter:
        if exitFlag:
            threadName.exit()
        #time.sleep(delay)
        print ("%s: %s" % (threadName, time.ctime(time.time())))
        counter -= 1

# Create new threads
for m in range(0,1000):
    print(m)
    thread = myThread(1, "Thread-" + str(m) , m)
    thread.start()

#thread1 = myThread(1, "Thread-1", 1)
#thread2 = myThread(2, "Thread-2", 2)

# Start new Threads
#thread1.start()
#thread2.start()

print ("Exiting Main Thread")
