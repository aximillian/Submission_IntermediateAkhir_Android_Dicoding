package com.example.bstory.data.response

import com.google.gson.annotations.SerializedName

abstract class DetailResponse (

    @field:SerializedName("error")
    val error: Boolean = false,

    @field:SerializedName("message")
    val message: String = ""
)