package com.mistamek.drawablepreview.settings

import com.intellij.openapi.options.Configurable
import com.mistamek.drawablepreview.getDigits
import javax.swing.JComponent

class SettingsConfigurable : Configurable {

    private var settingsUi: SettingsUi? = null

    override fun isModified(): Boolean {
        return settingsUi?.previewSizeTextField?.let {
            SettingsUtils.isModified(it.text.getDigits() ?: 0)
        } ?: false
    }

    override fun getDisplayName() = "Android drawable preview"

    override fun apply() {
        settingsUi?.previewSizeTextField?.let {
            SettingsUtils.apply(it.text.getDigits() ?: 0)
        }
    }

    override fun createComponent(): JComponent? {
        settingsUi = SettingsUi()
        settingsUi?.previewSizeTextField?.text = SettingsUtils.getPreviewSize().toString()
        return settingsUi?.rootPanel
    }

    override fun disposeUIResources() {
        settingsUi = null
    }
}