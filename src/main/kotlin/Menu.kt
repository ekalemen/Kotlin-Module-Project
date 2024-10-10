import java.util.Scanner

enum class MenuOperationCodes(val value: Int) {
    EXIT(1),
    SAVE(2),
    DELETE(3),
    CREATE(4),
    GO_DEEPER (5)
}
open class Menu(
    val menuHeader: String,
    val navItems: ArrayList<String>,
    var viewItems: ArrayList<String>,
    val isEdit: Boolean) {
    private var menuItems: MutableMap<Int,String> = mutableMapOf()
    private var scanner = Scanner(System.`in`)
    private var codeOfOperation: MenuOperationCodes = MenuOperationCodes.GO_DEEPER

    init {
        init()
    }

    fun init() {
        menuItems.clear()
        for (i in 0 until navItems.size) {
            menuItems.put(i, navItems[i])
        }

        if (!isEdit) {
            for (i in 0 until viewItems.size) {
                menuItems.put((i + navItems.size),viewItems[i])
            }
        }
    }
    open fun printHeader() {
        println(menuHeader)
        for (i in 0 until menuHeader.length) print('-')
        println()
    }
    open fun printItems() {
        for (item in menuItems) {
            println("${item.key}. ${item.value}")
        }
    }

    open fun printMenu() {
        printHeader()
        printItems()
    }

    fun getUserInput(): Pair<Int, String>  {
        var codeOps: Pair<Int, String> = Pair(0,"")
        var userInp: Int = 0
        var isInt: Boolean = false

        while (!isInt) {
            if (scanner.hasNextInt()) {
                userInp = scanner.nextInt()
                isInt = true
            } else scanner.next()
        }

        if (menuItems.containsKey(userInp)) {
            if (userInp < navItems.size) {
                codeOps = when(menuItems.get(userInp)) {
                    NAV_ITEM_EXIT -> Pair(MenuOperationCodes.EXIT.value , "")
                    NAV_ITEM_DELETE_ARCH,NAV_ITEM_DELETE_NOTE -> Pair(MenuOperationCodes.DELETE.value , "")
                    NAV_ITEM_SAVE -> Pair(MenuOperationCodes.SAVE.value , "")
                    else -> Pair(MenuOperationCodes.CREATE.value , menuItems.getValue(userInp))
                }
            } else {
                codeOps = Pair(MenuOperationCodes.GO_DEEPER.value , menuItems.getValue(userInp))
            }

            } else {
                println("Ошибка: Некорректная операция!")
            }

        return codeOps
    }

    fun getTextFromInput(): String {
        var text: String = ""
        var isLineGet: Boolean = false

        while(!isLineGet) {
            if (scanner.hasNextLine()) {
                text = scanner.nextLine()
                if (!text.isEmpty()) isLineGet = true
                else println("Ошибка: Текстовое поле не может быть пустым!")
            }
        }

        return text
    }

    companion object{
        const val NAV_ITEM_NEW_NOTE = "[Создать заметку]"
        const val NAV_ITEM_NEW_ARCHIVE = "[Создать архив]"
        const val NAV_ITEM_DELETE_ARCH = "[Удалить архив]"
        const val NAV_ITEM_DELETE_NOTE = "[Удалить заметку]"
        const val NAV_ITEM_SAVE = "[Сохранить]"
        const val NAV_ITEM_EXIT = "[Выход]"
    }
}