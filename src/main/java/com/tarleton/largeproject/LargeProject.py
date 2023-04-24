import pyttsx4
from VoiceInput import VoiceInput
from CommandProcessor import CommandProcessor


class VoiceAssistant:
    def __init__(self):
        self.engine = pyttsx4.init()
        self.voice_input = VoiceInput()
        self.command_processor = CommandProcessor(self)
        self.engine.setProperty('rate', 250)

    def speak(self, text):
        self.engine.say(text)
        self.engine.runAndWait()

    def run(self):
        while True:
            wakeWord = self.voice_input.listen()
            if "jarvis" not in wakeWord:
                continue
            self.speak("Hello, how can I help you?")
            text = self.voice_input.listen()
            self.command_processor.process(text)


if __name__ == "__main__":
    VoiceAssistant().run()
