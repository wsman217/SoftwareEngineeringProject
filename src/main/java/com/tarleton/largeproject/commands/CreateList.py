from src.main.java.com.tarleton.largeproject.commands import AddToList


def run(voice_assistant):
    voice_assistant.speak("What would you like this list to be called?")
    text = voice_assistant.voice_input.listen()

    if "cancel" in text:
        voice_assistant.speak("List cancelled.")
        return

    if text in AddToList.lists.keys():
        voice_assistant.speak("That list already exists.")
        return

    AddToList.lists[text] = []
    voice_assistant.speak("List " + text + " created.")
