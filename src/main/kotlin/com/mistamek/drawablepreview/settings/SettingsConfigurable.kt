package com.mistamek.drawablepreview.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class SettingsConfigurable : Configurable {

    private var settingsUi: SettingsUi? = null

    override fun isModified() = false

    override fun getDisplayName() = "Android drawable preview"

    override fun apply() {
    }

    override fun createComponent(): JComponent? {
        settingsUi = SettingsUi()
        return settingsUi?.rootPanel
    }

    override fun disposeUIResources() {
        settingsUi = null
    }
}