def simulate_received_signal(full_message, short_interval=0.1, long_interval=0.2):
    signals = []
    # we need to find start and end signal
    for bit in full_message:
        if bit == '0':
            signals.append(short_interval)
        else:
            signals.append(long_interval)
    return signals


def receive_message(timing_intervals, short_interval=0.1, long_interval=0.2, tolerance=0.05):
    decoding = False
    message_bits = []
    current_sequence = ""

    for interval in timing_intervals:
        # we determine each bit according to time interval
        bit = '0' if abs(interval - short_interval) <= tolerance else '1'
        current_sequence += bit

        if not decoding:
            if current_sequence.endswith("110011"): # "110011" is start signal
                decoding = True
                current_sequence = ""  # we need to empty current sequence to store massage
        else:
            # while storing each bit in current sequence we need to check for end signal sequence
            message_bits.append(bit)
            if current_sequence.endswith("001100"): # "001100" is end signal
                decoding = False
                return ''.join(message_bits[:-6]) # we remove the end signal from main massage , since we know the end signal we know it's length too

    # if the massage doesn't start with start signal or no message is found
    return ""


# starting the receiving processs
encoded_message = "110011" + "10101100101" + "001100"
simulated_intervals = simulate_received_signal(encoded_message)

# decoding massage , the receiving proccess doesn't actually happen
decoded_message = receive_message(simulated_intervals)
print("A massage is being recievd:", decoded_message)
