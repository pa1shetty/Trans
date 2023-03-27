package com.example.trans.utillity

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UtilClass @Inject constructor(){
    fun validateData(strings: ArrayList<String>): Boolean {
        for (s in strings) {
            if (s.isEmpty()) {
                return false // return false if any string is empty
            }
        }
        return true // all strings are not empty
    }
}