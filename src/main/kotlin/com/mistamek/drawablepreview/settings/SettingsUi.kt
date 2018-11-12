package com.mistamek.drawablepreview.settings

import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.uiDesigner.core.Spacer
import java.awt.Dimension
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class SettingsUi {

    val rootPanel = JPanel()
    val previewSizeTextField = JTextField()

    init {
        rootPanel.layout = GridLayoutManager(2, 2, Insets(0, 0, 0, 0), -1, -1)

        val previewSizeLabel = JLabel()
        previewSizeLabel.text = "Preview size"
        rootPanel.add(previewSizeLabel, GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false))
        previewSizeTextField.text = ""
        rootPanel.add(previewSizeTextField, GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, Dimension(50, -1), Dimension(50, -1), 0, false))

        val spacer1 = Spacer()
        rootPanel.add(spacer1, GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false))
    }
}