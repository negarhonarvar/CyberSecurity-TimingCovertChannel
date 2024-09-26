# Timing Covert Channel Simulation

This project simulates a **timing covert channel**, where a **sender** transmits a binary message to a **receiver** without directly sending data. Instead, the communication is done using **event timing** (sleep intervals) to encode the message. The sender and receiver agree on a start and end signal to encode and decode the message.

### Overview of Encoding and Decoding:
- **Message Encoding**: The message is encoded using two different time intervals:
  - **Short Interval (100 ms)**: Encodes bit '0'.
  - **Long Interval (200 ms)**: Encodes bit '1'.
  - A tolerance of **50 ms** is added to accommodate timing inaccuracies.

- **Message Decoding**: 
  - The receiver calculates the time intervals between events to determine whether a '0' or '1' was sent.
  - A timer starts when an event occurs and stops when the next event is detected. The timer's value determines if the received bit is '0' or '1'.
  - The process continues until the end signal of the message is detected.

### Implementation Details:
- **Sender**: Prepares the binary message (e.g., `1381`) and transmits it using the agreed intervals.
- **Receiver**: Listens for the events, calculates the time between them, and reconstructs the binary message based on the intervals.

### Multi-threaded Version:
- A more realistic version is implemented using **threads**, where one thread handles the sending process and another handles the receiving. This version removes the need for start and end signals.
- In this case, the time intervals are modified:
  - **Short Interval**: 0.5 seconds.
  - **Long Interval**: 1 second.
  - The tolerance is set to the average of these two values.

### Data Exchange Speed:
- The speed of data transmission is based on the intervals:
  - '1' is sent in 200 ms.
  - '0' is sent in 100 ms.
- For a sample message: `11001110101100101001100`, the transmission speed is calculated as **0.657 bits per second**.

### Additional Information:
- The multi-threaded version is available in the repository as **TimingCovertChannel.java**, which demonstrates synchronized communication between sender and receiver threads using locks to manage timing.

This project offers a practical demonstration of covert channels and timing attacks, showcasing how data can be transferred covertly by manipulating event timing.
