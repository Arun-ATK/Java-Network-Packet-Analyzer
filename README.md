# Java Packet Analyser Project

A basic Java GUI application for capturing network packets, viewing packet headers, and saving/opening pcap files.

Done using [jNetPcap](https://sourceforge.net/projects/jnetpcap/), a java wrapper for WinPcap/libpcap.

## Installation

Download the latest version from [releases](https://github.com/Arun-ATK/Java-Network-Packet-Analyzer/releases).

### Windows

* Install WinPcap from <https://www.winpcap.org/default.htm>
* Add download location to environment path.
  * Type `edit environment variables for your account` in start menu
  * Double click "Path" option, click "New" option and add absolute path of download folder.

### Linux

* Use apt to install LibPcap

```bash
sudo apt-get install libpcap-dev
```

* Copy `libjnetpcap.so.0` to `/usr/lib` (Requires sudo permissions).
* Alternatively, type the following command (Must be done each time terminal is opened)

```bash
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/Path/to/application/Directory
```

* Create a link to `libjnetpcap.so.0` in the same directory
```bash
ln -s libjnetpcap.so.0 libjnetpcap.so
```

## Usage

Administrator/SuperUser privileges are required for live capturing of data. Pcap files can be opened with regular access.

### Windows

Run cmd as administrator, then use
```shell
java -jar 'Packet Sniffer.jar'
```

### Linux

Use the command
```bash
sudo java -jar 'Packet Sniffer.jar'
```
