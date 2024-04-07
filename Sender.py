import time

def send_message(message, short_interval=0.1, long_interval=0.2):
    # we create a start and end signal to signal receiver that a massage is being sent
    start_signal = "110011"
    end_signal = "001100"
    full_message = start_signal + message + end_signal

    for bit in full_message:
        if bit == '0':
            time.sleep(short_interval)
        elif bit == '1':
            time.sleep(long_interval)
        print(bit, end='', flush=True)  # Simulating sending a bit
    print()  # For clarity

# The binary message to send
binary_message = "10101100101" # 1381
print("Sending message:", binary_message)
send_message(binary_message)
