fun main(args: Array<String>) {
    val archives: ArrayList<String> = arrayListOf("Список дача", "за 12.01.2024", "список2")
    val notes: ArrayList<String> = arrayListOf("сделать розетку", "заменить масло в снегоуборке", "убрать батут")

    val notesArchive: MutableMap<String,ArrayList<Note>> = mutableMapOf()

    /* just init data for debugging. uncomment to test app
    val note1: Note = Note("сделать розетку", "купить розетку с крышкой на озоне")
    val note2: Note = Note("заменить масло в снегоуборке", "купить масло 5w40")
    val note3: Note = Note("убрать батут", "просушить и сложить в сарай")
    notesArchive.put("Список дача", arrayListOf(note1,note2,note3))
    notesArchive.put("за 12.01.2024", arrayListOf())
    notesArchive.put("список2", arrayListOf()) */

    val menuArchList: MenuArchiveList = MenuArchiveList(notesArchive.keys.toList() as ArrayList<String>)
    var isExit: Boolean = false

    menuArchList.printMenu()
    while (!isExit) {
        var codeOps: Pair<Int, String> = menuArchList.getUserInput()
        when(codeOps.first) {
            /* Go to the archive notes */
            MenuOperationCodes.GO_DEEPER.value -> {
                val archiveName: String = codeOps.second
                var notesNames: ArrayList<String> = arrayListOf()
                var inNotesList: Boolean = true

                for (note in notesArchive.getValue(codeOps.second)) notesNames.add(note.name)

                val menuNotesList: MenuNotesList = MenuNotesList(archiveName, notesNames)
                menuNotesList.printMenu()
                while (inNotesList) {
                    codeOps = menuNotesList.getUserInput()
                    when (codeOps.first) {
                        /* Exit from the archive notes to the archives list*/
                        MenuOperationCodes.EXIT.value -> {
                            inNotesList = false
                            menuArchList.printMenu()
                        }
                        /* Delete archive and back to th archive list*/
                        MenuOperationCodes.DELETE.value -> {
                            notesArchive.remove(archiveName)
                            menuArchList.viewItems.clear()
                            menuArchList.viewItems.addAll(notesArchive.keys)
                            menuArchList.init()
                            menuArchList.printMenu()
                            inNotesList = false
                        }

                        /* Go to create new note */
                        MenuOperationCodes.CREATE.value -> {
                            val menuCreateNote: MenuCreateNote = MenuCreateNote()
                            var noteName: String = ""
                            var noteText: String = ""
                            var isNoteAdded: Boolean = true

                            menuCreateNote.printMenu()
                            noteName = menuCreateNote.getTextFromInput()
                            println("Введите текст заметки:")
                            noteText = menuCreateNote.getTextFromInput()
                            menuCreateNote.printItems()
                            codeOps = menuCreateNote.getUserInput()
                            when (codeOps.first) {
                                /* Exit back to the notes list */
                                MenuOperationCodes.EXIT.value -> menuNotesList.printMenu()
                                /* Save/add new note and exit back to the archives list */
                                MenuOperationCodes.SAVE.value -> {
                                    for(note in notesArchive.getValue(archiveName)) {
                                        if(note.name.equals(noteName)) {
                                            println("Ошибка: заметка с таким именем уже существует в данном архиве!")
                                            isNoteAdded = false
                                            break;
                                        }
                                    }
                                    if (isNoteAdded) {
                                        notesArchive.getValue(archiveName)
                                            .add(Note(noteName, noteText))
                                        menuNotesList.viewItems.clear()
                                        for (n in notesArchive.getValue(archiveName))
                                            menuNotesList.viewItems.add(n.name)
                                        menuNotesList.init()
                                    }
                                    menuNotesList.printMenu()
                                }
                            }
                        }

                        /* Go to the note */
                        MenuOperationCodes.GO_DEEPER.value -> {
                            val noteName: String = codeOps.second
                            var noteText: String = " "

                            for (note in notesArchive.getValue(archiveName)) {
                                if (note.name.equals(noteName)) noteText = note.noteText
                            }

                            val menuViewNote: MenuViewNote = MenuViewNote(noteName, noteText)
                            menuViewNote.printMenu()

                            codeOps = menuViewNote.getUserInput()
                            when (codeOps.first) {
                                /* Exit back to the notes list */
                                MenuOperationCodes.EXIT.value -> menuNotesList.printMenu()
                                /* Delete note and back to the notes list */
                                MenuOperationCodes.DELETE.value -> {
                                    for (note in notesArchive.getValue(archiveName)) {
                                        if (note.name.equals(noteName)) {
                                            notesArchive.getValue(archiveName).remove(note)
                                            break
                                        }
                                    }

                                    menuNotesList.viewItems.clear()
                                    for (note in notesArchive.getValue(archiveName)) menuNotesList.viewItems.add(
                                        note.name
                                    )
                                    menuNotesList.init()
                                    menuNotesList.printMenu()
                                }
                            }
                        }
                    }
                }
            }
            /* Create new archive */
            MenuOperationCodes.CREATE.value -> {
                val menuCreateArchive: MenuCreateArchive = MenuCreateArchive()
                var archName: String = ""
                menuCreateArchive.printMenu()
                archName = menuCreateArchive.getTextFromInput()
                menuCreateArchive.printItems()
                codeOps = menuCreateArchive.getUserInput()
                when (codeOps.first) {
                    /* Exit back to the archives list */
                    MenuOperationCodes.EXIT.value -> menuArchList.printMenu()
                    /* Save/add archive and exit back to the archives list */
                    MenuOperationCodes.SAVE.value -> {
                        if(notesArchive.contains(archName)) {
                            println("Ошибка: архив с таким именем уже существует!")
                            menuArchList.printMenu()
                        } else {
                            notesArchive.put(archName, arrayListOf())
                            menuArchList.viewItems.add(archName)
                            menuArchList.init()
                            menuArchList.printMenu()
                        }
                    }
                }
            }
            /* Exit from app */
            MenuOperationCodes.EXIT.value -> isExit = true
        }
    }
}
