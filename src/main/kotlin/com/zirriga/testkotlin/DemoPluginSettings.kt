package com.zirriga.testkotlin

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "Demo",
    storages = [Storage("my-demo.xml")]
)

class DemoPluginSettings: PersistentStateComponent<DemoPluginState> {

    private var pluginState = DemoPluginState()

    override fun getState(): DemoPluginState? {
        return pluginState;

    }

    override fun loadState(state: DemoPluginState) {
        pluginState = state
    }

    companion object{
        @JvmStatic
        fun getInstance(): PersistentStateComponent<DemoPluginState>?{
            return ServiceManager.getService(DemoPluginSettings::class.java)
        }
    }


}