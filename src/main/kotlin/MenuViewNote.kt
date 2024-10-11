class MenuViewNote(val noteName: String, val noteText: String): Menu(
    "Просмотр заметки $noteName:",
    arrayListOf(NAV_ITEM_DELETE_NOTE, NAV_ITEM_EXIT),
    arrayListOf(),
    true
) {
    override fun printMenu() {
        printHeader()
        println("Текст заметки:")
        println(noteText)
        printItems()
    }
}