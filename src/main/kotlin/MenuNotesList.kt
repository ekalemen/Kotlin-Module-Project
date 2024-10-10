class MenuNotesList(val archiveName: String, val notes: ArrayList<String> ): Menu(
    "Список заметок архива $archiveName:",
    arrayListOf(NAV_ITEM_NEW_NOTE, NAV_ITEM_DELETE_ARCH, NAV_ITEM_EXIT),
    notes,
    false
) { }