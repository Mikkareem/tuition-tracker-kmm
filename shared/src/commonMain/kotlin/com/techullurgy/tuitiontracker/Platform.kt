package com.techullurgy.tuitiontracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform