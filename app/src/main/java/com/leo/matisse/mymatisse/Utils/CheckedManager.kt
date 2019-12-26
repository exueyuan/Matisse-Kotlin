package com.leo.matisse.mymatisse.Utils

import com.matisse.entity.Item

object CheckedManager {
    private val checkedNumList = arrayListOf<Item>()


    fun getNum(item:Item):Int {
        for (i in 0 until checkedNumList.size) {
            val checkedItem = checkedNumList[i]
            if (item.id == checkedItem.id) {
                return i + 1
            }
        }
        return -1
    }

    fun addItem(item:Item) {
        checkedNumList.add(item)
    }

    fun removeItem(item: Item) {
        for (i in 0 until checkedNumList.size) {
            val checkedItem = checkedNumList[i]
            if (item.id == checkedItem.id) {
                checkedNumList.remove(checkedItem)
                return
            }
        }
    }

    fun clear() {
        checkedNumList.clear()
    }
}