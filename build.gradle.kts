plugins {
    //trick: for the same plugin versions in all sub-modules
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.androidApplication).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.androidLibrary).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinAndroid).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.sqldelightPlugin).apply(false)
}
