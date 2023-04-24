from src.main.java.com.tarleton.largeproject.commands import AddToList


def run(voice_assistant):
    voice_assistant.speak("What list would you like to add to?")
    text = voice_assistant.voice_input.listen()

    if "cancel" in text:
        voice_assistant.speak("List cancelled.")
        return

    if text not in AddToList.lists.keys():
        voice_assistant.speak("That list does not exist")
        return

    for item in AddToList.lists[text]:
        voice_assistant.speak(item)
