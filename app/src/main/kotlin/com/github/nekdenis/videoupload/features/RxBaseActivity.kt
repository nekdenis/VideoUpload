package com.github.nekdenis.videoupload.features

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable

open class RxBaseActivity() : Activity() {

    protected var disposables = CompositeDisposable()

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}