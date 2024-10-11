class MenuCreateArchive:  Menu(
    "Создание нового архива:",
    arrayListOf(NAV_ITEM_SAVE, NAV_ITEM_EXIT),
    arrayListOf(),
    true
) {
    override fun printMenu() {
        printHeader()
        println("Введите название архива: ")
    }
}