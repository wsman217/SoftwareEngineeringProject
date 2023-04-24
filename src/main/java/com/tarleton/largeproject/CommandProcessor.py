from src.main.java.com.tarleton.largeproject.commands import Alarm, Stop, CreateList, AddToList, ReadList


class CommandProcessor:
    def __init__(self, voice_assistant):
        self.voice_assistant = voice_assistant
        self.commands = {"set alarm": Alarm, "stop": Stop, "exit": Stop, "create list": CreateList,
                         "add to list": AddToList, "read list": ReadList}

    def process(self, text):
        for command, executor in self.commands.items():
            if command in text:
                executor.run(self.voice_assistant)
                return
        self.voice_assistant.speak("Sorry, I didn't understand that.")
