package com.ar.ui_practice.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.data.demo.DemoData
import com.ar.ui_practice.data.demo.DemoData.shortcutData
import com.ar.ui_practice.data.model.ShortcutData
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {

    var shorCutList = MutableLiveData(
        mutableListOf(
            ShortcutData(0, ic_add, "Shortcut"),
            ShortcutData(1, ic_add, "Shortcut"),
            ShortcutData(2, ic_add, "Shortcut"),
            ShortcutData(3, ic_add, "Shortcut")
        )
    )
        private set

    var shortCutSelectionList = MutableLiveData(shortcutData.toMutableList())
        private set

    fun updateShortcut(data: ShortcutData, selectedData: ShortcutData) {
        shortCutSelectionList.value?.remove(selectedData)
        val newList = shorCutList.value
        newList?.set(data.id, ShortcutData(data.id, selectedData.icon, selectedData.title, false))
        shorCutList.value = newList
        changeState()
    }

    private var isRemoveShow = false
    fun updateRemoveShow() {
        val newList = shorCutList.value

        isRemoveShow = !isRemoveShow
        newList?.forEach {
            if (it.title != "Shortcut") {
                it.removeIcon = !it.removeIcon
            }
        }
        shorCutList.value = newList
    }

    fun changeState() {
        isRemoveShow = false
        val newList = shorCutList.value
        newList?.forEach {
            if (it.title != "Shortcut") {
                it.removeIcon = false
            }
        }
        shorCutList.value = newList
    }

    fun removeShortCut(data: ShortcutData) {
        val newList = shorCutList.value
        val newSelectorList = shortCutSelectionList.value

        newList?.set(data.id, ShortcutData(data.id, ic_add, "Shortcut"))

        val item = shortcutData.find {
            data.title == it.title
        }

        newSelectorList?.add(item!!)
        newSelectorList?.sortBy { it.id }
        shorCutList.value = newList
        shortCutSelectionList.value = newSelectorList
    }

}