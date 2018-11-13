package com.mistamek.drawablepreview.settings

import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.ProjectManager
import com.mistamek.drawablepreview.factories.Constants

object SettingsUtils {

    private const val PROPERTIES_SIZE = "com.mistamek.drawablepreview.settings.PropertiesSize"

    fun getPreviewSize() = PropertiesComponent.getInstance().getInt(PROPERTIES_SIZE, Constants.ICON_SIZE)

    fun isModified(previewSize: Int): Boolean {
        return previewSize != getPreviewSize()
    }

    fun apply(previewSize: Int) {
        PropertiesComponent.getInstance().setValue(PROPERTIES_SIZE, previewSize, Constants.ICON_SIZE)
        ProjectManager.getInstance().openProjects.forEach {
            ProjectView.getInstance(it).refresh()
        }
    }
}