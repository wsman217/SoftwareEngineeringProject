import datetime
import threading
import time


def run(voice_assistant):
    voice_assistant.speak("What time would you like to set the alarm for?")
    time_input = voice_assistant.voice_input.listen()

    if "cancel" in time_input:
        voice_assistant.speak("Alarm cancelled.")
        return

    try:
        tSplit = time_input.split()
        if ":" not in time_input and len(tSplit) == 2:
            time_input = tSplit[0] + ":00 " + tSplit[1]
        time_input = time_input.lower().replace("a.m.", "am").replace("p.m.", "pm")
        alarm_time = datetime.datetime.strptime(time_input, "%I:%M %p")
    except ValueError:
        voice_assistant.speak("Sorry, I didn't understand the time you specified.")
        return

    voice_assistant.speak("Alarm set for {}.".format(time_input))
    alarm_thread = threading.Thread(target=run_alarm, args=(voice_assistant, alarm_time,))
    alarm_thread.start()


def run_alarm(voice_assistant, alarm_time):
    current_time = datetime.datetime.now()
    while current_time.time() < alarm_time.time():
        time.sleep(1)
        current_time = datetime.datetime.now()
    voice_assistant.speak("Time's up!")
