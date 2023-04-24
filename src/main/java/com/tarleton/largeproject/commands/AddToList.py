lists = {}


def run(voice_assistant):
    voice_assistant.speak("What list would you like to add to?")
    text = voice_assistant.voice_input.listen()

    if "cancel" in text:
        voice_assistant.speak("List cancelled.")
        return

    if text not in lists.keys():
        voice_assistant.speak(f"The list {text} does not exist")
        return
    voice_assistant.speak("What would you like to add?")
    item = voice_assistant.voice_input.listen()
    lists[text].append(item)
    voice_assistant.speak(item + " added.")
