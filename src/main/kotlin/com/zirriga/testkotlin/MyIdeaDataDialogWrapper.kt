package com.zirriga.testkotlin

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class MyIdeaDataDialogWrapper : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    val txtAction = JTextField()
    val txtVoicePhrase = JTextField()

    var actionSaved = "txtAction"
    var phraseSaved = "txtVoicePhrase"

    init {
        init()
        title = "Zirriga voice Data"
        val state = DemoPluginSettings.getInstance()?.state
        if (state != null) {
            txtAction.text = state.zirriAction
            txtVoicePhrase.text = state.zirriPhrase

            actionSaved = state.zirriAction
            phraseSaved = state.zirriPhrase
        }

    }

    override fun createCenterPanel(): JComponent? {
        val gb = GridBag()
            .setDefaultInsets(Insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
            .setDefaultWeightX(1.0)
            .setDefaultFill(GridBagConstraints.HORIZONTAL)

        panel.preferredSize = Dimension(400, 200)

        panel.add(label("Action"), gb.nextLine().next().weightx(0.2))
        panel.add(txtAction, gb.nextLine().next().weightx(0.8))
        panel.add(label("VoicePhrase"), gb.nextLine().next().weightx(0.2))
        panel.add(txtVoicePhrase, gb.nextLine().next().weightx(0.8))

        return panel
    }

    override fun doOKAction() {
        val gotZirriAction = txtAction.text
        val gotZirriPhrase = txtVoicePhrase.text
        val state = DemoPluginSettings.getInstance()?.state
        state?.zirriAction = gotZirriAction
        state?.zirriPhrase = gotZirriPhrase

        actionSaved = gotZirriAction
        phraseSaved = gotZirriPhrase

        val credentialAttributes = CredentialAttributes("myplugin", "mykey")
        val credentials = Credentials(gotZirriAction, gotZirriPhrase)
        PasswordSafe.instance.set(credentialAttributes, credentials)
    }

    private fun label(text: String): JComponent {
        val label = JBLabel(text)
        label.componentStyle = UIUtil.ComponentStyle.SMALL
        label.fontColor = UIUtil.FontColor.BRIGHTER
        label.border = JBUI.Borders.empty(0, 5, 2, 0)
        return label
    }

}