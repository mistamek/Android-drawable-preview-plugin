package com.mistamek.drawablepreview

import com.android.tools.idea.navigator.nodes.android.AndroidResFileNode
import com.android.tools.idea.navigator.nodes.android.AndroidResGroupNode
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.packageDependencies.ui.PackageDependenciesNode
import com.intellij.ui.ColoredTreeCellRenderer
import com.mistamek.drawablepreview.factories.IconPreviewFactory

class DrawablePreviewProjectViewNodeDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>?, data: PresentationData?) {
        if (node is AndroidResFileNode && node.parent is AndroidResGroupNode) {
            IconPreviewFactory.createIcon(node.value)?.also {

            }
        }
    }

    override fun decorate(node: PackageDependenciesNode?, cellRenderer: ColoredTreeCellRenderer?) {
    }
}