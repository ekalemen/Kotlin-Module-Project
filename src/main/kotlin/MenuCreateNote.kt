class MenuCreateNote: Menu(
    "Создание новой заметки:",
    arrayListOf(NAV_ITEM_SAVE, NAV_ITEM_EXIT),
    arrayListOf(),
    true
) {
    override fun printMenu() {
        printHeader()
        println("Введите название заметки: ")
    }
}