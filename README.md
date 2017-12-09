# roomba_client
[![Build Status](https://travis-ci.org/hiroki-sawano/roomba_client.svg?branch=master)](https://travis-ci.org/hiroki-sawano/roomba_client)

## Prerequisite
Make sure your Roomba has RooWiFi module(http://www.roowifi.com/) so that it can be operated by using socket TCP/IP communication.

## Installation
Simply clone this repository first.
```bash
$ git clone https://github.com/hiroki-sawano/roomba_client.git
$ cd roomba_client
```

Then, you need to modify properties in application.yml(at least roomba.ip).

**src/main/resources/application.yml**
```yaml
# roomba_client's specific settings
roomba:
  # your Roomba's ip address 
  ip: 192.168.0.19
  # your Roomba's port number
  port: 9001
  # serial sequences in the select box, 'Selectable Sequence'
  sequences:
    start_and_safe: 128 131
    start_and_full: 128 132
    clean: 135
    max: 136
    spot: 134
    seek_dock: 143
    song: 140 0 7 76 16 76 32 79 16 79 16 77 16 74 16 72 32
    play: 141 0
    end: 128
    daily_schedule: 167 62 0 0 12 0 12 0 12 0 12 0 12 0 0 0
```

Lastly, type the following command to start the app.
```bash
$ ./gradlew bootRun
```

## How it works
Step1 : Fill in a command's name and either choose a serial sequence in the select box or input arbitrary numbers.  
(For information on (The Roomba Open Interface(OI)), please refer to http://www.irobot.lv/uploaded_files/File/iRobot_Roomba_500_Open_Interface_Spec.pdf.)  

Step2 : You can add the command to the data table on the bottom of the page by clicking on 'ADD COMMAND' or send it to your Roomba immediately(w/o saving it) by clicking on 'EXECUTE COMMAND'.  
### Multiple commands execution
![Execute all the command you added](https://github.com/hiroki-sawano/roomba_client/blob/master/imgs/demo_exec_all.gif)

### Immediate command execution
![Execute a command immediately](https://github.com/hiroki-sawano/roomba_client/blob/master/imgs/demo_exec_im.gif)
