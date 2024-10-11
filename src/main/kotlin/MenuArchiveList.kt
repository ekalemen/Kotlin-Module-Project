class MenuArchiveList(val archives: ArrayList<String>): Menu(
    "Список архивов:",
    arrayListOf(NAV_ITEM_NEW_ARCHIVE, NAV_ITEM_EXIT),
    archives,
    false
) { }