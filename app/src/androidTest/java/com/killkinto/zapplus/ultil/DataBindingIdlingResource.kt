package com.killkinto.zapplus.ultil

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingResource
import java.util.*

/**
 * Classe auxiliar para informar o Espresso Test uma Activity ou Fragment terminou de concluir o binding *
 */
class DataBindingIdlingResource : IdlingResource {
    private val idlingCallbacks = mutableListOf<IdlingResource.ResourceCallback>()

    private val id = UUID.randomUUID().toString()

    private var wasNotIdle = false

    lateinit var activity: FragmentActivity

    override fun getName() = "DataBinding $id"

    override fun isIdleNow(): Boolean {
        val idle = !getBindings().any { it.hasPendingBindings() }
        if (idle) {
            if (wasNotIdle) {
                // Notify observers to avoid Espresso race detector.
                idlingCallbacks.forEach { it.onTransitionToIdle() }
            }
            wasNotIdle = false
        } else {
            wasNotIdle = true
            // Check next frame.
            activity.findViewById<View>(android.R.id.content).postDelayed({
                isIdleNow
            }, 16)
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        idlingCallbacks.add(callback)
    }

    private fun getBindings(): List<ViewDataBinding> {
        val fragments = (activity as? FragmentActivity)?.supportFragmentManager?.fragments

        val bindings = fragments?.mapNotNull {
            it.view?.getBinding()
        } ?: emptyList()

        val childrenBindings = fragments?.flatMap { it.childFragmentManager.fragments }
            ?.mapNotNull { it.view?.getBinding() } ?: emptyList()

        return bindings + childrenBindings
    }
}

private fun View.getBinding(): ViewDataBinding? = DataBindingUtil.getBinding(this)

fun DataBindingIdlingResource.monitorActivity(
    activityScenario: ActivityScenario<out FragmentActivity>
) {
    activityScenario.onActivity {
        this.activity = it
    }
}

/*
fun DataBindingIdlingResource.monitorFragment(
    fragmentScenario: FragmentScenario<out Fragment>) {
    fragmentScenario.onFragment {
        this.activity = it.requireActivity()
    }
}*/
